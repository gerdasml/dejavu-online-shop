// tslint:disable:max-classes-per-file
import * as React from "react";
import { NavLink } from "react-router-dom";

import { Table, Tag } from "antd";

import { ImportStatus } from "../../../../../model/Product";
import { addKey, WithKey } from "../../../../../utils/table";
import { config } from "../../../../../config";

type ImportJobRecord = ImportStatus & WithKey;

interface ImportJobStatusTableProps {
    jobs: ImportStatus[];
}

class ImportJobStatusTable extends Table<ImportJobRecord> {}

class ImportJobStatusColumn extends Table.Column<ImportJobRecord> {}

export const ImportJobsTable = (props: ImportJobStatusTableProps) => (
    <ImportJobStatusTable
        scroll={{x: config.adminTableScrollWidth}}
        bordered={true}
        dataSource={props.jobs.map(addKey)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}>
        <ImportJobStatusColumn
            key = "jobId"
            title = "Job ID"
            render={(_, record) => <NavLink to={`/admin/imports/${record.id}`}>{record.id}</NavLink>} // TODO: link
        />
        <ImportJobStatusColumn
            key = "startedAt"
            title = "Started At"
            render={(_, record) => new Date(Date.parse(record.startTime.toString())).toLocaleDateString()}
        />
        <Table.ColumnGroup title="Statistics">
            <ImportJobStatusColumn
                key = "totalCount"
                title = "Total items"
                render={(_, record) => <Tag color="geekblue">{record.total}</Tag>}
            />
            <ImportJobStatusColumn
                key = "successCount"
                title = "# of successes"
                render={(_, record) => <Tag color="green">{record.successCount}</Tag>}
            />
            <ImportJobStatusColumn
                key = "failureCount"
                title = "# of errors"
                render={(_, record) => <Tag color="red">{record.failureCount}</Tag>}
            />
            <ImportJobStatusColumn
                key = "resolvedCount"
                title = "# of resolved errors"
                render={(_, record) => <Tag color="orange">{record.failureCount - record.failedProducts.length}</Tag>}
            />
        </Table.ColumnGroup>
        <ImportJobStatusColumn
            key = "status"
            title = "Status"
            render={(_, record) => <Tag color="geekblue">{record.status}</Tag>}
        />
    </ImportJobStatusTable>
);
