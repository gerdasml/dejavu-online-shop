import * as React from "react";

import { Button, Icon, Input, notification } from "antd";
import * as api from "../../../../api";

export interface LoginFormsState {
    isAdmin: boolean;
    email: string;
    password: string;
}

export class LoginForm extends React.Component<{}, LoginFormsState> {
    isAdmin = false;
    email = "";
    password = "";
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
    async handleLogin (email: string, password: string) {
        const response = await api.auth.login(email, password);
        if(api.isError(response)) {
            notification.error({message: response.message, description: "The error occured"});
            return;
        }
        this.setState({isAdmin: true});
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
                <Button onClick={() => this.handleLogin(this.state.email, this.state.password)}>Log in</Button>
            </div>
        );
    }
}
