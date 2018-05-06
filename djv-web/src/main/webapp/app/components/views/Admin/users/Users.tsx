import * as React from "react";

import {Header} from "semantic-ui-react";

import { Spin } from "antd";

import { OrderSummary } from "../../../../model/Order";

import * as api from "../../../../api";
import { OrderSummaryTable } from "./Table/OrderSummaryTable";

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
            return;
        }
        this.setState({summaries: response, isLoading: false});
    }

    renderLoading = () => <Spin size="large" />;

    renderLoaded = () => <OrderSummaryTable summaries={this.state.summaries} />;

    render () {
        const { isLoading } = this.state;
        return (
            <div>
                { isLoading ? this.renderLoading() : this.renderLoaded() }
            </div>
        );
    }
}
