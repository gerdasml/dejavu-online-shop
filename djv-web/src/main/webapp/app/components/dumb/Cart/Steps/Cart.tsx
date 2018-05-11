import * as React from "react";

import { Button, Dimmer, Icon, Loader, Table } from "semantic-ui-react";

import { Cart as CartModel } from "../../../../model/Cart";

import { Amount } from "../../../dumb/Cart/Amount";

import * as api from "../../../../api";

import { OrderItem } from "../../../../model/Order";
import { Product } from "../../../../model/Product";

interface CartProps {
    cart: CartModel;
    onStepComplete: () => void;
    onCartUpdate: (cart: CartModel) => void;
}

interface CartState {
    isLoading: boolean;
    error?: string;
}
export class Cart extends React.Component <CartProps, CartState> {
    state: CartState = {
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
            this.setState({
                ...this.state,
                error: amountChangeInfo.message,
            });
        } else {
            const newUnitTotal = newAmount*orderItem.product.price;
            const newTotal = this.props.cart.total - orderItem.total + newUnitTotal;
            const newItems = [...this.props.cart.items];
            const idx = newItems.findIndex(i => i.product.id === orderItem.product.id);
            newItems[idx] = {
                ...orderItem,
                amount: newAmount,
                total: newUnitTotal,
            };
            this.props.onCartUpdate({
                ...this.props.cart,
                items: newItems,
                total: newTotal,
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
                        { this.props.cart !== undefined
                        ?
                        this.props.cart.items.map( (p,i) =>
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
                            <Table.Cell>{this.props.cart.total}€</Table.Cell>
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
