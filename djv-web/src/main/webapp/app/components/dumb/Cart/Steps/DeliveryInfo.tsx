import * as React from "react";

import { Button, Form } from "semantic-ui-react";

import {AddressInput} from "../../Address/AddressInput";

import "../../../../../style/cart.css";
import { Address } from "../../../../model/Address";
import { User } from "../../../../model/User";

interface DeliveryInfoProps {
    onStepComplete: () => void;
    user: User;
}
export class DeliveryInfo extends React.Component <DeliveryInfoProps, {}> {
    handleNewAddress = (newAddress: Address) => {
        // TODO
    }

    render () {
        return (
            <Form size = "big" id="deliveryInfoForm">
                <Form.Field inline>
                    <label>First name</label>
                    <input type="text" placeholder="John" defaultValue={this.props.user.firstName}/>
                </Form.Field>
                <Form.Field inline>
                    <label>Last name</label>
                    <input type="text" placeholder="Doe" defaultValue={this.props.user.lastName}/>
                </Form.Field>
                <Form.Field inline>
                    <label>Email</label>
                    <input type="email" placeholder="email@email.com" defaultValue={this.props.user.email}/>
                </Form.Field>
                <Form.Field inline>
                    <label>Phone number</label>
                    <input type="text" placeholder="+37061234567" defaultValue={this.props.user.phone}/>
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
