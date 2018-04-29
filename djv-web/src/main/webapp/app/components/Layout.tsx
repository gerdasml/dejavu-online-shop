import * as React from "react";
import { Route, Switch } from "react-router";

import { AdminMain } from "./AdminMain";
import { RegularMain } from "./RegularMain";

export const Layout = () => (
    <Switch>
        <Route path="/admin" component={AdminMain} />
        <Route path="/" component={RegularMain} />
    </Switch>
);
