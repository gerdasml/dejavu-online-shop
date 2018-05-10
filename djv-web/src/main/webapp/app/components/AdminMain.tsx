import * as React from "react";
import { Route, Switch } from "react-router";

import { notification, Spin } from "antd";

import * as api from "../api";
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
import { UserType } from "../model/User";
import { storeToken } from "../utils/token";

const isLoggedInAsAdmin = async () => {
    const userResponse = await api.user.getProfile();
    if(api.isError(userResponse)) {
        notification.error({message: "Failed to login", description: userResponse.message});
        return false;
    }
    return userResponse.type === UserType.ADMIN;
};

export interface AdminMainState {
    isLoading: boolean;
    isAdmin: boolean;
}

export class AdminMain extends React.Component<{},AdminMainState> {
    state: AdminMainState = {
        isAdmin: false,
        isLoading: true
    };
    async handlelogin (email: string, password: string) {
        this.setState({...this.state, isLoading: true});
        if(await this.executeLogin(email, password) && await isLoggedInAsAdmin()) {
                this.setState({...this.state, isAdmin: true});
        }
        this.setState({...this.state, isLoading: false});
    }
    executeLogin = async (email: string, password: string) => {
        const response = await api.auth.login(email, password);
        if(api.isError(response)) {
            notification.error({message: "Failed to login", description: response.message});
            return false;
        }
        if(api.auth.isBanned(response)) {
            notification.error({message: "Failed to login", description: "You are banned"});
            return false;
        }
        storeToken(response.token);
        return true;
    }
    async componentDidMount () {
        if (await isLoggedInAsAdmin()) {
            this.setState({...this.state, isAdmin: true});
        }
        this.setState({...this.state, isLoading: false});
    }
    render () {
        return (
            <Spin spinning={this.state.isLoading}>
            {
                this.state.isAdmin
                ?
                <div>
                    <MenuHeader/>
                    <Switch>
                        <Route path="/admin/products" component={Products}/>
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
                : <LoginForm onLogin={this.handlelogin.bind(this)}/>
            }
            </Spin>
        );
    }
}
