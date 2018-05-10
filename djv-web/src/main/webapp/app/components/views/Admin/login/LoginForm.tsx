import * as React from "react";

import { Button, Icon, Input } from "antd";

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
            <div>
                <Input
                    placeholder="Enter your username"
                    prefix={<Icon type="user" className="login-form-icon" />}
                    onChange={e => this.setState({...this.state, email: e.target.value})}
                />
                <Input
                    placeholder="Enter your password"
                    type="password"
                    prefix={<Icon type="lock" className="login-form-icon" />}
                    onChange={e => this.setState({...this.state, password: e.target.value})}
                />
                <Button onClick={() => this.props.onLogin(this.state.email, this.state.password)}>Log in</Button>
            </div>
        );
    }
}
