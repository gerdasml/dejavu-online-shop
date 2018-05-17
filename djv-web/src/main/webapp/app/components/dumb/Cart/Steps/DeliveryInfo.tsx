import * as React from "react";

import { Button, Form } from "semantic-ui-react";

import {AddressInput} from "../../Address/AddressInput";

import "../../../../../style/cart.css";
import { Address } from "../../../../model/Address";
import { User } from "../../../../model/User";

interface DeliveryInfoProps {
    onStepComplete: () => void;
    onUserInfoChange: (user: User) => void;
    user: User;
}
export class DeliveryInfo extends React.Component <DeliveryInfoProps, {}> {
    handleNewAddress = (newAddress: Address) => {
        const newUser = this.props.user;
        newUser.address = newAddress;
        this.props.onUserInfoChange(newUser);
    }
    handleFirstNameChange = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        const newUser = { ...this.props.user };
        newUser.firstName = value;
        this.props.onUserInfoChange(newUser);
    }
    handleLastNameChange = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        const newUser = { ...this.props.user };
        newUser.lastName = value;
        this.props.onUserInfoChange(newUser);
    }
    handleEmailChange = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        const newUser = { ...this.props.user };
        newUser.email = value;
        this.props.onUserInfoChange(newUser);
    }
    handlePhoneChange = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        const newUser = { ...this.props.user };
        newUser.phone = value;
        this.props.onUserInfoChange(newUser);
    }

    render () {
        return (
            <Form size = "big" id="deliveryInfoForm">
                <Form.Field inline>
                    <label>First name</label>
                    <input
                        type="text"
                        placeholder="John"
                        defaultValue={this.props.user.firstName}
                        onChange={this.handleFirstNameChange.bind(this)}
                    />
                </Form.Field>
                <Form.Field inline>
                    <label>Last name</label>
                    <input
                        type="text"
                        placeholder="Doe"
                        defaultValue={this.props.user.lastName}
                        onChange={this.handleLastNameChange.bind(this)}
                />
                </Form.Field>
                <Form.Field inline>
                    <label>Email</label>
                    <input
                        type="email"
                        placeholder="email@email.com"
                        defaultValue={this.props.user.email}
                        onChange={this.handleEmailChange.bind(this)}
                    />
                </Form.Field>
                <Form.Field inline>
                    <label>Phone number</label>
                    <input
                        type="text"
                        placeholder="+37061234567"
                        defaultValue={this.props.user.phone}
                        onChange={this.handlePhoneChange.bind(this)}
                />
                </Form.Field>
                <AddressInput
                    formSize="big"
                    address={this.props.user.address}
                    onAddressChange={ newAddress => this.handleNewAddress(newAddress)} />
                <Button type="submit" positive onClick={this.props.onStepComplete}>Next</Button>
            </Form>
        );
    }
}
