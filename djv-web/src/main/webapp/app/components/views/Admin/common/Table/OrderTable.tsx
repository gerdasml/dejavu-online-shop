import * as React from "react";

import { Table } from "antd";
import { OrderItem } from "../../../../../model/Order";
import { Product } from "../../../../../model/Product";

interface OrderItemRecord {
    key: number;
    amount: number;
    total: number;
    price: number;
    product: Product;
}

interface OrderTableProps {
    items: OrderItem[];
}

class OrderRecordTable extends Table<OrderItemRecord> {}

class OrderRecordColumn extends Table.Column<OrderItemRecord> {}

const convertToRecord = (item: OrderItem, i: number): OrderItemRecord => ({
    amount: item.amount,
    key: i,
    price: item.product.price,
    product: item.product,
    total: item.total,
});

export const OrderTable = (props: OrderTableProps) => (
    <OrderRecordTable
        bordered={true}
        dataSource={props.items.map(convertToRecord)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}>
        <OrderRecordColumn
            key = "product"
            title = "Product"
            render={(_, record) => record.product.name} // TODO: link
        />
        <OrderRecordColumn
            key = "price"
            title = "Unit price"
            dataIndex="price"
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
