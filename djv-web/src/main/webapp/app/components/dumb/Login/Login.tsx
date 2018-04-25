import * as React from "react";
import { Button, Form, Grid, Icon, Message, Modal, Segment } from "semantic-ui-react";
import * as api from "../../../api";
import { storeToken } from "../../../utils/token";
import {Register} from "./Register";

import "../../../../style/login.css";

interface LoginState {
    loading: boolean;
    email: string;
    password: string;
    error: string;
    open: boolean;
}

export class Login extends React.Component {
    state = {
        email: "",
        error: "",
        loading: false,
        open: false,
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
    handleOpen = () => this.setState({...this.state, open: true});
    handleClose = () => this.setState({...this.state, open: false});
    login = async (): Promise<void> => {
        this.setState({
            ...this.state, loading: true
        });
        const tkn = await api.auth.login(this.state.email, this.state.password);
        if(api.isError(tkn)) {
            this.setState({
                ...this.state, error: tkn.message, loading: false
            });
            return undefined;
        }
        if(api.auth.isBanned(tkn)) {
            this.setState({
                ...this.state, error: "You are banned", loading: false
            });
            return undefined;
        }
        storeToken(tkn.token);
        this.setState({
            ...this.state, error: "", loading: false
        });
        this.handleClose();
    }
    render () {
        return (
            <Modal trigger={<Button icon
                                    size="medium"
                                    onClick={this.handleOpen.bind(this)}
            >
                LOG&nbsp;IN
                <br/>
                <Icon name="user circle outline" size="big"/>
            </Button>}
            open={this.state.open}
            onClose={this.handleClose.bind(this)}
            >
                <Modal.Content id="loginModal">
                    <Grid columns={2} divided>
                        <Grid.Column>
                            <Segment basic>
                                <h3> Already registered user?<br/>Log in:</h3>
                                <Form 
                                    loading={this.state.loading}
                                    error={this.state.error !== ""}
                                    onSubmit={this.login.bind(this)}
                                >
                                    <Form.Field>
                                        <label>Email address</label>
                                        <input
                                            type="email"
                                            placeholder="email"
                                            onChange={this.handleEmailInput.bind(this)}
                                            required
                                        />
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Password</label>
                                        <input
                                            type="password"
                                            placeholder="********"
                                            onChange={this.handlePasswordInput.bind(this)}
                                            required
                                        />
                                    </Form.Field>
                                    <Message
                                        error
                                        header="Login failed"
                                        content={this.state.error}
                                    />
                                    <Button type="submit">
                                        Log in
                                    </Button>
                                </Form>
                            </Segment>
                        </Grid.Column>
                        <Grid.Column>
                            <Segment basic>
                                <h3> Haven't used before?<br/>Sign up:</h3>
                                <div className="register">
                                    <Register/>
                                </div>
                            </Segment>
                        </Grid.Column>
                    </Grid>
                </Modal.Content>
            </Modal>
        );
    }
}
