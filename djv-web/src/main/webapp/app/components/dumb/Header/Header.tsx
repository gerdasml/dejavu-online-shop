import * as React from "react";
import {NavLink} from "react-router-dom";

import {Button, Dropdown, Grid, Icon, Image, Search} from "semantic-ui-react";
import "../../../../style/header.css";
import {Login} from "../Login/Login";

import { clearToken, getToken } from "../../../utils/token";
import { Logo } from "./Logo";

interface HeaderState {
    loggedIn: boolean;
}

export class Header extends React.Component <{}, HeaderState> {
    state = {loggedIn: getToken() !== null};

    handleLogout = () => {
        clearToken();
        this.setState ({loggedIn: false});

    }

    render () {
        return (
            <Grid id="headerGrid">
                <Grid.Column width={2} id="logoHeaderColumn">
                    <NavLink to="/">
                        <Logo size="large" id="large-logo" />
                        <Logo size="small" id="small-logo" />
                    </NavLink>
                </Grid.Column>
                <Grid.Column width={8} id="infoSearchColumn">
                    <Grid.Row id="mailPhoneRow">
                        <div    className="headerInfo"
                                id="mail">
                            <Icon   title="dejavu.psk@gmail.com"
                                    name="mail outline" />
                            dejavu.psk@gmail.com
                        </div>
                        <div    className="headerInfo"
                                id="phone">
                            <Icon   title="+37060000000"
                                    name="phone" />
                            +37060000000
                        </div>
                    </Grid.Row>
                    <Grid.Row id="searchRow">
                        <Search id="searchBar"
                                value="Search..."
                                noResultsMessage="Not implemented exception. xD"
                                size="mini"
                                fluid
                        >
                        </Search>
                    </Grid.Row>
                </Grid.Column>
                <Grid.Column width={6} id="threeHeaderButtons">
                    <Dropdown   className="headerButton"
                                simple
                                trigger={
                        <Button className="headerButton"
                                icon
                                size="medium"
                        >
                            ABOUT
                            <br/>
                            <Icon name="info" size="big"/>
                        </Button>
                    } icon={null}>
                        <Dropdown.Menu>
                            <Dropdown.Item icon="question" text="F.A.Q." />
                            <Dropdown.Divider />
                            <Dropdown.Item icon="ship" text="Shipping" />
                            <Dropdown.Divider />
                            <Dropdown.Item icon="shopping basket" text="How to buy?" />
                            <Dropdown.Divider />
                            <Dropdown.Item icon="exclamation" text="RULES" />
                            <Dropdown.Divider />
                            <Dropdown.Item icon="wait" text="Warranty" />
                            <Dropdown.Divider />
                            <Dropdown.Item icon="info" text="About Us" />
                        </Dropdown.Menu>
                    </Dropdown>
                    {/*  TODO: this generates a warning, if you fix it, fix the css as well */}
                    <Button className="headerButton"
                            icon
                            size="medium"
                            as={NavLink}
                            to="/cart"
                            >
                        CART
                        <br/>
                        <Icon name="cart" size="big"/>
                    </Button>
                    {
                        this.state.loggedIn
                        ? <Dropdown className="headerButton"
                        simple
                        direction="left"
                        trigger={
                            <Button className="headerButton"
                            icon
                            size="medium"
                            >
                                User
                                <br/>
                                <Icon name="user circle outline" size="big"/>
                            </Button>
                        } icon={null}>
                            <Dropdown.Menu>
                                <Dropdown.Item
                                    icon="address card outline"
                                    text="View profile"
                                    as={NavLink}
                                    to="/profile"
                                />
                                <Dropdown.Divider />
                                <Dropdown.Item
                                    icon="sign out"
                                    text="Log&nbsp;out"
                                    as={NavLink}
                                    to="/"
                                    onClick={this.handleLogout.bind(this)}
                                />
                            </Dropdown.Menu>
                        </Dropdown>
                        : <Login onLogin={() => this.setState ({loggedIn: true}) }/>
                    }
                </Grid.Column>
            </Grid>
        );
    }
}
