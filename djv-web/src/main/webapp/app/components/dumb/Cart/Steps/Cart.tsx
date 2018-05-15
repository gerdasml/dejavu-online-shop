import * as React from "react";

import { notification } from "antd";
import { Button, Dimmer, Icon, Loader } from "semantic-ui-react";

import { Cart as CartModel } from "../../../../model/Cart";

import * as api from "../../../../api";

import { OrderItem } from "../../../../model/Order";
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
            notification.error({message: "Failed to change amount", description: amountChangeInfo.message});
        } else {
            this.props.onCartUpdate(amountChangeInfo);
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
            notification.error({message: "Failed to remove from cart", description: removeItemInfo.message});
        } else {
            this.props.onCartUpdate(removeItemInfo);
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
