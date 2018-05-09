import * as React from "react";

import { notification, Spin } from "antd";

import { Product } from "../../../../model/Product";
import { ProductTable } from "../common/Table/ProductTable";

import * as api from "../../../../api";

interface ProductsState {
    isLoading: boolean;
    products: Product[];
}

export class Products extends React.Component<never, ProductsState> {
    state: ProductsState = {
        isLoading: true,
        products: [],
    };

    async componentWillMount () {
        const response = await api.product.getAllProducts();
        if (api.isError(response)) {
            notification.error({message: "Failed to fetch data", description: response.message});
            return;
        }
        this.setState({products: response, isLoading: false});
    }

    render () {
        const { isLoading } = this.state;
        return (
            <Spin spinning={isLoading} size="large">
                <ProductTable products={this.state.products} />
            </Spin>
        );
    }
}
