import * as React from "react";

import { Icon, notification, Select, Spin } from "antd";
import { OrderStatus } from "../../../../../model/Order";

import * as api from "../../../../../api";

interface OrderStatusCellProps {
    orderId: number;
    status: OrderStatus;
}

interface OrderStatusCellState {
    isLoading: boolean;
    status: OrderStatus;
    isBeingEdited: boolean;
}

export class OrderStatusCell extends React.Component<OrderStatusCellProps, OrderStatusCellState> {
    state: OrderStatusCellState = {
        isBeingEdited: false,
        isLoading: false,
        status: OrderStatus.CREATED,
    };

    componentDidMount () {
        this.setState({...this.state, status: this.props.status});
    }

    startEditing () {
        this.setState({...this.state, isBeingEdited: true});
    }

    async applyChange (status: OrderStatus) {
        const response = await api.order.updateOrderStatus(this.props.orderId, status);
        if (api.isError(response)) {
            notification.error({message: "Failed to update status", description: response.message});
            return;
        }
    }

    async stopEditing () {
        this.setState({...this.state, isLoading: true});
        await this.applyChange(this.state.status);
        this.setState({...this.state, isBeingEdited: false, isLoading: false});
    }

    handleChange (val: string) {
        const newStatus = OrderStatus[val.toUpperCase() as keyof typeof OrderStatus];
        this.setState({...this.state, status: newStatus});
    }

    renderEditable () {
        const { status, isLoading } = this.state;
        return (
            <div className="editable-cell-input-wrapper">
                <Spin spinning={this.state.isLoading}>
                    <Select onChange={this.handleChange.bind(this)} defaultValue={status}>
                        {
                            Object.keys(OrderStatus)
                                .filter(key => !Number(key))
                                .map((x, i) =>
                                    <Select.Option value={x} key={i}>{x}</Select.Option>
                                )
                        }
                    </Select>
                    <Icon
                        type="check"
                        className="editable-cell-icon-check"
                        onClick={this.stopEditing.bind(this)}
                    />
                </Spin>
            </div>
        );
    }

    renderText () {
        const { status } = this.state;
        return (
            <div className="editable-cell-text-wrapper">
                {status.toString() || ""}
                <Icon
                    type="edit"
                    className="editable-cell-icon"
                    onClick={this.startEditing.bind(this)}
                />
            </div>
        );
    }

    render () {
        const { isBeingEdited } = this.state;
        if (isBeingEdited) {
            return this.renderEditable();
        } else {
            return this.renderText();
        }
    }
}
