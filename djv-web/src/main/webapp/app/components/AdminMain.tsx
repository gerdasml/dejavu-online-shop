import * as React from "react";
import { Route, Switch } from "react-router";

import { Admin } from "./views/Admin";
import { Categories } from "./views/Admin/categories/Categories";
import { MenuHeader } from "./views/Admin/layout/Header";
import { Orders } from "./views/Admin/orders/Orders";
import { SingleProduct } from "./views/Admin/product/SingleProduct";
import { SingleUser } from "./views/Admin/users/SingleUser";
import { Users } from "./views/Admin/users/Users";
import { NotFound } from "./views/NotFound";

export const AdminMain = () => (
    <div>
        <MenuHeader/>
        <Switch>
            <Route path="/admin/product" component={SingleProduct}/>
            <Route path="/admin/orders" component={Orders}/>
            <Route path="/admin/users" component={Users}/>
            <Route path="/admin/user/:id" component={SingleUser} />
            <Route path="/admin/categories" component={Categories}/>
            <Route path="/" component={Admin} />
            <Route component={NotFound} />
        </Switch>
    </div>
);
