import * as React from "react";
import { Route, Switch } from "react-router";

import AdminMain from "./AdminMain";
import { RegularMain } from "./RegularMain";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { login, logout } from "../redux/actions/auth";

import * as api from "../api";

export const Layout = connect(
    () => ({}),
    dispatch => bindActionCreators({
        dispatchLogin: login,
        dispatchLogout: logout,
    }, dispatch),
    undefined,
    { pure: false }
)(class extends React.Component<any, never> {
    async componentWillMount () {
        const response = await api.auth.isTokenValid();
        if (!api.isError(response) && response) {
            this.props.dispatchLogin();
        }
    }
    render () {
        return (
            <Switch>
                <Route path="/admin" component={AdminMain} />
                <Route path="/" component={RegularMain} />
            </Switch>
        );
    }
});
