import * as React from "react";

import {Button, Dropdown, Grid, Icon, Image, Search} from "semantic-ui-react";
import "../../../../style/header.css";

import * as api from "../../../api";

// import logo from "../../assets/dejavu-logo-transperant.png"; // "Cannot find module"
const logo = require("../../../assets/dejavu-logo-transperant.png"); // šiuo metu veikiantis variantas

export class Header extends React.Component <{}, {}> {

    render () {
        return (
            <Grid className="header" id="headerGrid">
                <Grid.Column width={2}>
                    <Image id="logo"
                           title="dejavu"
                           src={logo}
                           alt="Neveikia"
                    />
                </Grid.Column>
                <Grid.Column width={8}>
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
                <Grid.Column width={6}>
                    <div id="threeHeaderButtons">
                        <Dropdown   simple
                                    className="headerButton"
                                    trigger={
                            <Button icon
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
                        <Button className="headerButton"
                                icon
                                size="medium"
                                >
                            CART
                            <br/>
                            <Icon name="cart" size="big"/>
                        </Button>
                        <Button className="headerButton"
                                icon
                                size="medium"
                                >
                            LOG&nbsp;IN
                            <br/>
                            <Icon name="user circle outline" size="big"/>
                        </Button>
                    </div>
                </Grid.Column>
            </Grid>
        );
    }
}

const getUser = async () => {
    const tkn = await api.login("admin@email.com", "password");
    if(api.isBanned(tkn)) {
        console.log("You banned bro");
        return undefined;
    }
    localStorage.setItem("accessToken", tkn.token);
    const users = await api.getUsers();
    console.log(users);
    return users;
};

getUser();
