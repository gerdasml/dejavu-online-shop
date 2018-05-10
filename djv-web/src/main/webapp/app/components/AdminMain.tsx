import * as React from "react";
import { Route, Switch } from "react-router";

import { Products } from "../components/views/Admin/product/Products";
import { Admin } from "./views/Admin";
import { Categories } from "./views/Admin/categories/Categories";
import { MenuHeader } from "./views/Admin/layout/Header";
import { LoginForm } from "./views/Admin/login/LoginForm";
import { Orders } from "./views/Admin/orders/Orders";
import { CreateProduct } from "./views/Admin/product/CreateProduct";
import { SingleProduct } from "./views/Admin/product/SingleProduct";
import { SingleUser } from "./views/Admin/users/SingleUser";
import { Users } from "./views/Admin/users/Users";
import { NotFound } from "./views/NotFound";

const isLoggedInAsAdmin = () => false;

export const AdminMain = () => (
    isLoggedInAsAdmin ()
    ?
    <div>
        <MenuHeader/>
        <Switch>
            <Route path="/admin/product/create" component={CreateProduct}/>
            <Route path="/admin/product/:id" component={SingleProduct}/>
            <Route path="/admin/orders" component={Orders}/>
            <Route path="/admin/users" component={Users}/>
            <Route path="/admin/user/:id" component={SingleUser} />
            <Route path="/admin/categories" component={Categories}/>
            <Route path="/" component={Admin} />
            <Route component={NotFound} />
        </Switch>
    </div>
    : <LoginForm/>
);
