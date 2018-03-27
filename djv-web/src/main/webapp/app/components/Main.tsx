import * as React from "react";
import { Route, Switch } from "react-router";
import { DrawerMenu } from "./dumb/Menu/Drawer";

import { Link } from "react-router-dom";
import {About, Admin, Cart, Category, Home, Product, Profile, User} from "./views";

export const Main = () => (
    <DrawerMenu>
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>
        <Link to="/admin">Admin</Link>
        <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/about" component={About} />
            <Route path="/product/:id" component={Product} />
            <Route path="/user/:id" component={User}/>
            <Route path="/admin" component={Admin} />
            <Route path="/cart" component={Cart} />
            <Route path="/profile" component = {Profile} />
            <Route path="/category/:name" component={Category} />
            <Route path="*" component={Home} /> {/*TODO: Replace with a NotFound page*/}
        </Switch>
    </DrawerMenu>
);
