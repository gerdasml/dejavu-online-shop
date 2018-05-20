// tslint:disable:max-classes-per-file
import * as React from "react";
import { Discount, DiscountTarget, DiscountType } from "../../../../model/Discount";
import { Table } from "antd";
import { WithKey } from "../../../../utils/table";

type DiscountRecord = Discount & WithKey;

interface DiscountsTableProps {
    discounts: Discount[];
    onDelete: (id: number, targetType: DiscountTarget) => void;
}

class DiscountRecordTable extends Table<DiscountRecord> {}

class DiscountRecordColumn extends Table.Column<DiscountRecord> {}

const getTargetName = (record: DiscountRecord) => {
    if(record.target === undefined) {
        return "-";
    }
    return record.target.name;
};

const showAmount = (record: DiscountRecord) => {
    if(record.type === DiscountType.ABSOLUTE) {
        const absoluteAmount = record.amount + "€";
        return absoluteAmount;
    }
    const percentageAmount = record.amount + "%";
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
            render={(_, record) => (record.dateStart + " ~ " + record.dateEnd)}
        />
        // TODO: FINISHED HERE ( and create adminMain.tsx extra rout for editing)
        {/* <DiscountRecordColumn
            key = "editRemove"
            render={(_, record) =>
                <div>
                    <NavLink to={`/admin/product/${record.id}`}>
                        <Button icon="edit"/>
                    </NavLink>
                    <Popconfirm
                        title="Are you sure you want to delete this product?"
                        cancelText="No"
                        okText="Yes"
                        onConfirm={() => props.onDelete(record.id)}>
                        <Button icon="delete" />
                    </Popconfirm>
                </div>
            }
        /> */}
    </DiscountRecordTable>
);
