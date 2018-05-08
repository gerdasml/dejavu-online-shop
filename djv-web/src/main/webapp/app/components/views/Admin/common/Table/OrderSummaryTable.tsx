// tslint:disable:max-classes-per-file
import * as React from "react";
import { NavLink } from "react-router-dom";

import { Button, Table, Tag } from "antd";

import { OrderSummary } from "../../../../../model/Order";

import { addKey, WithKey } from "../../../../../utils/table";

type Summary = OrderSummary & WithKey;

interface OrderSummaryTableProps {
    summaries: OrderSummary[];
}

class SummaryTable extends Table<Summary> {}

class SummaryColumn extends Table.Column<Summary> {}

export const OrderSummaryTable = (props: OrderSummaryTableProps) => (
    <SummaryTable
        dataSource={props.summaries.map(addKey)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}>
        <SummaryColumn
                    key="email"
                    title="Email"
                    render={(_, record) => record.user.email} />
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
                    render={(_, record) =>
                        record.user.banned
                        ? <Tag color="red">Yes</Tag>
                        : <Tag color="green">No</Tag>} />
        <SummaryColumn
                    key="moreInfo"
                    render={(_, record) =>
                        <NavLink to={`/admin/user/${record.user.id}`}>
                            <Button shape="circle" icon="info"/>
                        </NavLink>
                    } />
    </SummaryTable>
);
