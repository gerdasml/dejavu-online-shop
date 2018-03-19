import * as React from "react";

import {Button, Container, Dropdown, Grid, Icon, Image, Search} from "semantic-ui-react";
import "../../../style/header.css";

// import logo from "../../assets/dejavu-logo-transperant.png"; // "Cannot find module"
const logo = require("../../assets/dejavu-logo-transperant.png"); // šiuo metu veikiantis variantas

export default class Header extends React.Component <{}, {}> {

    render () {
        return (
            <Grid className="header" id="headerGrid">
                <Grid.Column width={2}>
                    <Image id="logo"
                           src={logo}
                           alt="Neveikia"
                    />
                </Grid.Column>
                <Grid.Column width={8}>
                    <Grid.Row id="mailPhoneRow">
                        <div id="mail">
                            <Icon name="mail outline" />
                            dejavu.psk@gmail.com
                        </div>
                        <div id="phone">
                            <Icon name="phone" />
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
                        <Dropdown trigger={
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
                        <Button icon
                                size="medium"
                                >
                            CART
                            <br/>
                            <Icon name="cart" size="big"/>
                        </Button>
                        <Button icon
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
