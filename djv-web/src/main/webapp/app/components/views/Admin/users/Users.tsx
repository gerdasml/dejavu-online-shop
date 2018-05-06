import * as React from "react";

import {Header} from "semantic-ui-react";

import { notification, Spin } from "antd";

import { OrderSummary } from "../../../../model/Order";
import { OrderSummaryTable } from "../common/Table/OrderSummaryTable";

import * as api from "../../../../api";

interface UsersState {
    isLoading: boolean;
    summaries: OrderSummary[];
}

export class Users extends React.Component<never, UsersState> {
    state: UsersState = {
        isLoading: true,
        summaries: [],
    };

    async componentWillMount () {
        const response = await api.order.getOrderSummary();
        if (api.isError(response)) {
            notification.error({message: "Failed to fetch data", description: response.message});
            return;
        }
        this.setState({summaries: response, isLoading: false});
    }

    render () {
        const { isLoading } = this.state;
        return (
            <Spin spinning={isLoading} size="large">
                <OrderSummaryTable summaries={this.state.summaries} />
            </Spin>
        );
    }
}
