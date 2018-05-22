import * as React from "react";

import {Button, Table} from "semantic-ui-react";

import { Cart } from "../../../model/Cart";
import { Order, OrderItem } from "../../../model/Order";
import { Amount } from "../Cart/Amount";
import { formatPrice } from "../../../utils/common";
import "../../../../style/cart.css";

interface OrderTableProps {
    data: Order | Cart;
    onAmountChange?: (item: OrderItem, amount: number) => void;
    onItemRemove?: (item: OrderItem) => void;
}

export const OrderTable = (props: OrderTableProps) => (
    <Table celled striped id="order-table-father">
        <Table.Header>
            <Table.Row id="order-table">
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
                    <Table.Cell textAlign="center">{formatPrice(item.product.price)}</Table.Cell>
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
                        <Button negative icon="remove" className="remove-button"
                            onClick={() => props.onItemRemove(item)}
                        />
                    </Table.Cell>
                    : ""
                    }
                    <Table.Cell textAlign="center">{formatPrice(item.total)}</Table.Cell>
                </Table.Row>
            ))}
            <Table.Row>
                { props.onItemRemove !== undefined
                ? <Table.Cell colSpan={4} textAlign="right"><h4 className="cart-total">Total:</h4></Table.Cell>
                : <Table.Cell colSpan={3} textAlign="right"><h4 className="cart-total">Total:</h4></Table.Cell>
                }
                <Table.Cell textAlign="center">{formatPrice(props.data.total)}</Table.Cell>
            </Table.Row>
        </Table.Body>
        : ""
        }
    </Table>
);
