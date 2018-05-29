// tslint:disable:max-classes-per-file
import * as React from "react";

import { Table, Rate, Tag, Divider, notification, Modal } from "antd";
import { Order, OrderStatus } from "../../../../../model/Order";
import { OrderStatusCell } from "./OrderStatusCell";
import { OrderTable } from "./OrderTable";

import { stringifyAddress } from "../../../../../utils/common";
import { addKey, WithKey } from "../../../../../utils/table";
import { config } from "../../../../../config";

import * as api from "../../../../../api";
import "../../../../../../style/admin/orders.css";

type OrderRecord = Order & WithKey;

interface OrdersTableProps {
    orders: Order[];
}

class OrdersRecordTable extends Table<OrderRecord> {}
class OrdersRecordColumn extends Table.Column<OrderRecord> {}

const dateToString = (d: Date) => new Date(Date.parse(d.toString())).toLocaleDateString();

export class OrdersTable extends React.Component<OrdersTableProps, never> {

    showLockModal = (localStatus: OrderStatus, remoteStatus: OrderStatus, onSelect: (s: OrderStatus) => void) => {
        Modal.confirm({
            title: "Status update error",
            content: (
                <span>
                    <p>The status of this order has already been changed by another admin.</p>
                    <p>You have chosen <b>{localStatus}</b>, they have chosen <b>{remoteStatus}</b></p>
                    <p>Which status would you like to use?</p>
                </span>
            ),
            okText: `${localStatus}`,
            cancelText: `${remoteStatus}`,
            onCancel: () => onSelect(remoteStatus),
            onOk: () => onSelect(localStatus),
            className: "lock-modal"
        });
    }

    async handleStatusUpdate (order: Order, newStatus: OrderStatus): Promise<OrderStatus> {
        const index = this.props.orders.findIndex(el => el.id === order.id);
        const response = await api.order.updateOrderStatus(order.id, newStatus, order.lastModified);
        if (!api.isError(response)) {
            this.props.orders[index] = response;
            this.forceUpdate();
            return response.status;
        }
        if (response.status !== 412) { // PRECONDITION_FAILED code
            notification.error({message: "Status update failed", description: response.message});
            return order.status;
        }
        const fetchResponse = await api.order.getOrder(order.id);
        if (api.isError(fetchResponse)) {
            notification.error({message: "Failed to fetch order information", description: fetchResponse.message});
            return order.status;
        }
        if (fetchResponse.status === newStatus) {
            // Don't show the modal if the statuses are equal
            this.props.orders[index] = fetchResponse;
            this.forceUpdate();
            return newStatus;
        }
        const modalPromise = new Promise<OrderStatus>(resolve => {
            this.showLockModal(newStatus, fetchResponse.status, resolve);
        });
        const status = await modalPromise;
        fetchResponse.status = status;
        order.lastModified = fetchResponse.lastModified;
        return await this.handleStatusUpdate(order, fetchResponse.status);
    }
    render () {
        return (
            <OrdersRecordTable
                className="ordersRecordTable"
                scroll={{x: config.adminTableScrollWidth.common}}
                bordered={true}
                dataSource={this.props.orders.map(addKey)}
                pagination={{pageSize: 25, hideOnSinglePage: true}}
                expandedRowRender={(record: OrderRecord) => (
                    <span>
                        { record.review !== undefined
                        ?
                        <span>
                            <b>Review comment: </b>
                            { record.review.comment === undefined || record.review.comment.length === 0
                            ? <Tag color="red">Not Given</Tag>
                            : record.review.comment
                            }
                            <Divider />
                        </span>
                        : ""
                        }
                        <OrderTable items={record.items} />
                    </span>
                )}>
                <OrdersRecordColumn
                    className="ordersRecordColumn"
                    key="date"
                    title="Date"
                    render={(_, record) => dateToString(record.createdDate)}
                />
                <OrdersRecordColumn
                    className="ordersRecordColumn"
                    key="user"
                    title="User email"
                    render={(_, record) => record.user.email}
                />
                <OrdersRecordColumn
                    className="ordersRecordColumn"
                    key="status"
                    title="Status"
                    dataIndex="status"
                    render={(_, record) =>
                        <OrderStatusCell
                            onStatusUpdate={newStatus => this.handleStatusUpdate(record, newStatus)}
                            status={record.status}
                        />
                    }
                />
                <OrdersRecordColumn
                    className="ordersRecordColumn"
                    key="recipient"
                    title="Recipient"
                    render={(_, record) =>
                        `${record.shippingInformation.recipientFirstName} ${record.shippingInformation.recipientLastName}`
                    }
                />
                <OrdersRecordColumn
                    className="ordersRecordColumn"
                    key="shipping"
                    title="Shipping address"
                    render={(_, record) => stringifyAddress(record.shippingInformation.shippingAddress)}
                />
                <OrdersRecordColumn
                    className="ordersRecordColumn"
                    key="review"
                    title="Review"
                    render={(_, record) =>
                        record.reviewShown
                            ? record.review === undefined
                                ? <Tag color="red">REJECTED</Tag>
                                : <Rate disabled defaultValue={record.review.rating} />
                            : record.status === OrderStatus.DELIVERED
                                ? <Tag color="orange">NOT SHOWN</Tag>
                                : <Tag color="orange">N/A</Tag>
                    }
                />
                <OrdersRecordColumn
                    className="ordersRecordColumn"
                    key="total"
                    title="Total"
                    dataIndex="total"
                />
            </OrdersRecordTable>
        );
    }
}
