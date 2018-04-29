import * as React from "react";
import { Route, Switch } from "react-router";

import { Admin } from "./views/Admin";
import { NotFound } from "./views/NotFound";

export const AdminMain = () => (
    <Switch>
        <Route exact path="/" component={Admin} />
        <Route component={NotFound} />
    </Switch>
);
