import * as React from "react";

import { Table } from "antd";
import { Product } from "../../../../../model/Product";
import { addKey, WithKey } from "../../../../../utils/table";

type ProductRecord = Product & WithKey;

interface ProductTableProps {
    products: Product[];
}

class ProductRecordTable extends Table<ProductRecord> {}

// tslint:disable-next-line:max-classes-per-file
class ProductRecordColumn extends Table.Column<ProductRecord> {}

// tslint:disable-next-line:max-classes-per-file
export const ProductTable = (props: ProductTableProps) => (
    <ProductRecordTable
        bordered={true}
        dataSource={props.products.map(addKey)}
        pagination={{pageSize: 25, hideOnSinglePage: true}}>
        <ProductRecordColumn
            key = "picture"
            title = "Picture"
            render={(_, record) => record.mainImageUrl}
        />
        <ProductRecordColumn
            key = "name"
            title = "Name"
            render={(_, record) => record.name}
        />
        <ProductRecordColumn
            key = "category"
            title = "Category"
            render={(_, record) => record.categoryId}
        />
        <ProductRecordColumn
            key = "price"
            title = "Price"
            render={(_, record) => record.price}
        />
        <ProductRecordColumn
            key = "editRemove"
            dataIndex="buttons"
        />
    </ProductRecordTable>
);
