import * as React from "react";

import { Loader} from "semantic-ui-react";

import { RouteComponentProps } from "react-router-dom";
import {Product} from "../../../model/Product";
import {Category as CategoryModel, CategoryInfo} from "../../../model/Category";

import { notification, message } from "antd";
import * as api from "../../../api";
import { ProductList } from "../../dumb/Product/ProductList";

import { config } from "../../../config";
import { ProductProperties } from "../../../model/ProductProperties";
import { ProductSearchRequest } from "../../../api/product";
import { ProductFilter } from "../../dumb/Product/ProductFilter";
import { Cart } from "../../../model/Cart";

import * as CartManager from "../../../utils/cart";

interface ProductContainerProps {
    categoryIdentifier?: string;
}

interface ProductContainerState {
    isProductInfoLoading: boolean;
    isFilterInfoLoading: boolean;
    activePage: number;
    minPrice?: number;
    maxPrice?: number;
    cart?: Cart;
    products: Product[];
    categoryInfo?: CategoryInfo;
    productCount?: number;
    selectedProperties: ProductProperties[];
}

export class ProductContainer extends React.Component<ProductContainerProps, ProductContainerState> {
    state: ProductContainerState = {
        isFilterInfoLoading: true,
        isProductInfoLoading: true,
        products: [],
        activePage: 1,
        selectedProperties: []
    };
    // required to load data on initial render
    async componentDidMount () {
        await this.loadData(this.props);
    }

    // required to load data on each url change
    async componentWillReceiveProps (nextProps: ProductContainerProps) {
        await this.loadData(nextProps);
    }

    async loadData (props: ProductContainerProps) {
        this.setState({
            ...this.state,
            isProductInfoLoading: true,
            isFilterInfoLoading: true
        });
        await this.fetchData(props);
        this.setState({
            ...this.state,
            isProductInfoLoading: false,
            isFilterInfoLoading: false
        });
    }

    async fetchData (props: ProductContainerProps) {
        const productPromise = (
            props.categoryIdentifier === undefined
            ? api.product.getAllProducts(0, config.productsPerPage)
            : api.product.searchForProducts({categoryIdentifier: props.categoryIdentifier}, 0, config.productsPerPage)
        ).then(response => {
            if (api.isError(response)) {
                notification.error({ message: "Failed to fetch product data", description: response.message });
            } else {
                this.setState({...this.state, products: response});
            }
        });

        const cartPromise =
            CartManager.getCart()
            .then(response => {
                if (api.isError(response)) {
                    notification.error({message: "Failed to load cart", description: response.message});
                } else {
                    this.setState({
                        ...this.state,
                        cart: response
                    });
                }
            });

        let categoryPromise;

        if (props.categoryIdentifier !== undefined) {
            categoryPromise =
                api.category.getCategoryByIdentifier(props.categoryIdentifier)
                .then(response => {
                    if (api.isError(response)) {
                        notification.error({
                            message: "Failed to fetch category data",
                            description: response.message
                        });
                    } else {
                        return api.category.getCategoryInfo(response.id);
                    }
                })
                .then(response => {
                    if (api.isError(response)) {
                        notification.error({
                            message: "Failed to fetch category info",
                            description: response.message
                        });
                    } else {
                        this.setState({
                            ...this.state,
                            categoryInfo: response,
                            productCount: response.productCount
                        });
                    }
                });
        }

        let productCountPromise;
        if (this.props.categoryIdentifier === undefined) {
            productCountPromise =
                api.product.getTotalProductCount()
                .then(response => {
                    if (api.isError(response)) {
                        notification.error({
                            message: "Failed to fetch product information",
                            description: response.message
                        });
                    } else {
                        this.setState({
                            ...this.state,
                            productCount: response
                        });
                    }
                });
        }
        await Promise.all([productPromise, cartPromise, categoryPromise, productCountPromise]);
        this.setState({
            isProductInfoLoading: false,
            isFilterInfoLoading: false,
        });
    }

    async handlePageChange (page: number) {
        this.setState({
            ...this.state,
            isProductInfoLoading: true
        });
        const request = this.buildSearchRequest(
            this.props.categoryIdentifier,
            this.state.minPrice,
            this.state.maxPrice,
            this.state.selectedProperties);
        await this.fetchNewProducts(request, page);
        this.setState({...this.state, activePage: page, isProductInfoLoading: false});
    }

    async fetchNewProducts (req: ProductSearchRequest, page: number) {
        const offset = (page-1) * config.productsPerPage;
        const limit = config.productsPerPage;
        const products = await api.product.searchForProducts(req, offset, limit);
        if (api.isError(products)) {
            notification.error({message: "Failed to load products", description: products.message});
        } else {
            this.setState({...this.state, products, activePage: 1});
        }
    }

    async handleFilterChange (minPrice: number, maxPrice: number, properties: ProductProperties[]) {
        this.setState({
            ...this.state,
            isProductInfoLoading: true,
            minPrice,
            maxPrice,
            selectedProperties: properties
        });
        const request = this.buildSearchRequest(this.props.categoryIdentifier, minPrice, maxPrice, properties);
        await this.fetchNewProducts(request, 1);
        this.setState({...this.state, isProductInfoLoading: false});
    }

    buildSearchRequest = (identifier: string, minPrice: number, maxPrice: number, properties: ProductProperties[]) => ({
        categoryIdentifier: identifier,
        minPrice,
        maxPrice,
        properties
    })

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

    renderFilter () {
        if (this.props.categoryIdentifier === undefined) return "";
        if (this.state.isFilterInfoLoading) return "";
        return (
            <ProductFilter
                minPrice={this.state.categoryInfo.minPrice}
                maxPrice={this.state.categoryInfo.maxPrice}
                availableProperties={this.state.categoryInfo.availableProperties}
                onFilterChange={this.handleFilterChange.bind(this)}
            />
        );
    }

    renderProductList () {
        if (this.state.isProductInfoLoading) return <Loader active inline="centered" />;
        return (
            <ProductList
                totalProductCount={this.state.productCount}
                products={this.state.products}
                activePage={this.state.activePage}
                onPageChange={this.handlePageChange.bind(this)}
                cart={this.state.cart}
                onAddToCart={this.handleAddToCart.bind(this)}
            />
        );
    }
    render () {
        return [this.renderFilter(), this.renderProductList()];
    }
}
