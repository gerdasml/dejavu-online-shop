import * as React from "react";
import { Route, Switch } from "react-router";
import {Image} from "semantic-ui-react";
import { DrawerMenu } from "./dumb/Menu/Drawer";

import {About, Home, Product, User, Admin, Cart, Profile, Category} from "./views";
import { Link } from "react-router-dom";

export const Main = () => (
    <DrawerMenu>
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
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>
        <Link to="/admin">Admin</Link>
    </DrawerMenu>
);
