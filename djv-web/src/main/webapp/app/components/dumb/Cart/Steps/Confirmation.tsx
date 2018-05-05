import * as React from "react";
import { Button, Icon, List, Table } from "semantic-ui-react";
import "../../../../../style/cart.css";
import { Purchase } from "../../../../model/Purchase";

interface ConfirmationProps {
    purchases: Purchase[];
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
        <Table striped celled attached="top">
            <Table.Header>
                <Table.Row>
                <Table.HeaderCell>Item</Table.HeaderCell>
                <Table.HeaderCell>Unit Price</Table.HeaderCell>
                <Table.HeaderCell>Amount</Table.HeaderCell>
                <Table.HeaderCell>Total</Table.HeaderCell>
                </Table.Row>
            </Table.Header>

            <Table.Body>
                {props.purchases.map(p =>
                    <Table.Row>
                        <Table.Cell>{p.item.name}</Table.Cell>
                        <Table.Cell>{p.item.price}€</Table.Cell>
                        <Table.Cell>{p.amount}</Table.Cell>
                        <Table.Cell>{p.total}€</Table.Cell>
                    </Table.Row>
                )}
            </Table.Body>
            <Table.Footer>
                <Table.Row>
                    <Table.HeaderCell colspan="3" textAlign="right"><h4>Overall:</h4></Table.HeaderCell>
                    <Table.HeaderCell ><h4>1000000€</h4></Table.HeaderCell>
                    </Table.Row>
            </Table.Footer>
        </Table>
        <Button id="confirmButton" floated="right" positive onClick={props.onStepComplete}>Confirm</Button>
    </div >
);
