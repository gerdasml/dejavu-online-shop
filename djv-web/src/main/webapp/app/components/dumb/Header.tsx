import * as React from "react";

import {Button, Grid, Icon, Image, Search} from "semantic-ui-react";
import "../../../style/header.css";
// import logo from "../../assets/dejavu-logo-transperant.png";

const logo = require("../../assets/dejavu-logo-transperant.png");

export default class Header extends React.Component <{}, {}> {

    render () {
        return (
            <Grid className="header">
                <Grid.Column width={2}>
                    <Image id="logo"
                           src={logo}
                           alt="Neveikia"
                    />
                </Grid.Column>
                <Grid.Column width={7}>
                    <Grid>
                        <Grid.Row>
                            <Grid.Column width={8}>
                                <Icon name="mail outline" />
                                dejavu.psk@gmail.com
                            </Grid.Column>
                            <Grid.Column width={8}>
                                <Icon name="phone" />
                                +37060000000
                            </Grid.Column>
                        </Grid.Row>
                        <Grid.Row>
                            <Search id="searchBar"
                                    value="Search..."
                                    noResultsMessage="Not implemented exception. xD"
                                    size="mini"
                            >
                            </Search>
                        </Grid.Row>
                    </Grid>
                </Grid.Column>
                <Grid.Column width={4}>
                </Grid.Column>
                <Grid.Column width={3}>
                    <Grid>
                        <Grid.Column  width={5}>
                            <Button icon
                                    size="massive"
                                    >
                                ABOUT US
                                <br/>
                                <Icon name="info" />
                            </Button>
                        </Grid.Column>
                        <Grid.Column width={5}>
                            <Button icon
                                    size="massive"
                                    >
                                CART
                                <br/>
                                <Icon name="cart" />
                            </Button>
                        </Grid.Column>
                        <Grid.Column width={5}>
                            <Button icon
                                    size="massive"
                                    >
                                LOG IN
                                <br/>
                                <Icon name="user circle outline" />
                            </Button>
                        </Grid.Column>
                    </Grid>
                </Grid.Column>
            </Grid>
        );
    }
}
