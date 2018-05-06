import * as React from "react";

import { Table } from "antd";
import { Address } from "../../../../../model/Address";
import { Order, OrderItem, OrderStatus } from "../../../../../model/Order";
import { User } from "../../../../../model/User";
import { OrderStatusCell } from "./OrderStatusCell";
import { OrderTable } from "./OrderTable";

import { stringifyAddress } from "../../../../../utils/common";
import { addKey, WithKey } from "../../../../../utils/table";

type OrderRecord = Order & WithKey;

interface OrdersTableProps {
    orders: Order[];
}

class OrdersRecordTable extends Table<OrderRecord> {}
class OrdersRecordColumn extends Table.Column<OrderRecord> {}

const dateToString = (d: Date) => new Date(Date.parse(d.toString())).toLocaleDateString();

export const OrdersTable = (props: OrdersTableProps) => (
    <OrdersRecordTable
        bordered={true}
        dataSource={props.orders.map(addKey)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}
        expandedRowRender={(record: OrderRecord) => <OrderTable items={record.items} />}>
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
            key="total"
            title="Total"
            dataIndex="total"
        />
    </OrdersRecordTable>
);
