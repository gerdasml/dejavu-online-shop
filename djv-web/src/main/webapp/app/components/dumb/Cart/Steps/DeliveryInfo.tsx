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

    render () {
        return (
            <Form size = "big" id="deliveryInfoForm">
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
