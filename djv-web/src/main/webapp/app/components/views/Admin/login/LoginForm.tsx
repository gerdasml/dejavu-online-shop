import * as React from "react";

import { Button, Icon, Input, notification } from "antd";
import * as api from "../../../../api";
import { UserType } from "../../../../model/User";

export interface LoginFormsState {
    isAdmin: boolean;
    email: string;
    password: string;
}

export interface LoginFormsProps {
    onLogin: (email: string, password: string) => void;
}
export class LoginForm extends React.Component<LoginFormsProps, LoginFormsState> {
    state: LoginFormsState = {
        email: "",
        isAdmin: false,
        password: ""
    };
    handleEmailInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, email: value
        });
    }
    handlePasswordInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, password: value
        });
    }
    render () {
        return (
            <div>
                <Input
                    placeholder="Enter your username"
                    //TODO: move to css
                    prefix={<Icon type="user" style={{ color: "rgba(0,0,0,.25)" }} />}
                    onChange={this.handleEmailInput.bind(this)}
                />
                <Input
                    placeholder="Enter your password"
                    type="password"
                    prefix={<Icon type="lock" style={{ color: "rgba(0,0,0,.25)"}}/>}
                    onChange={this.handlePasswordInput.bind(this)}
                />
                <Button onClick={() => this.props.onLogin(this.state.email, this.state.password)}>Log in</Button>
            </div>
        );
    }
}
