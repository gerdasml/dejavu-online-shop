// tslint:disable:max-classes-per-file
import * as React from "react";

import { Table, Rate, Tag, Divider } from "antd";
import { Order, OrderStatus } from "../../../../../model/Order";
import { OrderStatusCell } from "./OrderStatusCell";
import { OrderTable } from "./OrderTable";

import { stringifyAddress } from "../../../../../utils/common";
import { addKey, WithKey } from "../../../../../utils/table";
import { config } from "../../../../../config";

type OrderRecord = Order & WithKey;

interface OrdersTableProps {
    orders: Order[];
}

class OrdersRecordTable extends Table<OrderRecord> {}
class OrdersRecordColumn extends Table.Column<OrderRecord> {}

const dateToString = (d: Date) => new Date(Date.parse(d.toString())).toLocaleDateString();

export const OrdersTable = (props: OrdersTableProps) => (
    <OrdersRecordTable
        scroll={{x: config.adminTableScrollWidth}}
        bordered={true}
        dataSource={props.orders.map(addKey)}
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
            key="date"
            title="Date"
            render={(_, record) => dateToString(record.createdDate)}
        />
        <OrdersRecordColumn
            key="user"
            title="User email"
            render={(_, record) => record.user.email}
        />
        <OrdersRecordColumn
            key="status"
            title="Status"
            dataIndex="status"
            render={(_, record) => <OrderStatusCell orderId={record.id} status={record.status} />}
        />
        <OrdersRecordColumn
            key="shipping"
            title="Shipping address"
            render={(_, record) => stringifyAddress(record.shippingAddress)}
        />
        <OrdersRecordColumn
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
            key="total"
            title="Total"
            dataIndex="total"
        />
    </OrdersRecordTable>
);
