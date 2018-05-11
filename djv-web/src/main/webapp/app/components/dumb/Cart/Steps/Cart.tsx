import * as React from "react";

import { Button, Icon, Loader, Table, Dimmer } from "semantic-ui-react";

import { Cart as CartModel } from "../../../../model/Cart";

import { Amount } from "../../../dumb/Cart/Amount";

import * as api from "../../../../api";

import { OrderItem } from "../../../../model/Order";
import { Product } from "../../../../model/Product";

interface CartProps {
    cart: CartModel;
    onStepComplete: () => void;
}

interface CartState {
    cart: CartModel;
    isLoading: boolean;
    error?: string;
}
export class Cart extends React.Component <CartProps, CartState> {
    state = {
        cart: this.props.cart,
        isLoading: false,
    };

    async changeAmount (orderItem: OrderItem, newAmount: number) {
        this.setState({
            ...this.state,
            isLoading: true,
        });
        const amountChangeInfo = await api.cart.updateAmount({
            amount: newAmount,
            productId: orderItem.product.id,
        });
        if(api.isError(amountChangeInfo)) {
            console.log(amountChangeInfo.message);
            this.setState({
                ...this.state,
                error: amountChangeInfo.message,
                // isLoading: false,
            });
        } else {
            const newUnitTotal = newAmount*orderItem.product.price;
            const newTotal = this.state.cart.total - orderItem.total + newUnitTotal;
            console.log(newUnitTotal, newTotal);
            const newItems = [...this.state.cart.items];
            const idx = newItems.findIndex(i => i.product.id === orderItem.product.id);
            newItems[idx] = {
                ...orderItem,
                amount: newAmount,
                total: newUnitTotal,
            };
            this.setState({
                ...this.state,
                cart: {
                    ...this.state.cart,
                    items: newItems,
                    total: newTotal,
                }
            });
        }
        this.setState({
            ...this.state,
            isLoading: false,
        });
    }

    render () {
        return(
            <Dimmer.Dimmable blurring dimmed={this.state.isLoading}>
                <Dimmer inverted active={this.state.isLoading}>
                    <Loader size="large">Loading</Loader>
                </Dimmer>
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
                        { this.state.cart !== undefined
                        ?
                        this.state.cart.items.map( (p,i) =>
                            <Table.Row key={i}>
                                <Table.Cell>{p.product.name}</Table.Cell>
                                <Table.Cell>{p.product.price}€</Table.Cell>
                                <Table.Cell>
                                    <Amount amount={p.amount}
                                        onAmountChange={amount => this.changeAmount(p, amount)}
                                    />
                                </Table.Cell>
                                <Table.Cell>{p.total}€</Table.Cell>
                            </Table.Row>
                        )
                        : ""
                        }
                        <Table.Row>
                            <Table.Cell colSpan={3} textAlign="right"><h4>Overall:</h4></Table.Cell>
                            <Table.Cell>{this.state.cart.total}€</Table.Cell>
                        </Table.Row>
                    </Table.Body>
                </Table>
                <Button icon labelPosition="right" floated="right" onClick={this.props.onStepComplete}>
                        Buy
                        <Icon name="chevron right" />
                </Button>
            </Dimmer.Dimmable>
        );
    }
}
