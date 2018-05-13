import * as React from "react";

import { Card, Header, Loader } from "semantic-ui-react";

import * as api from "../../../api";

import { products } from "../../../data/products";

import { Product } from "../../../model/Product";
import { ProductCard } from "../../dumb/Home/ProductCard";
import { ProductContainer } from "../../dumb/Product/ProductContainer";

interface HomeState {
    error?: string;
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
            console.log(productsInfo.message);
            this.setState({
                ...this.state,
                error: productsInfo.message,
                isLoading: false,
            });
        } else {
            this.setState({
                ...this.state,
                isLoading: false,
                products: productsInfo,
            });
        }
    }

    render () {
        return (
            <div>
                <Header size="large">Home</Header>
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
