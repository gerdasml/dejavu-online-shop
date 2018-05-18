import * as React from "react";
import {NavLink, Link} from "react-router-dom";

import {Button, Dropdown, Grid, Icon, Image, List, Menu, Search} from "semantic-ui-react";
import "../../../../style/header.css";
import {Login} from "../Login/Login";

import { clearToken, getToken } from "../../../utils/token";
import { Logo } from "./Logo";

interface HeaderState {
    loggedIn: boolean;
    activeItem: String;
}

export class Header extends React.Component <{}, HeaderState> {
    state: HeaderState = {
        activeItem: "home",
        loggedIn: getToken() !== null
    };

    handleLogout = () => {
        clearToken();
        this.setState ({...this.state, loggedIn: false});
    }
    handleItemClick = (e: any, { name }: any) => this.setState({ ...this.state, activeItem: name });

    render () {
        const { activeItem } = this.state;
        return (
                <Menu id="menuHeader">
                    <Menu.Item
                        className="logoContainer"
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
                                value="Search..."
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
        );
    }
}
