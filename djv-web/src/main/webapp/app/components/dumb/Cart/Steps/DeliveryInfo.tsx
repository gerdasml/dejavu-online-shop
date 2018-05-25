import * as React from "react";

import { Button, Form, Icon } from "semantic-ui-react";

import {AddressInput} from "../../Address/AddressInput";

import "../../../../../style/cart.css";
import { Address } from "../../../../model/Address";
import { ShippingInformation } from "../../../../model/ShippingInformation";

interface DeliveryInfoProps {
    onStepComplete: () => void;
    onShippingInfoChange: (info: ShippingInformation) => void;
    shippingInformation: ShippingInformation;
}
export class DeliveryInfo extends React.Component <DeliveryInfoProps, {}> {
    render () {
        return (
            <Form size = "big" id="deliveryInfoForm">
                <Form.Field inline>
                    <label>Name: </label>
                    <input
                        type="text"
                        placeholder="Name"
                        defaultValue={this.props.shippingInformation.recipientFirstName}
                        onChange={e => this.props.onShippingInfoChange({
                            ...this.props.shippingInformation,
                            recipientFirstName: e.target.value
                        })} />
                </Form.Field>
                <Form.Field inline>
                    <label>Last name: </label>
                    <input
                        type="text"
                        placeholder="Last name"
                        defaultValue={this.props.shippingInformation.recipientLastName}
                        onChange={e => this.props.onShippingInfoChange({
                            ...this.props.shippingInformation,
                            recipientLastName: e.target.value
                        })} />
                </Form.Field>
                <AddressInput
                    formSize="big"
                    address={this.props.shippingInformation.shippingAddress}
                    onAddressChange={ newAddress => this.props.onShippingInfoChange({
                        ...this.props.shippingInformation,
                        shippingAddress: newAddress
                    })} />
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
