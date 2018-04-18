import * as React from "react";

import { Button, Icon, Table } from "semantic-ui-react";

import { IPurchase } from "../../../../model/Purchase";

import { Amount } from "../../../dumb/Cart/Amount";

interface ICartProps { purchases: IPurchase[]; }

export const Cart = (props: ICartProps) => (
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
                {props.purchases.map(p =>
                    <Table.Row>
                        <Table.Cell>{p.item.name}</Table.Cell>
                        <Table.Cell>{p.item.price}€</Table.Cell>
                        <Table.Cell><Amount amount={p.amount}/></Table.Cell>
                        <Table.Cell>{p.total}€</Table.Cell>
                    </Table.Row>
                )}
            </Table.Body>
        </Table>
        <Button icon labelPosition="right" floated="right">
                Buy
                <Icon name="chevron right" />
        </Button>
    </div >
);