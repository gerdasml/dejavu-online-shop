import * as React from "react";

import {Table} from "semantic-ui-react";

import { Cart } from "../../../model/Cart";
import { Order, OrderItem } from "../../../model/Order";
import { Amount } from "../Cart/Amount";

interface OrderTableProps {
    data: Order | Cart;
    onAmountChange?: (item: OrderItem, amount: number) => void;
}

export const OrderTable = (props: OrderTableProps) => (
    <Table celled striped>
        <Table.Header>
            <Table.Row>
                <Table.HeaderCell>Product name</Table.HeaderCell>
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
                    <Table.Cell>
                        { props.onAmountChange === undefined
                        ?
                        item.amount
                        :
                        <Amount amount={item.amount}
                            onAmountChange={amount => props.onAmountChange(item, amount)}
                        />
                        }
                    </Table.Cell>
                    <Table.Cell>{item.total}</Table.Cell>
                </Table.Row>
            ))}
            <Table.Row>
                <Table.Cell colSpan={3} textAlign="right"><h4>Total:</h4></Table.Cell>
                <Table.Cell>{props.data.total}</Table.Cell>
            </Table.Row>
        </Table.Body>
        : ""
        }
    </Table>
);
