import * as React from "react";
import {Link, NavLink} from "react-router-dom";

import {Menu, Search, Icon} from "semantic-ui-react";
import "../../../../style/header.css";
import {Login} from "../Login/Login";

import { clearToken, getToken } from "../../../utils/token";
import { Logo } from "./Logo";

import {ReviewModal} from "../Order/ReviewModal";

import {Review} from "../../../model/Review";
import MediaQuery from "react-responsive";

import * as api from "../../../api";
import { Order } from "../../../model/Order";

import * as CartManager from "../../../utils/cart";

import { connect } from "react-redux";
import { bindActionCreators} from "redux";
import { login, logout } from "../../../redux/actions/auth";
import { AuthReducerState, AuthAction } from "../../../redux/reducers/authReducer";

interface HeaderState {
    activeItem: String;
    isSearch: boolean;
    ordersToReview: Order[];
}

interface HeaderReducerMethods{
    dispatchLogin: () => AuthAction;
    dispatchLogout: () => AuthAction;
}

class Header extends React.Component <AuthReducerState & HeaderReducerMethods, HeaderState> {
    state: HeaderState = {
        activeItem: "home",
        isSearch: false,
        ordersToReview: []
    };

    handleLogout = () => {
        clearToken();
        this.props.dispatchLogout();
    }

    handleLogin = async () => {
        this.props.dispatchLogin();
        const response = await api.review.getOrdersToReview();
        if (api.isError(response)) {
            // Don't show an error if the review fetch failed
            return;
        }
        this.setState({...this.state, ordersToReview: response});
        const cartMergeResponse = await CartManager.onLogin();
        if (api.isError(cartMergeResponse)) {
            // TODO: should we show an error if the merge fails?
            return;
        }
    }

    handleItemClick = (e: any, { name }: any) => this.setState({ ...this.state, activeItem: name });

    handleReview = (orderId: number, review: Review) => {
        api.review.submitReview(orderId, review);
        // No need to wait for the response
        this.setState({
            ...this.state,
            ordersToReview: this.state.ordersToReview.slice(1)
        });
    }

    handleReviewClose = () => {
        Promise.all(this.state.ordersToReview.map(o => api.review.rejectReview(o.id)));
        // No need to await
        this.setState({...this.state, ordersToReview: []});
    }

    isLoggedIn = (): boolean => {
        console.log("a");
        return this.props.loggedIn !== null ? this.props.loggedIn : getToken() !== null;
    }
    render () {
        const loggedIn = this.isLoggedIn();
        const { activeItem } = this.state;
        return (
            <div>
                <Menu id="menuHeader">
                    <Menu.Item
                        className="logoContainer borderless"
                        id="logoHeaderColumn"
                        active={activeItem === "menu"}
                        onClick={this.handleItemClick}>
                    <NavLink to="/">
                        <MediaQuery query="(min-width: 900px)">
                            <Logo size="large"/>
                        </MediaQuery>
                        <MediaQuery query="(max-width: 899px)">
                            <Logo size="small"/>
                        </MediaQuery>
                    </NavLink>
                    </Menu.Item>
                    <Menu.Menu position="right">
                        <MediaQuery query="(min-width: 500px)">
                            <Menu.Item className="borderless">
                                <Search id="searchBar"
                                    placeholder="Search..."
                                    noResultsMessage="No products were found"
                                    size="mini"
                                    fluid
                                />
                            </Menu.Item>
                            <Menu.Item
                                name="cart"
                                active={activeItem === "cart"}
                                onClick={this.handleItemClick}
                                as={Link}
                                to="/cart"
                            >Cart
                            </Menu.Item>
                            {
                                loggedIn
                                ?
                                        [<Menu.Item
                                                name="profile"
                                                active={activeItem === "profile"}
                                                onClick={this.handleItemClick}
                                                as={Link}
                                                to="/profile"
                                            />,
                                            <Menu.Item
                                                name="log out"
                                                active={false}
                                                onClick={this.handleLogout.bind(this)}
                                                as={Link}
                                                to="/"
                                            />]
                                :
                                <Login onLogin={this.handleLogin.bind(this)}/>
                            }
                        </MediaQuery>
                        <MediaQuery query="(max-width: 499px)">
                            <Menu.Item
                                className="borderless"
                                onClick={() => this.setState({...this.state, isSearch: !this.state.isSearch})}>
                                <Icon name="search"/>
                            </Menu.Item>
                            <Menu.Item
                                name="cart"
                                active={activeItem === "cart"}
                                onClick={this.handleItemClick}
                                as={Link}
                                to="/cart"
                            >
                                <Icon name="cart"/>
                            </Menu.Item>
                            {
                                loggedIn
                                ?
                                        [<Menu.Item
                                                name="profile"
                                                active={activeItem === "profile"}
                                                onClick={this.handleItemClick}
                                                as={Link}
                                                to="/profile"
                                            >
                                                <Icon name="user"/>
                                            </Menu.Item>,
                                            <Menu.Item
                                                name="log out"
                                                active={false}
                                                onClick={this.handleLogout.bind(this)}
                                                as={Link}
                                                to="/"
                                            >
                                                <Icon name="log out"/>
                                            </Menu.Item>]
                                :
                                <Login onLogin={this.handleLogin.bind(this)}/>
                            }
                        </MediaQuery>
                    </Menu.Menu>
                </Menu>
                {
                    this.state.isSearch
                    ?
                    <div>
                        <Search
                            className="search-header"
                            fluid
                            placeholder="Search..."/>
                    </div>
                    :
                    ""
                }
                <ReviewModal
                    onReviewSubmit={this.handleReview.bind(this)}
                    onClose={this.handleReviewClose.bind(this)}
                    open = {this.state.ordersToReview.length > 0}
                    order={this.state.ordersToReview[0]}
                />
                </div>
        );
    }
}

export default connect(
    (state: AuthReducerState) => ({
        loggedIn: state.loggedIn
    }),
    dispatch => bindActionCreators({
          dispatchLogin: login,
          dispatchLogout: logout,
    }, dispatch)
)(Header);
