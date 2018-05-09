import * as React from "react";

import { Button, notification, Spin } from "antd";

import { NavLink } from "react-router-dom";
import * as api from "../../../../api";
import { CategoryTree } from "../../../../model/CategoryTree";
import { Product } from "../../../../model/Product";
import { ProductTable } from "../common/Table/ProductTable";

interface ProductsState {
    isLoading: boolean;
    products: Product[];
    categories: CategoryTree[];
}

export class Products extends React.Component<never, ProductsState> {
    state: ProductsState = {
        categories: [],
        isLoading: true,
        products: [],
    };

    async componentWillMount () {
        const [productResponse, categoriesResponse] =
            await Promise.all([api.product.getAllProducts(), api.category.getCategoryTree()]);
        if (api.isError(productResponse)) {
            notification.error({message: "Failed to fetch data", description: productResponse.message});
            return;
        }
        if(api.isError(categoriesResponse)) {
            notification.error({message: "Failed to fetch data", description: categoriesResponse.message});
            return;
        }
        this.setState({products: productResponse, categories: categoriesResponse, isLoading: false});
    }

    async handleDelete (id: number) {
        await api.product.deleteProduct(id);
        this.setState({...this.state, products: this.state.products.filter(p => p.id !== id)});
    }

    render () {
        const { isLoading } = this.state;
        return (
            <Spin spinning={isLoading} size="large">
                <NavLink to={`/admin/product/create`}>
                    <Button>Add new product</Button>
                </NavLink>
                <ProductTable
                    products={this.state.products}
                    categories={this.state.categories}
                    onDelete={idToDelete => this.handleDelete(idToDelete)}
                    />
            </Spin>
        );
    }
}
