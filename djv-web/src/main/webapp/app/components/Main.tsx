import * as React from "react";
import { Route, Switch } from "react-router";
import {Image} from "semantic-ui-react";
import { DrawerMenu } from "./dumb/Menu/Drawer";

import {Home} from "./views/Home";
import {About} from "./views/About";
import { Link } from "react-router-dom";

export const Main = () => (
    <DrawerMenu>
        <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/about" component={About} />
            <Route path="*" component={Home} /> {/*TODO: Replace with a NotFound page*/}
        </Switch>
        <Link to="/about">Go</Link>
    </DrawerMenu>
);
