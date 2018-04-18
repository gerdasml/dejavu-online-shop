import * as React from "react";

import {Button, Dropdown, Grid, Icon, Image, Search} from "semantic-ui-react";
import "../../../../style/header.css";
import {Login} from "../Login/Login";

import * as api from "../../../api";
import { Payment } from "../../../model/Payment";
import { storeToken, clearToken } from "../../../utils/token";

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
                        <Login/>
                    </div>
                </Grid.Column>
            </Grid>
        );
    }
}

/**
 * TODO: remove all of the functions and function calls below
 * after the connection between front- and back end is made.
 * These functions serve only as examples of how to use the
 * API functions.
 */
const getUser = async () => {
    const tkn = await api.auth.login("admin@email.com", "password");
    if(api.isError(tkn)) {
        console.error(tkn);
        return undefined;
    } else {
        console.log(tkn);
    }

    if(api.auth.isBanned(tkn)) {
        console.log("You banned bro");
        return undefined;
    }
    storeToken(tkn.token);
    const users = await api.user.getUsers();
    if(api.isError(users)) {
        console.error(users);
    } else {
        console.log(users);
    }
    return users;
};

const getImages = async () => {
    const images = await api.image.getImages();
    if(api.isError(images)) {
        console.error(images);
    } else {
        console.log(images);
    }

    return images;
};

const getImage = async (id: number) => {
    const image = await api.image.getImage(id);
    if(api.isError(image)) {
        console.error(image);
    } else {
        console.log(image);
    }
    return image;
};

const validatePayment = async (payment: Payment) => {
    const errors = await api.payment.validate(payment);
    if(api.isError(errors)) {
        console.error(errors);
    } else {
        console.log(errors);
    }
    return errors;
};

getUser();
getImages();
getImage(1);
getImage(2);
validatePayment({
    amount: 0,
    card: {
        cvv: "123",
        holder: "Vardas",
        number: "4111111111111111"
    },
    expiration: {
        month: 9,
        year: 2018
    }
});
