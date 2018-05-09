import * as React from "react";

import { Button, Icon, Popconfirm, Table } from "antd";
import { NavLink } from "react-router-dom";
import { Product } from "../../../../../model/Product";
import { addKey, WithKey } from "../../../../../utils/table";

import "../../../../../../style/product.css";
import { CategoryTree } from "../../../../../model/CategoryTree";
import { findCategoryFromTree } from "../../../../../utils/categories";

type ProductRecord = Product & WithKey;

interface ProductTableProps {
    products: Product[];
    categories: CategoryTree[];
    onDelete: (id: number) => void;
}

class ProductRecordTable extends Table<ProductRecord> {}

// tslint:disable-next-line:max-classes-per-file
class ProductRecordColumn extends Table.Column<ProductRecord> {}

// tslint:disable-next-line:max-classes-per-file
export const ProductTable = (props: ProductTableProps) => (
    <ProductRecordTable
        bordered={true}
        dataSource={props.products.map(p => ({...p, key: p.id}))}
        pagination={{pageSize: 25, hideOnSinglePage: true}}>
        <ProductRecordColumn
            key = "picture"
            title = "Picture"
            render={(_, record) => <img className="product-photo-table" src={record.mainImageUrl} />}
        />
        <ProductRecordColumn
            key = "name"
            title = "Name"
            render={(_, record) => record.name}
        />
        <ProductRecordColumn
            key = "category"
            title = "Category"
            render={(_, record) => findCategoryFromTree(props.categories, record.categoryId).name}
        />
        <ProductRecordColumn
            key = "price"
            title = "Price"
            render={(_, record) => record.price}
        />
        <ProductRecordColumn
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
        />
    </ProductRecordTable>
);
