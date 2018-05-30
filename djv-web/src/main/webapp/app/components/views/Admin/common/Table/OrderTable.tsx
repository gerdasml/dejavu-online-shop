// tslint:disable:max-classes-per-file
import * as React from "react";

import { Table } from "antd";
import { OrderItem } from "../../../../../model/Order";

import { addKey, WithKey } from "../../../../../utils/table";
import { formatPrice } from "../../../../../utils/common";
import { config } from "../../../../../config";

import "../../../../../../style/admin/orders.css";

type OrderItemRecord = OrderItem & WithKey;

interface OrderTableProps {
    items: OrderItem[];
}

class OrderRecordTable extends Table<OrderItemRecord> {}

class OrderRecordColumn extends Table.Column<OrderItemRecord> {}

export const OrderTable = (props: OrderTableProps) => (
    <OrderRecordTable
        className="ordersRecordTable"
        scroll={{x: config.adminTableScrollWidth.common}}
        bordered={true}
        dataSource={props.items.map(addKey)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}>
        <OrderRecordColumn
            className="ordersRecordColumn"
            key = "product"
            title = "Product"
            render={(_, record) => record.product.name} // TODO: link
        />
        <OrderRecordColumn
            className="ordersRecordColumn"
            key = "price"
            title = "Unit price"
            render={(_, record) => formatPrice(record.product.price)}
        />
        <OrderRecordColumn
            className="ordersRecordColumn"
            key = "amount"
            title = "Quantity"
            dataIndex="amount"
        />
        <OrderRecordColumn
            className="ordersRecordColumn"
            key = "total"
            title = "Total"
            render={(_, record) => formatPrice(record.total)}
        />
    </OrderRecordTable>
);
