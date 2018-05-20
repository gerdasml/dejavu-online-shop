import * as React from "react";
import {Link, NavLink} from "react-router-dom";

import {Menu, Search} from "semantic-ui-react";
import "../../../../style/header.css";
import {Login} from "../Login/Login";

import { clearToken, getToken } from "../../../utils/token";
import { Logo } from "./Logo";

import {ReviewModal} from "../Order/ReviewModal";

import {Review} from "../../../model/Review";

/*const fakeOrderItems: OrderItem[] = [
    {
        amount: 5,
        product: products[0]
    },
    {
        amount: 4,
        product: products[1]
    },
    {
        amount: 15,
        product: products[2]
    }
];

const fakeOrders: Order[] = [
    {
        id: 1,
        items: fakeOrderItems
    },
    {
        id: 2,
        items: [fakeOrderItems[1]]
    },
    {
        id: 3,
        items: [fakeOrderItems[2]]
    }
];*/

interface HeaderState {
    loggedIn: boolean;
    activeItem: String;
    triggerReview: boolean;
}

export class Header extends React.Component <{}, HeaderState> {
    state: HeaderState = {
        activeItem: "home",
        loggedIn: getToken() !== null,
        triggerReview: false
    };

    handleLogout = () => {
        clearToken();
        this.setState ({...this.state, loggedIn: false});
    }
    handleItemClick = (e: any, { name }: any) => this.setState({ ...this.state, activeItem: name });

    handleReview = (orderId: number, review: Review) => {
        console.log("order: " + orderId + review.comment + review.rating);
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
                            <Login onLogin={() => this.setState ({...this.state, loggedIn: true}) }/>
                        }
                </Menu.Menu>
                </Menu>
                <ReviewModal onReviewSubmit={this.handleReview.bind(this)}
                onClose={()  => this.setState({triggerReview: false})}
                open = {this.state.triggerReview} orders={[]}/>
                </div>
        );
    }
}
