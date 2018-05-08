import * as React from "react";
import { RouteComponentProps } from "react-router-dom";

import { notification, Spin } from "antd";

import * as api from "../../../../api";
import { Product } from "../../../../model/Product";
import { ProductForm } from "./ProductForm";

interface SingleProductState {
    product?: Product;
}

interface SingleProductProps {
    id: number;
}

export class SingleProduct extends React.Component<RouteComponentProps<SingleProductProps>,SingleProductState> {
    state: SingleProductState = {};

    async componentWillReceiveProps (nextProps: RouteComponentProps<SingleProductProps>) {
        const response = await api.product.getProduct(nextProps.match.params.id);
        if (api.isError(response)) {
            notification.error({ message: "Failed to fetch product data", description: response.message });
            return;
        }
        this.setState({product: response});
    }
    render () {
        return (
            <Spin spinning={this.state.product === undefined}>
                <ProductForm product={this.state.product} />
            </Spin>
        );
    }
}
