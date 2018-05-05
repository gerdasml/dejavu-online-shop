import * as React from "react";

import {Button, Form, Message, Modal } from "semantic-ui-react";

import * as api from "../../../api";
import { Address } from "../../../model/Address";

import "../../../../style/login.css";

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
        phone: ""
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
    handleStreetInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        const newAddress = {...this.state.address};
        newAddress.street = value;
        this.setState({...this.state, address: newAddress});
    }
    handleCityInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        const newAddress = {...this.state.address};
        newAddress.city = value;
        this.setState({...this.state, address: newAddress});
    }
    handleZipCodeInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        const newAddress = {...this.state.address};
        newAddress.zipCode = value;
        this.setState({...this.state, address: newAddress});
    }
    handleCountryInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        const newAddress = {...this.state.address};
        newAddress.country = value;
        this.setState({...this.state, address: newAddress});
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
        const user = {
            address: {},
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
        this.handleClose();
    }
    render () {
        return (
            <Modal trigger={<Button icon
                size="medium"
                onClick={this.handleOpen.bind(this)}
            >
                REGISTER
                </Button>}
                open={this.state.open}
                onClose={this.handleClose.bind(this)}
            >
                <Modal.Content id="registerModal">
                    <h2>Registration</h2>
                    <Form
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
                                    onChange={this.handlePasswordInput.bind(this)}
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
                        <Form.Field inline>
                            <label>Street</label>
                            <input  type="text"
                                    placeholder="street"
                                    onChange={this.handleStreetInput.bind(this)}/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>City</label>
                            <input  type="text"
                                    placeholder="city"
                                    onChange={this.handleCityInput.bind(this)}/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Country</label>
                            <select onChange={this.handleCountryInput.bind(this)}>
                                <option value="Lithuania">Lithuania</option>
                                <option value="United Kingdom">United Kingdom</option>
                            </select>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Zip Code</label>
                            <input  type="text"
                                    placeholder="zip code"
                                    onChange={this.handleZipCodeInput.bind(this)}/>
                        </Form.Field>
                        <Message
                            error
                            header="Registration failed"
                            content={this.state.error}
                        />
                        <Button onClick={this.handleClose.bind(this)}>Cancel</Button>
                        <Button type="submit">Register</Button>
                    </Form>
                </Modal.Content>
            </Modal>
        );
    }
}
