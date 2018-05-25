import * as React from "react";
import { Route, Switch } from "react-router";
import { DrawerMenu } from "./dumb/Menu/Drawer";

import { Link } from "react-router-dom";
import { NotFound } from "./views/NotFound";
import {About, Cart, Category, Home, Product, Profile, User} from "./views/Regular";

import { Footer } from "../components/dumb/Footer/Footer";
import Header from "../components/dumb/Header/Header";
import { ProductSearchResults } from "./views/Regular/ProductSearchResults";

export const RegularMain = () => (
    <div>
        <Header/>
        <DrawerMenu>
            <Switch>
                <Route exact path="/" component={Home} />
                <Route path="/about" component={About} />
                <Route path="/product/search" component={ProductSearchResults}/>
                <Route path="/product/:identifier+" component={Product} />
                <Route path="/user/:id" component={User}/>
                <Route path="/cart" component={Cart} />
                <Route path="/profile" component = {Profile} />
                <Route path="/category/:identifier+" component={Category} />
                <Route component={NotFound} />
            </Switch>
        </DrawerMenu>
        <Footer/>
    </div>
);
