import * as React from "react";

import { Pagination, Table, Button } from "antd";

import { OrderSummary } from "../../../../../model/Order";

interface Summary {
    key: number;
    email: string;
    orderCount: number;
    totalSpending: number;
    averageSpending: number;
    isBanned: boolean;
}

interface OrderSummaryTableProps {
    summaries: OrderSummary[];
}

class SummaryTable extends Table<Summary> {}

class SummaryColumn extends Table.Column<Summary> {}

const convertToDataSource = (summaries: OrderSummary, i: number): Summary => ({
    averageSpending: summaries.averageSpending,
    email: summaries.user.email,
    isBanned: summaries.user.banned,
    key: i,
    orderCount: summaries.orderCount,
    totalSpending: summaries.totalSpending,
});

export const OrderSummaryTable = (props: OrderSummaryTableProps) => (
    <SummaryTable
        dataSource={props.summaries.map(convertToDataSource)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}>
        <SummaryColumn
                    key="email"
                    title="Email"
                    dataIndex="email" />
        <SummaryColumn
                    key="orderCount"
                    title="# of orders"
                    dataIndex="orderCount" />
        <SummaryColumn
                    key="totalSpending"
                    title="Total money spent"
                    dataIndex="totalSpending" />
        <SummaryColumn
                    key="averageSpending"
                    title="Average money per order"
                    dataIndex="averageSpending"
                    render={(_, record) => record.averageSpending === undefined ? "-" : record.averageSpending} />
        <SummaryColumn
                    key="isBanned"
                    title="Is banned?"
                    render={(_, record) => record.isBanned ? "Yes" : "No"} />
        <SummaryColumn
                    key="moreInfo"
                    render={(_, record) => <Button shape="circle" icon="info" />} />
    </SummaryTable>
);
