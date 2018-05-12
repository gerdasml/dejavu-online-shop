import * as React from "react";

import {Table} from "semantic-ui-react";

import { Cart } from "../../../model/Cart";
import { Order } from "../../../model/Order";

interface OrderTableProps {
    data: Order | Cart;
}

export const OrderTable = (props: OrderTableProps) => (
    <Table celled>
        <Table.Header>
            <Table.Row>
                <Table.HeaderCell>Name</Table.HeaderCell>
                <Table.HeaderCell>Unit price</Table.HeaderCell>
                <Table.HeaderCell>Quantity</Table.HeaderCell>
                <Table.HeaderCell>Total price</Table.HeaderCell>
            </Table.Row>
        </Table.Header>
        { props.data
        ?
        <Table.Body>
            {props.data.items.map((item,i) => (
                <Table.Row key={i}>
                    <Table.Cell>{item.product.name}</Table.Cell>
                    <Table.Cell>{item.product.price}</Table.Cell>
                    {/* TODO: use price formatting utility*/}
                    <Table.Cell>{item.amount}</Table.Cell>
                    <Table.Cell>{item.total}</Table.Cell>
                </Table.Row>
            ))}
            <Table.Row>
                <Table.Cell colSpan={3}></Table.Cell>
                <Table.Cell>{props.data.total}</Table.Cell>
            </Table.Row>
        </Table.Body>
        : ""
        }
    </Table>
);
