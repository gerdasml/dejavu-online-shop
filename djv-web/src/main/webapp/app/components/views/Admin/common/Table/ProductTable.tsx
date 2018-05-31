// tslint:disable:max-classes-per-file
import * as React from "react";

import { Button, Popconfirm, Table } from "antd";
import { NavLink } from "react-router-dom";
import { Product } from "../../../../../model/Product";
import { WithKey } from "../../../../../utils/table";

import "../../../../../../style/product.css";
import { CategoryTree } from "../../../../../model/CategoryTree";
import { findCategoryFromTree } from "../../../../../utils/categories";
import { formatPrice } from "../../../../../utils/common";
import { TablePaginationConfig } from "antd/lib/table";
import { config } from "../../../../../config";

import "../../../../../../style/admin/products.css";

type ProductRecord = Product & WithKey;

interface ProductTableProps {
    products: Product[];
    categories: CategoryTree[];
    onDelete: (id: number) => void;
    isLoading: boolean;
    onChange: (pagination: TablePaginationConfig, filters: string[], sorter: any) => void;
    totalItems: number;
    pageSize: number;
}

class ProductRecordTable extends Table<ProductRecord> {}

class ProductRecordColumn extends Table.Column<ProductRecord> {}

export const ProductTable = (props: ProductTableProps) => (
    <ProductRecordTable className="productRecordTable"
        scroll={{x: config.adminTableScrollWidth.common}}
        bordered={true}
        dataSource={props.products.map(p => ({...p, key: p.id}))}
        pagination={{pageSize: props.pageSize, hideOnSinglePage: true, total: props.totalItems}}
        loading={props.isLoading}
        onChange={props.onChange}
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
            key = "skuCode"
            title = "Sku code"
            render={(_, record) => record.skuCode}
        />
        <ProductRecordColumn
            key = "category"
            title = "Category"
            render={(_, record) => {
                const cat = findCategoryFromTree(props.categories, record.categoryId);
                return cat === undefined ? "-" : cat.name;
            }}
        />
        <ProductRecordColumn
            key = "price"
            title = "Price"
            render={(_, record) => formatPrice(record.price)}
        />
        <ProductRecordColumn
            key = "editRemove"
            render={(_, record) =>
                <div>
                    <NavLink to={`/admin/product/${record.id}`}>
                        <Button icon="edit" className="editButton"/>
                    </NavLink>
                    <Popconfirm
                        title="Are you sure you want to delete this product?"
                        cancelText="No"
                        okText="Yes"
                        onConfirm={() => props.onDelete(record.id)}>
                        <Button icon="delete" className="editButton"/>
                    </Popconfirm>
                </div>
            }
        />
    </ProductRecordTable>
);
