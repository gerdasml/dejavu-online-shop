import * as React from "react";

import { Button, Form } from "semantic-ui-react";

import {AddressInput} from "../../Address/AddressInput";

import "../../../../../style/cart.css";

interface DeliveryInfoProps {
    onStepComplete: () => void;
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
            <AddressInput formSize="big" address={{}} onAddressChange={()=>{ }} />
            <Button type="submit" positive onClick={props.onStepComplete}>Next</Button>
        </Form>
    </div >
);
