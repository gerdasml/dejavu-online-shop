import * as React from "react";

import { notification, Spin } from "antd";

import { Order } from "../../../../model/Order";
import { OrdersTable } from "../common/Table/OrdersTable";

import * as api from "../../../../api";

interface OrdersState {
    isLoading: boolean;
    orders: Order[];
}

export class Orders extends React.Component<never, OrdersState> {
    state: OrdersState = {
        isLoading: true,
        orders: [],
    };

    async componentWillMount () {
        const response = await api.order.getAllOrders();
        if (api.isError(response)) {
            notification.error({message: "Failed to fetch data", description: response.message});
            return;
        }
        this.setState({orders: response, isLoading: false});
    }

    render () {
        const { isLoading } = this.state;
        return (
            <Spin spinning={isLoading} size="large">
                <OrdersTable orders={this.state.orders} />
            </Spin>
        );
    }
}
