// tslint:disable:max-classes-per-file
import * as React from "react";
import { Discount, DiscountTarget, DiscountType } from "../../../../model/Discount";
import { Table, Button, Popconfirm } from "antd";
import { WithKey } from "../../../../utils/table";
import { NavLink } from "react-router-dom";

type DiscountRecord = Discount & WithKey;

interface DiscountsTableProps {
    discounts: Discount[];
    onDelete: (id: number) => void;
}

class DiscountRecordTable extends Table<DiscountRecord> {}

class DiscountRecordColumn extends Table.Column<DiscountRecord> {}

const getTargetName = (record: DiscountRecord) => {
    console.log("existing discount: " + record.id);
    if(record.target === undefined) {
        return "-";
    }
    return record.target.name;
};

const showAmount = (record: DiscountRecord) => {
    if(record.type === DiscountType.ABSOLUTE) {
        const absoluteAmount = record.value + "€";
        return absoluteAmount;
    }
    const percentageAmount = record.value + "%";
    return percentageAmount;
};

export const DiscountsTable = (props: DiscountsTableProps) => (
    <DiscountRecordTable
        bordered={true}
        dataSource={props.discounts.map(d => ({...d, key: d.id}))}
        pagination={{pageSize: 25, hideOnSinglePage: true}}
    >
        <DiscountRecordColumn
            key = "targetType"
            title = "Target type"
            render={(_, record) => record.targetType}
        />
        <DiscountRecordColumn
            key = "name"
            title = "Name"
            render={(_, record) => getTargetName(record)}
        />
        <DiscountRecordColumn
            key = "discountType"
            title = "Discount type"
            render={(_, record) => record.type}
        />
        <DiscountRecordColumn
            key = "amount"
            title = "Amount"
            render={(_, record) => showAmount(record)}
        />
        <DiscountRecordColumn
            key = "period"
            title = "Period"
            render={(_, record) =>
                ((new Date (Date.parse(record.activeFrom.toString())).toLocaleDateString())
                + " ~ " +
                (new Date (Date.parse(record.activeTo.toString())).toLocaleDateString()))}
        />
        <DiscountRecordColumn
            key = "editRemove"
            render={(_, record) =>
                <div>
                    <NavLink to={`/admin/discount/${record.id}`}>
                        <Button icon="edit"/>
                    </NavLink>
                    <Popconfirm
                        title="Are you sure you want to delete this discount?"
                        cancelText="No"
                        okText="Yes"
                        onConfirm={() => props.onDelete(record.id)}>
                        <Button icon="delete" />
                    </Popconfirm>
                </div>
            }
        />
    </DiscountRecordTable>
);
