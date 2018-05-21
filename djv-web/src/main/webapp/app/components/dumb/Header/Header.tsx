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
                                <Login onLogin={() => this.setState ({...this.state, loggedIn: true}) }/>
                            }
                        </MediaQuery>
                        <MediaQuery query="(max-width: 499px)">
                            <Menu.Item
                                className="borderless">
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
                                <Login onLogin={() => this.setState ({...this.state, loggedIn: true}) }/>
                            }
                        </MediaQuery>
                    </Menu.Menu>
                </Menu>
                <ReviewModal onReviewSubmit={this.handleReview.bind(this)}
                onClose={()  => this.setState({triggerReview: false})}
                open = {this.state.triggerReview} orders={[]}/>
                </div>
        );
    }
}
