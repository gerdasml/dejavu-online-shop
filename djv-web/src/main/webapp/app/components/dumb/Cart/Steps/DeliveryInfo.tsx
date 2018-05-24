import * as React from "react";

import { Button, Form, Icon } from "semantic-ui-react";

import {AddressInput} from "../../Address/AddressInput";

import "../../../../../style/cart.css";
import { Address } from "../../../../model/Address";

interface DeliveryInfoProps {
    onStepComplete: () => void;
    onShippingInfoChange: (address: Address) => void;
    shippingAddress: Address;
}
export class DeliveryInfo extends React.Component <DeliveryInfoProps, {}> {

    handleNewAddress = (newAddress: Address) => {
        const address = newAddress;
        this.props.onShippingInfoChange(address);
    }

    handleNameInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
    }

    handleLastNameInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
    }

    render () {
        return (
            <Form size = "big" id="deliveryInfoForm">
                <Form.Field inline>
                    <label>Name: </label>
                    <input
                        type="text"
                        placeholder="Name"
                        onChange={this.handleNameInput.bind(this)} />
                </Form.Field>
                <Form.Field inline>
                    <label>Last name: </label>
                    <input
                        type="text"
                        placeholder="Last name"
                        onChange={this.handleLastNameInput.bind(this)} />
                </Form.Field>
                <AddressInput
                    formSize="big"
                    address={this.props.shippingAddress}
                    onAddressChange={ newAddress => this.handleNewAddress(newAddress)} />
                <Button
                    icon
                    type="submit"
                    positive
                    labelPosition="right"
                    floated="right"
                    onClick={this.props.onStepComplete}>
                    Next
                    <Icon name="chevron right" />
                </Button>
            </Form>
        );
    }
}
