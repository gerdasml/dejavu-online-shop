import * as React from "react";

import { notification, message } from "antd";

import { Header, Loader } from "semantic-ui-react";

import * as api from "../../../api";

import { Product } from "../../../model/Product";
import { ProductList } from "../../dumb/Product/ProductList";

import { config } from "../../../config";
import { Cart } from "../../../model/Cart";

import * as CartManager from "../../../utils/cart";

interface HomeState {
    isLoading: boolean;
    products: Product[];
    activePage: number;
    productCount?: number;
    cart?: Cart;
}

export class Home extends React.Component< {}, HomeState> {
    state: HomeState = {
        isLoading: true,
        products: [],
        activePage: 1
    };

    async componentDidMount () {
        const [productsInfo, productCount, cartInfo] = await Promise.all([
            api.product.getAllProducts(0, config.productsPerPage),
            api.product.getTotalProductCount(),
            CartManager.getCart()
        ]);

        if(api.isError(productsInfo)) {
            notification.error({message: "Failed to load products", description: productsInfo.message});
        } else {
            this.setState({...this.state, products: productsInfo});
        }

        if(api.isError(productCount)) {
            notification.error({message: "Failed to load products", description: productCount.message});
        } else {
            this.setState({...this.state, productCount});
        }

        if(api.isError(cartInfo)) {
            notification.error({message: "Failed to load cart", description: cartInfo.message});
        } else {
            this.setState({...this.state, cart: cartInfo});
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

    async handleAddToCart (addedProduct: Product) {
        const response = await CartManager.addProduct(addedProduct, 1);
        if(api.isError(response)) {
            notification.error({message: "Failed to add product to cart", description: response.message});
        } else {
            this.setState({
                ...this.state,
                cart: response
            });
            message.success("Successfully added product to cart");
        }
    }

    render () {
        return (
            <div>
                { this.state.isLoading
                ?
                <Loader active inline="centered" />
                :
                <ProductList
                    products={this.state.products}
                    activePage={this.state.activePage}
                    onPageChange={this.handlePageChange.bind(this)}
                    totalProductCount={this.state.productCount}
                    cart={this.state.cart}
                    onAddToCart={this.handleAddToCart.bind(this)}
                />
                }
            </div>
        );
    }
}
