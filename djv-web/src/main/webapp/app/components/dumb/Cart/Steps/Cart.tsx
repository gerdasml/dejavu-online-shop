import * as React from "react";

import { Button, Dimmer, Icon, Loader, Table } from "semantic-ui-react";

import { Cart as CartModel } from "../../../../model/Cart";

import { Amount } from "../../../dumb/Cart/Amount";

import * as api from "../../../../api";

import { OrderItem } from "../../../../model/Order";
import { Product } from "../../../../model/Product";
import { OrderTable } from "../../Order/OrderTable";

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

    async removeItem (item: OrderItem) {
        this.setState({
            ...this.state,
            isLoading: true,
        });
        const removeItemInfo = await api.cart.removeFromCart(item.product.id);
        if(api.isError(removeItemInfo)) {
            this.setState({
                ...this.state,
                error: removeItemInfo.message,
            });
        } else {
            const newTotal = this.props.cart.total - item.total;
            const prevItems = [...this.props.cart.items];
            const newItems = prevItems.filter(i => i.product.id !== item.product.id);
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
                <OrderTable
                    data={this.props.cart}
                    onAmountChange={this.changeAmount.bind(this)}
                    onItemRemove={this.removeItem.bind(this)}
                />
                <Button
                    icon
                    labelPosition="right"
                    floated="right"
                    onClick={this.props.onStepComplete}
                    disabled={this.props.cart.total === 0}
                >
                    Buy
                    <Icon name="chevron right" />
                </Button>
            </Dimmer.Dimmable>
        );
    }
}
