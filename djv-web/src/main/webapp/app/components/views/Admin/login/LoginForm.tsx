import * as React from "react";

import { Button, Icon, Input } from "antd";

import "../../../../../style/admin/login.css";

export interface LoginFormsState {
    email: string;
    password: string;
}

export interface LoginFormsProps {
    onLogin: (email: string, password: string) => void;
}
export class LoginForm extends React.Component<LoginFormsProps, LoginFormsState> {
    state: LoginFormsState = {
        email: "",
        password: ""
    };
    render () {
        return (
            <div id="loginForm">
                <Input className="loginInput"
                    onPressEnter={() => this.props.onLogin(this.state.email, this.state.password)}
                    placeholder="Enter your username"
                    prefix={<Icon type="user" className="login-form-icon" />}
                    onChange={e => this.setState({...this.state, email: e.target.value})}
                /><br/>
                <Input className="loginInput"
                    onPressEnter={() => this.props.onLogin(this.state.email, this.state.password)}
                    placeholder="Enter your password"
                    type="password"
                    prefix={<Icon type="lock" className="login-form-icon" />}
                    onChange={e => this.setState({...this.state, password: e.target.value})}
                /><br/>
                <Button id="loginButton"
                onClick={() => this.props.onLogin(this.state.email, this.state.password)}>Log in</Button>
            </div>
        );
    }
}
