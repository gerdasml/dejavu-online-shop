import * as React from "react";
import { Route, Switch } from "react-router";

import { notification, Spin } from "antd";

import * as api from "../api";
import { Discounts } from "../components/views/Admin/discounts/Discounts";
import { Products } from "../components/views/Admin/product/Products";
import { UserType } from "../model/User";
import { storeToken } from "../utils/token";
import { Admin } from "./views/Admin";
import { Categories } from "./views/Admin/categories/Categories";
import { MenuHeader } from "./views/Admin/layout/Header";
import { LoginForm } from "./views/Admin/login/LoginForm";
import { Orders } from "./views/Admin/orders/Orders";
import { CreateProduct } from "./views/Admin/product/CreateProduct";
import { ImportJob } from "./views/Admin/product/import/ImportJob";
import { ImportJobs } from "./views/Admin/product/import/ImportJobs";
import { SingleProduct } from "./views/Admin/product/SingleProduct";
import { SingleUser } from "./views/Admin/users/SingleUser";
import { Users } from "./views/Admin/users/Users";
import { NotFound } from "./views/NotFound";

import { CreateDiscount } from "./views/Admin/discounts/DiscountCreate";
import { SingleDiscount } from "./views/Admin/discounts/SingleDiscount";

import { connect } from "react-redux";
import { StoreState } from "../redux/reducers";
import { bindActionCreators } from "redux";
import { login, logout } from "../redux/actions/auth";
import { AuthAction, AuthReducerState } from "../redux/reducers/authReducer";
import { isLoggedIn } from "../utils/user";

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
}

interface AuthReducerMethods {
    dispatchLogin: (token?: string) => AuthAction;
    dispatchLogout: () => AuthAction;
}

class AdminMain extends React.Component<AuthReducerState & AuthReducerMethods,AdminMainState> {
    state: AdminMainState = {
        isLoading: true,
    };
    async handlelogin (email: string, password: string) {
        this.setState({...this.state, isLoading: true});
        if(await this.executeLogin(email, password)) {
            if(await isLoggedInAsAdmin()) {
                this.props.dispatchLogin();
            } else {
                notification.error({message: "Failed to authenticate", description: "You are not authorized."});
                this.props.dispatchLogout();
            }
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
            this.props.dispatchLogin();
        } else {
            this.props.dispatchLogout();
        }
        this.setState({...this.state, isLoading: false});
    }
    handleLogout () {
        this.props.dispatchLogout();
    }
    render () {
        return (
            <Spin spinning={this.state.isLoading}>
            {
                this.props.loggedIn
                ?
                <div>
                    <MenuHeader onLogout={this.handleLogout.bind(this)}/>
                    <Switch>
                        <Route path="/admin/products" component={Products}/>
                        <Route path="/admin/product/create" component={CreateProduct}/>
                        <Route path="/admin/product/:id" component={SingleProduct}/>
                        <Route path="/admin/orders" component={Orders}/>
                        <Route path="/admin/users" component={Users}/>
                        <Route path="/admin/user/:id" component={SingleUser} />
                        <Route path="/admin/categories" component={Categories}/>
                        <Route path="/admin/imports/:jobId" component={ImportJob} />
                        <Route path="/admin/imports/" component={ImportJobs} />
                        <Route path="/admin/discounts/" component={Discounts} />
                        <Route path="/admin/discount/create" component={CreateDiscount} />
                        <Route path="/admin/discount/:id" component={SingleDiscount} />
                        <Route path="/admin" component={Admin} />
                        <Route component={NotFound} />
                    </Switch>
                </div>
                : <LoginForm onLogin={this.handlelogin.bind(this)}/>
            }
            </Spin>
        );
    }
}

export default connect(
    (state: StoreState) => ({
        loggedIn: state.auth.loggedIn
    }),
    dispatch => bindActionCreators({
          dispatchLogin: login,
          dispatchLogout: logout,
    }, dispatch),
    undefined,
    { pure: false }
)(AdminMain);
