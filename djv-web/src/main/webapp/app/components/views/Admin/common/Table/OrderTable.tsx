// tslint:disable:max-classes-per-file
import * as React from "react";

import { Table } from "antd";
import { OrderItem } from "../../../../../model/Order";

import { addKey, WithKey } from "../../../../../utils/table";
import { formatPrice } from "../../../../../utils/common";
import { config } from "../../../../../config";

type OrderItemRecord = OrderItem & WithKey;

interface OrderTableProps {
    items: OrderItem[];
}

class OrderRecordTable extends Table<OrderItemRecord> {}

class OrderRecordColumn extends Table.Column<OrderItemRecord> {}

export const OrderTable = (props: OrderTableProps) => (
    <OrderRecordTable
        scroll={{x: config.adminTableScrollWidth.common}}
        bordered={true}
        dataSource={props.items.map(addKey)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}>
        <OrderRecordColumn
            key = "product"
            title = "Product"
            render={(_, record) => record.product.name} // TODO: link
        />
        <OrderRecordColumn
            key = "price"
            title = "Unit price"
            render={(_, record) => formatPrice(record.product.price)}
        />
        <OrderRecordColumn
            key = "amount"
            title = "Quantity"
            dataIndex="amount"
        />
        <OrderRecordColumn
            key = "total"
            title = "Total"
            dataIndex="total"
        />
    </OrderRecordTable>
);
