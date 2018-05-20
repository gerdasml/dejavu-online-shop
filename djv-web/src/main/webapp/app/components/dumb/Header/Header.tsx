import * as React from "react";
import {Link, NavLink} from "react-router-dom";

import {Menu, Search} from "semantic-ui-react";
import "../../../../style/header.css";
import {Login} from "../Login/Login";

import { clearToken, getToken } from "../../../utils/token";
import { Logo } from "./Logo";

import {ReviewModal} from "../Order/ReviewModal";

import {Review} from "../../../model/Review";

import * as api from "../../../api";
import { Order } from "../../../model/Order";

interface HeaderState {
    loggedIn: boolean;
    activeItem: String;
    ordersToReview: Order[];
}

export class Header extends React.Component <{}, HeaderState> {
    state: HeaderState = {
        activeItem: "home",
        loggedIn: getToken() !== null,
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
                        className="logoContainer"
                        id="logoHeaderColumn"
                        active={activeItem === "menu"}
                        onClick={this.handleItemClick}>
                    <NavLink to="/">
                        <Logo size="large" id="large-logo" />
                        <Logo size="small" id="small-logo" />
                    </NavLink>
                    </Menu.Item>
                    <Menu.Menu position="right">
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
                </Menu.Menu>
                </Menu>
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
