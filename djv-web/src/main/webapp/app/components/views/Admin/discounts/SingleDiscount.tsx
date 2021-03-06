import * as React from "react";
import { RouteComponentProps } from "react-router-dom";

import { notification, Spin } from "antd";

import * as api from "../../../../api";
import { DiscountEditor } from "./DiscountEditor";
import { Discount } from "../../../../model/Discount";

interface SingleDiscountState {
    discount?: Discount;
}

interface SingleDiscountProps {
    id: number;
}

export class SingleDiscount extends React.Component<RouteComponentProps<SingleDiscountProps>,SingleDiscountState> {
    state: SingleDiscountState = {};

    async componentDidMount () {
        await this.loadData(this.props);
    }

    async componentWillReceiveProps (nextProps: RouteComponentProps<SingleDiscountProps>) {
        await this.loadData(nextProps);
    }

    async loadData (props: RouteComponentProps<SingleDiscountProps>) {
        const response = await api.discount.getDiscount(props.match.params.id);
        if (api.isError(response)) {
            notification.error({ message: "Failed to fetch discount data", description: response.message });
            return;
        }
        this.setState({discount: response});
    }
    render () {
        return (
            <Spin spinning={this.state.discount === undefined}>
                <DiscountEditor discount={this.state.discount} />
            </Spin>
        );
    }
}
