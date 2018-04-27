import * as React from "react";

import { Button, Form } from "semantic-ui-react";


import "../../../../../style/cart.css";

interface DeliveryInfoProps {
    onStepComplete: () => void;
}
export const DeliveryInfo = (props: DeliveryInfoProps) => (
    <div>
        <Form id="deliveryInfoForm">
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
            <Form.Field inline>
                <label>Street Address</label>
                <input type="text" value="address" />
            </Form.Field>
            <Form.Field inline>
                <label>Zip Code</label>
                <input type="text" value="zip" />
            </Form.Field>
            <Form.Field inline>
                <label>Country</label>
                <select>
                    <option value="Lithuania">Lithuania</option>
                    <option value="United Kingdom">United Kingdom</option>
                </select>
                <label id="cityLabel">City</label>
                <select>
                    <option value="Vilnius">Vilnius</option>
                    <option value="London">London</option>
                </select>
            </Form.Field>
            <Button type="submit" positive onClick={props.onStepComplete}>Next</Button>
        </Form>
    </div >
);
