import * as React from "react";

import { notification } from "antd";

import { Header, Loader } from "semantic-ui-react";

import * as api from "../../../api";

import { Product } from "../../../model/Product";
import { ProductContainer } from "../../dumb/Product/ProductContainer";

interface HomeState {
    isLoading: boolean;
    products: Product[];
}

export class Home extends React.Component< {}, HomeState> {
    state: HomeState = {
        isLoading: true,
        products: [],
    };

    async componentDidMount () {
        const productsInfo = await api.product.getAllProducts();
        if(api.isError(productsInfo)) {
            notification.error({message: "Failed to load products", description: productsInfo.message});
        } else {
            this.setState({
                ...this.state,
                products: productsInfo,
            });
        }
        this.setState({
            ...this.state,
            isLoading: false,
        });
    }

    render () {
        return (
            <div>
                { this.state.isLoading
                ?
                <Loader active inline="centered" />
                :
                <ProductContainer products={this.state.products}/>
                }
            </div>
        );
    }
}
