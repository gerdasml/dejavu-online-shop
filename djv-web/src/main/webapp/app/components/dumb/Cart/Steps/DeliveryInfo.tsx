import * as React from "react";

import { Button, Form } from "semantic-ui-react";

import {AddressInput} from "../../Address/AddressInput";

import "../../../../../style/cart.css";
import { Address } from "../../../../model/Address";
import { User } from "../../../../model/User";

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

    render () {
        return (
            <Form size = "big" id="deliveryInfoForm">
                <AddressInput
                    formSize="big"
                    address={this.props.shippingAddress}
                    onAddressChange={ newAddress => this.handleNewAddress(newAddress)} />
                <Button type="submit" positive onClick={this.props.onStepComplete}>Next</Button>
            </Form>
        );
    }
}

export const DeliveryInfo = (props: DeliveryInfoProps) => (
    <div>
        <Form size = "big" id="deliveryInfoForm">
            <Form.Field inline>
                <label>First name</label>
                <input type="text" value="First Name"/>
            </Form.Field>
            <Form.Field inline>
                <label>Last name</label>
                <input type="text" value="Last Name" />
            </Form.Field>
            <Form.Field inline>
                <label>Email</label>
                <input type="email" value="email@email.com" />
            </Form.Field>
            <Form.Field inline>
                <label>Phone number</label>
                <input type="text" value="+370********" />
            </Form.Field>
            <AddressInput formSize="big" address={{}} onAddressChange={()=> { }} />
            <Button type="submit" positive onClick={props.onStepComplete}>Next</Button>
        </Form>
    </div >
);
