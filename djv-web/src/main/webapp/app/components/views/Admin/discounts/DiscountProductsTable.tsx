// tslint:disable:max-classes-per-file
import * as React from "react";
import { Table, Button, Popconfirm } from "antd";
import { WithKey } from "../../../../utils/table";
import { NavLink } from "react-router-dom";
import { Product } from "../../../../model/Product";
import { formatPrice } from "../../../../utils/common";

type ProductRecord = Product & WithKey;

interface DiscountProductsTableProps {
    products: Product[];
    onSelect: (selectedProducts: Product[]) => void;
    selectionDisabled?: boolean;
    selectedProductIds?: number[];
}

class ProductRecordTable extends Table<ProductRecord> {}

class ProductRecordColumn extends Table.Column<ProductRecord> {}

export const DiscountProductsTable = (props: DiscountProductsTableProps) => (
    <ProductRecordTable
        bordered={true}
        rowSelection={{
            onChange: (keys,rows: any) => props.onSelect(rows),
            getCheckboxProps: _ => ({disabled: props.selectionDisabled}),
            selectedRowKeys: props.selectedProductIds
        }}
        dataSource={props.products.map(p => ({...p, key: p.id}))}
        pagination={{pageSize: 25, hideOnSinglePage: true}}
    >
        <ProductRecordColumn
            key = "picture"
            title = "Picture"
            render={(_, record) => <img className="product-table-photo" src={record.mainImageUrl} />}
        />
        <ProductRecordColumn
            key = "name"
            title = "Name"
            render={(_, record) => record.name}
        />
        <ProductRecordColumn
            key = "price"
            title = "Price"
            render={(_, record) => formatPrice(record.price)}
        />
    </ProductRecordTable>
);
