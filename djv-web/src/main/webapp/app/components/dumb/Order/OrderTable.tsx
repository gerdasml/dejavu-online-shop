import * as React from "react";

import {Button, Table} from "semantic-ui-react";

import { Cart } from "../../../model/Cart";
import { Order, OrderItem } from "../../../model/Order";
import { Amount } from "../Cart/Amount";

interface OrderTableProps {
    data: Order | Cart;
    onAmountChange?: (item: OrderItem, amount: number) => void;
    onItemRemove?: (item: OrderItem) => void;
}

export const OrderTable = (props: OrderTableProps) => (
    <Table celled striped>
        <Table.Header>
            <Table.Row>
                <Table.HeaderCell textAlign="center">Product name</Table.HeaderCell>
                <Table.HeaderCell textAlign="center">Unit price</Table.HeaderCell>
                <Table.HeaderCell textAlign="center">Quantity</Table.HeaderCell>
                { props.onItemRemove !== undefined
                ?
                <Table.HeaderCell textAlign="center">Remove</Table.HeaderCell>
                : ""
                }
                <Table.HeaderCell textAlign="center">Total price</Table.HeaderCell>
            </Table.Row>
        </Table.Header>
        { props.data
        ?
        <Table.Body>
            {props.data.items.map((item,i) => (
                <Table.Row key={i}>
                    <Table.Cell textAlign="center">{item.product.name}</Table.Cell>
                    <Table.Cell textAlign="center">{item.product.price}</Table.Cell>
                    {/* TODO: use price formatting utility*/}
                    <Table.Cell textAlign="center">
                        { props.onAmountChange === undefined
                        ?
                        item.amount
                        :
                        <Amount amount={item.amount}
                            onAmountChange={amount => props.onAmountChange(item, amount)}
                        />
                        }
                    </Table.Cell>
                    { props.onItemRemove !== undefined
                    ?
                    <Table.Cell textAlign="center">
                        <Button negative icon="remove"
                            onClick={() => props.onItemRemove(item)}
                        />
                    </Table.Cell>
                    : ""
                    }
                    <Table.Cell textAlign="center">{item.total}</Table.Cell>
                </Table.Row>
            ))}
            <Table.Row>
                { props.onItemRemove !== undefined
                ? <Table.Cell colSpan={4} textAlign="right"><h4>Total:</h4></Table.Cell>
                : <Table.Cell colSpan={3} textAlign="right"><h4>Total:</h4></Table.Cell>
                }
                <Table.Cell textAlign="center">{props.data.total}</Table.Cell>
            </Table.Row>
        </Table.Body>
        : ""
        }
    </Table>
);
