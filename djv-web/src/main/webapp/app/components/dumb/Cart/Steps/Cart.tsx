import * as React from "react";

import { Button, Icon, Table } from "semantic-ui-react";

import { Cart as CartModel } from "../../../../model/Cart";

import { Amount } from "../../../dumb/Cart/Amount";

interface CartProps {
    cart: CartModel;
    onStepComplete: () => void;
}

export const Cart = (props: CartProps) => (
    <div>
        <Table striped celled>
            <Table.Header>
                <Table.Row>
                <Table.HeaderCell>Item</Table.HeaderCell>
                <Table.HeaderCell>Unit Price</Table.HeaderCell>
                <Table.HeaderCell>Amount</Table.HeaderCell>
                <Table.HeaderCell>Total</Table.HeaderCell>
                </Table.Row>
            </Table.Header>

            <Table.Body>
                { props.cart !== undefined
                ?
                props.cart.items.map(p =>
                        <Table.Row>
                            <Table.Cell>{p.product.name}</Table.Cell>
                            <Table.Cell>{p.product.price}€</Table.Cell>
                            <Table.Cell><Amount amount={p.amount}/></Table.Cell>
                            <Table.Cell>{p.total}€</Table.Cell>
                        </Table.Row>
                    )
                : ""
                }
                <Table.Row>
                    <Table.Cell colSpan={3} textAlign="right"><h4>Overall:</h4></Table.Cell>
                    <Table.Cell>{props.cart.total}€</Table.Cell>
                </Table.Row>
            </Table.Body>
        </Table>
        <Button icon labelPosition="right" floated="right" onClick={props.onStepComplete}>
                Buy
                <Icon name="chevron right" />
        </Button>
    </div >
);
