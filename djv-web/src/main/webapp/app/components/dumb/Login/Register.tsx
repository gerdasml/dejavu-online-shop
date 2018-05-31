import * as React from "react";

import {Button, Form, Message, Modal } from "semantic-ui-react";

import * as api from "../../../api";
import { Address } from "../../../model/Address";

import {AddressInput} from "../Address/AddressInput";

import "../../../../style/login.css";
import { message } from "antd";

interface RegistrationState {
    loading: boolean;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    phone: string;
    address: Address;
    error: string;
    open: boolean;
    repeatPassword: string;
}

export class Register extends React.Component <{}, RegistrationState> {
    state: RegistrationState = {
        address: {},
        email: "",
        error: "",
        firstName: "",
        lastName: "",
        loading: false,
        open: false,
        password: "",
        phone: "",
        repeatPassword: ""
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
    handleRepeatPasswordInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, repeatPassword: value
        });
    }
    handleFirstNameInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, firstName: value
        });
    }
    handleLastNameInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, lastName: value
        });
    }
    handlePhoneInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, phone: value
        });
    }
    handleOpen = () => this.setState({...this.state, open: true});
    handleClose = () => this.setState({...this.state, open: false, error: ""});
    register = async (): Promise<void> => {
        this.setState({
            ...this.state, loading: true
        });
        if (this.state.password !== this.state.repeatPassword) {
            this.setState({
                ...this.state,
                error: "The passwords do not match"
            });
            return;
        }
        const user = {
            address: this.state.address,
            email: this.state.email,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            password: this.state.password,
            phone: this.state.phone
        };
        const response = await api.auth.register(user);
        if(api.isError(response)) {
            this.setState({
                ...this.state, error: response.message, loading: false
            });
            return undefined;
        }
        this.setState({
            ...this.state, error: "", loading: false
        });
        message.success("Registration successful!");
        this.handleClose();
    }
    render () {
        return (
            <Modal trigger={<Button icon
                size="medium"
                className="login-window-button"
                onClick={this.handleOpen.bind(this)}
            >
                REGISTER
                </Button>}
                open={this.state.open}
                onClose={this.handleClose.bind(this)}
            >
            <Modal.Header className="modal-header"><h2>Register</h2></Modal.Header>
                <Modal.Content id="registerModal">
                    <Form size = "small"
                        loading={this.state.loading}
                        error={this.state.error !== ""}
                        onSubmit={this.register.bind(this)}
                    >
                        <Form.Field inline>
                            <label>Email<span className="redStar">*</span></label>
                            <input  type="email"
                                    placeholder="email"
                                    onChange={this.handleEmailInput.bind(this)}
                                    required/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Password<span className="redStar">*</span></label>
                            <input  type="password"
                                    placeholder="********"
                                    onChange={this.handlePasswordInput.bind(this)}
                                    required/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Repeat password<span className="redStar">*</span></label>
                            <input  type="password"
                                    placeholder="********"
                                    onChange={this.handleRepeatPasswordInput.bind(this)}
                                    required/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>First name</label>
                            <input  type="text"
                                    placeholder="First Name"
                                    onChange={this.handleFirstNameInput.bind(this)}/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Last name</label>
                            <input  type="text"
                                    placeholder="Last Name"
                                    onChange={this.handleLastNameInput.bind(this)}/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Phone number</label>
                            <input  type="text"
                                    placeholder="+370********"
                                    onChange={this.handlePhoneInput.bind(this)}/>
                        </Form.Field>
                        <AddressInput formSize="small"
                            address={this.state.address}
                            onAddressChange={newAddr => this.setState({...this.state, address: newAddr})}/>
                        <Message
                            error
                            header="Registration failed"
                            content={this.state.error}
                        />
                        <Button
                            className="registration-cancel-button"
                            onClick={this.handleClose.bind(this)}>Cancel</Button>
                        <Button
                            type="submit"
                            className="registration-save-button">Register</Button>
                    </Form>
                </Modal.Content>
            </Modal>
        );
    }
}
