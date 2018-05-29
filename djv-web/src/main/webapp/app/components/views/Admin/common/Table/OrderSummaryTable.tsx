// tslint:disable:max-classes-per-file
import * as React from "react";
import { NavLink } from "react-router-dom";

import { Button, Table, Tag } from "antd";

import { OrderSummary } from "../../../../../model/Order";

import { addKey, WithKey } from "../../../../../utils/table";
import { config } from "../../../../../config";

import "../../../../../../style/admin/users.css";

type Summary = OrderSummary & WithKey;

interface OrderSummaryTableProps {
    summaries: OrderSummary[];
}

class SummaryTable extends Table<Summary> {}

class SummaryColumn extends Table.Column<Summary> {}

export const OrderSummaryTable = (props: OrderSummaryTableProps) => (
    <SummaryTable
        className="summaryTable"
        scroll={{x: config.adminTableScrollWidth.common}}
        dataSource={props.summaries.map(addKey)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}
        bordered={true}>
        <SummaryColumn
                    className="summaryColumn"
                    key="email"
                    title="Email"
                    render={(_, record) => record.user.email} />
        <SummaryColumn
                    className="summaryColumn"
                    key="orderCount"
                    title="# of orders"
                    dataIndex="orderCount" />
        <SummaryColumn
                    className="summaryColumn"
                    key="totalSpending"
                    title="Total money spent"
                    dataIndex="totalSpending" />
        <SummaryColumn
                    className="summaryColumn"
                    key="averageSpending"
                    title="Average money per order"
                    dataIndex="averageSpending"
                    render={(_, record) => record.averageSpending === undefined ? "-" : record.averageSpending} />
        <SummaryColumn
                    className="summaryColumn"
                    key="isBanned"
                    title="Is banned?"
                    render={(_, record) =>
                        record.user.banned
                        ? <Tag color="red">Yes</Tag>
                        : <Tag color="green">No</Tag>} />
        <SummaryColumn
                    className="summaryColumn"
                    key="moreInfo"
                    render={(_, record) =>
                        <NavLink to={`/admin/user/${record.user.id}`}>
                            <Button shape="circle" icon="info" id="userInfoButton"/>
                        </NavLink>
                    } />
    </SummaryTable>
);
