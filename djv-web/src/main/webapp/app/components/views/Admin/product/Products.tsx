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
import { config } from "../../../../config";

import "../../../../../style/admin/products.css";

interface ProductsState {
    isLoading: boolean;
    products: Product[];
    categories: CategoryTree[];
    productCount?: number;
}

export class Products extends React.Component<RouteComponentProps<{}>, ProductsState> {
    state: ProductsState = {
        categories: [],
        isLoading: true,
        products: [],
    };

    async componentWillMount () {
        const [productResponse, categoriesResponse, productCountResponse] =
            await Promise.all([
                api.product.getAllProducts(0, config.productsPerPage),
                api.category.getCategoryTree(),
                api.product.getTotalProductCount()
            ]);
        if (api.isError(productResponse)) {
            notification.error({message: "Failed to fetch product data", description: productResponse.message});
            this.setState({...this.state, isLoading: false});
            return;
        }
        if(api.isError(categoriesResponse)) {
            notification.error({message: "Failed to fetch category data", description: categoriesResponse.message});
            this.setState({...this.state, isLoading: false});
            return;
        }
        if (api.isError(productCountResponse)) {
            notification.error({message: "Failed to fetch product data", description: productCountResponse.message});
            this.setState({...this.state, isLoading: false});
            return;
        }
        this.setState({
            products: productResponse,
            categories: categoriesResponse,
            isLoading: false,
            productCount: productCountResponse
        });
    }

    async handleDelete (id: number) {
        await api.product.deleteProduct(id);
        this.setState({...this.state, products: this.state.products.filter(p => p.id !== id)});
    }

    async fetchProducts (offset: number, limit: number) {
        const response = await api.product.getAllProducts(offset, limit);
        if (api.isError(response)) {
            notification.error({message: "Failed to fetch products", description: response.message});
            return;
        }
        this.setState({...this.state, products: response});
    }

    async loadNewProducts (page: number, pageSize: number) {
        this.setState({...this.state, isLoading: true});
        const offset = (page-1)*pageSize;
        const limit = pageSize;
        await this.fetchProducts(offset, limit);
        this.setState({...this.state, isLoading: false});
    }

    render () {
        const { isLoading } = this.state;
        return (
            <div>
                <ButtonGroup id="buttonGroup">
                    <NavLink to={`/admin/product/create`}>
                        <Button className="productsButton">Add new product</Button>
                    </NavLink>
                    <ProductImport navigateToJob={id => this.props.history.push(`/admin/imports/${id}`)}/>
                    <ProductExport />
                </ButtonGroup>
                <ProductTable
                    products={this.state.products}
                    categories={this.state.categories}
                    onDelete={idToDelete => this.handleDelete(idToDelete)}
                    isLoading={isLoading}
                    onChange={(p, f, s) => this.loadNewProducts(p.current, p.pageSize)}
                    totalItems={this.state.productCount}
                    pageSize={config.productsPerPage}
                />
            </div>
        );
    }
}
