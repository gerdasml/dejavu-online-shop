import * as React from "react";

import { notification } from "antd";

import { Header, Loader } from "semantic-ui-react";

import * as api from "../../../api";

import { Product } from "../../../model/Product";
import { ProductContainer } from "../../dumb/Product/ProductContainer";

interface HomeState {
    isLoading: boolean;
    products: Product[];
    activePage: number;
}

const PRODUCTS_PER_PAGE = 20;

export class Home extends React.Component< {}, HomeState> {
    state: HomeState = {
        isLoading: true,
        products: [],
        activePage: 1
    };

    async componentDidMount () {
        const productsInfo = await api.product.getAllProducts(0, PRODUCTS_PER_PAGE);
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

    async handlePageChange (page: number) {
        this.setState({...this.state, isLoading: true});
        const offset = (page-1) * PRODUCTS_PER_PAGE;
        const limit = PRODUCTS_PER_PAGE;
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
                    totalProductCount={0} // TODO: fetch actual count
                />
                }
            </div>
        );
    }
}
