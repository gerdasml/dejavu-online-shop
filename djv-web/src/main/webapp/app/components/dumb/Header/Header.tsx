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

interface HeaderState {
    loggedIn: boolean;
    activeItem: String;
    isSearch: boolean;
    ordersToReview: Order[];
}

export class Header extends React.Component <{}, HeaderState> {
    state: HeaderState = {
        activeItem: "home",
        loggedIn: getToken() !== null,
        isSearch: false,
        ordersToReview: []
    };

    handleLogout = () => {
        clearToken();
        this.setState ({...this.state, loggedIn: false});
    }

    handleLogin = async () => {
        this.setState ({...this.state, loggedIn: true});
        const response = await api.review.getOrdersToReview();
        if (api.isError(response)) {
            // Don't show an error if the review fetch failed
            return;
        }
        this.setState({...this.state, ordersToReview: response});
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

    render () {
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
                                this.state.loggedIn
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
                                this.state.loggedIn
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
