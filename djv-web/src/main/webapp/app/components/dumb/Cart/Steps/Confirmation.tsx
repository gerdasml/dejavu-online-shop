import * as React from "react";
import { Button, Icon, List, Table } from "semantic-ui-react";
import "../../../../../style/cart.css";
import { OrderItem } from "../../../../model/Order";

import { Cart as CartModel } from "../../../../model/Cart";
import { OrderTable } from "../../Order/OrderTable";

interface ConfirmationProps {
    cart: CartModel;
    onStepComplete: () => void;
}

export const Confirmation = (props: ConfirmationProps) => (
    <div>
        <List horizontal size="small" relaxed>
            <List.Item>
                <Icon name="cart" size="big"/>
                <List.Content>
                    <List.Header>Order number</List.Header>
                    03000152
                </List.Content>
            </List.Item>
            <List.Item>
                <Icon name="credit card alternative" size="big"/>
                <List.Content>
                    <List.Header>Card number</List.Header>
                    LT00000000000
                </List.Content>
            </List.Item>
            <List.Item>
                <Icon name="shipping" size="big"/>
                <List.Content>
                    <List.Header>Delivery Address</List.Header>
                    Naugarduko g. 24, Vilnius
                </List.Content>
            </List.Item>
        </List>
        <OrderTable data={props.cart}/>
        <Button id="confirmButton" floated="right" positive onClick={props.onStepComplete}>Confirm</Button>
    </div >
);
