import * as React from "react";

import { RouteComponentProps } from "react-router-dom";

import { Button, notification, Spin } from "antd";

import { NavLink } from "react-router-dom";
import * as api from "../../../../api";
import { CategoryTree } from "../../../../model/CategoryTree";
import { Product } from "../../../../model/Product";
import { ProductTable } from "../common/Table/ProductTable";

import { ButtonGroup } from "semantic-ui-react";
import "../../../../../style/admin/product.css";
import { ProductExport } from "./import/ProductExport";
import { ProductImport } from "./import/ProductImport";

interface ProductsState {
    isLoading: boolean;
    products: Product[];
    categories: CategoryTree[];
}

export class Products extends React.Component<RouteComponentProps<{}>, ProductsState> {
    state: ProductsState = {
        categories: [],
        isLoading: true,
        products: [],
    };

    async componentWillMount () {
        const [productResponse, categoriesResponse] =
            await Promise.all([api.product.getAllProducts(), api.category.getCategoryTree()]);
        if (api.isError(productResponse)) {
            notification.error({message: "Failed to fetch product data", description: productResponse.message});
            this.setState({isLoading: false});
            return;
        }
        if(api.isError(categoriesResponse)) {
            notification.error({message: "Failed to fetch category data", description: categoriesResponse.message});
            this.setState({isLoading: false});
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
                <ButtonGroup>
                    <NavLink to={`/admin/product/create`}>
                        <Button>Add new product</Button>
                    </NavLink>
                    <ProductImport navigateToJob={id => this.props.history.push(`/admin/imports/${id}`)}/>
                    <ProductExport />
                </ButtonGroup>
                <ProductTable
                    products={this.state.products}
                    categories={this.state.categories}
                    onDelete={idToDelete => this.handleDelete(idToDelete)}
                    />
            </Spin>
        );
    }
}
