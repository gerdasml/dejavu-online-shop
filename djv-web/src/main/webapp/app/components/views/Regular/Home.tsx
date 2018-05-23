import * as React from "react";

import { notification } from "antd";

import { Header, Loader } from "semantic-ui-react";

import * as api from "../../../api";

import { Product } from "../../../model/Product";
import { ProductContainer } from "../../dumb/Product/ProductContainer";

import { config } from "../../../config";

interface HomeState {
    isLoading: boolean;
    products: Product[];
    activePage: number;
    productCount?: number;
}

export class Home extends React.Component< {}, HomeState> {
    state: HomeState = {
        isLoading: true,
        products: [],
        activePage: 1
    };

    async componentDidMount () {
        const [productsInfo, productCount] = await Promise.all([
            api.product.getAllProducts(0, config.productsPerPage),
            api.product.getTotalProductCount()
        ]);
        if(api.isError(productsInfo)) {
            notification.error({message: "Failed to load products", description: productsInfo.message});
        } else if(api.isError(productCount)) {
            notification.error({message: "Failed to load products", description: productCount.message});
        } else {
            this.setState({
                ...this.state,
                products: productsInfo,
                productCount
            });
        }
        this.setState({
            ...this.state,
            isLoading: false,
        });
    }

    async handlePageChange (page: number) {
        this.setState({...this.state, isLoading: true});
        const offset = (page-1) * config.productsPerPage;
        const limit = config.productsPerPage;
        const products = await api.product.getAllProducts(offset, limit);
        if (api.isError(products)) {
            notification.error({message: "Failed to load products", description: products.message});
        } else {
            this.setState({...this.state, products, activePage: 1});
        }
        this.setState({...this.state, activePage: page, isLoading: false});
    }

    render () {
        return (
            <div>
                { this.state.isLoading
                ?
                <Loader active inline="centered" />
                :
                <ProductContainer
                    products={this.state.products}
                    activePage={this.state.activePage}
                    onPageChange={this.handlePageChange.bind(this)}
                    onFilterChange={(mn, mx, props) => console.log(mn, mx, props)}
                    totalProductCount={this.state.productCount}
                />
                }
            </div>
        );
    }
}
