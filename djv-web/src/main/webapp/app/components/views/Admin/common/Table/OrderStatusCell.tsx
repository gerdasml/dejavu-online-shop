import * as React from "react";

import { Icon, notification, Select, Spin } from "antd";
import { OrderStatus } from "../../../../../model/Order";

import * as api from "../../../../../api";

import { fromString } from "../../../../../utils/enum";

import "../../../../../../style/admin/orders.css";

interface OrderStatusCellProps {
    status: OrderStatus;
    onStatusUpdate: (newStatus: OrderStatus) => Promise<OrderStatus>;
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

    componentWillReceiveProps (props: OrderStatusCellProps) {
        this.setState({...this.state, status: props.status});
    }

    startEditing () {
        this.setState({...this.state, isBeingEdited: true});
    }

    async stopEditing () {
        this.setState({...this.state, isLoading: true});
        const status = await this.props.onStatusUpdate(this.state.status);
        this.setState({...this.state, isBeingEdited: false, isLoading: false, status});
    }

    handleChange (val: string) {
        const newStatus = fromString(OrderStatus, val);
        this.setState({...this.state, status: newStatus});
    }

    renderEditable () {
        const { status, isLoading } = this.state;
        return (
            <div className="editable-cell-input-wrapper">
                <Spin spinning={this.state.isLoading}>
                    <Select className="statusSelect" onChange={this.handleChange.bind(this)} defaultValue={status}>
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
