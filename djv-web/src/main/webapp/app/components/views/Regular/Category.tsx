import * as React from "react";

import { Loader} from "semantic-ui-react";

import { RouteComponentProps } from "react-router-dom";
import {Product} from "../../../model/Product";
import {Category as CategoryModel, CategoryInfo} from "../../../model/Category";

import { notification } from "antd";
import * as api from "../../../api";
import { ProductContainer } from "../../dumb/Product/ProductContainer";

import { config } from "../../../config";
import { ProductProperties } from "../../../model/ProductProperties";
import { ProductSearchRequest } from "../../../api/product";

interface CategoryRouteProps {
    identifier: string;
}

interface CategoryState {
    products: Product[];
    category?: CategoryModel;
    categoryInfo?: CategoryInfo;
    isLoading: boolean;
    activePage: number;
    minPrice?: number;
    maxPrice?: number;
}

export class Category extends React.Component<RouteComponentProps<CategoryRouteProps>, CategoryState> {
    state: CategoryState = {
        isLoading: true,
        products: [],
        activePage: 1
    };
    // required to load data on initial render
    async componentDidMount () {
        await this.loadData(this.props);
    }

    // required to load data on each url change
    async componentWillReceiveProps (nextProps: RouteComponentProps<CategoryRouteProps>) {
        await this.loadData(nextProps);
    }

    async loadData (props: RouteComponentProps<CategoryRouteProps>) {
        const identifier = props.match.params.identifier;
        const [productResponse, categoryResponse] =
            await Promise.all(
                [ api.product.searchForProducts({categoryIdentifier: identifier}, 0, config.productsPerPage)
                , api.category.getCategoryByIdentifier(props.match.params.identifier)
                ]);
        if (api.isError(productResponse)) {
            notification.error({ message: "Failed to fetch product data", description: productResponse.message });
            this.setState({ ...this.state, isLoading: false });
            return;
        }
        if (api.isError(categoryResponse)) {
            notification.error({ message: "Failed to fetch category data", description: categoryResponse.message});
            this.setState({ ...this.state, isLoading: false});
            return;
        }
        const categoryInfoResponse = await api.category.getCategoryInfo(categoryResponse.id);
        if (api.isError(categoryInfoResponse)) {
            notification.error({message: "Failed to fetch category info", description: categoryInfoResponse.message});
            this.setState({ ...this.state, isLoading: false});
            return;
        }
        this.setState({
            activePage: 1,
            products: productResponse,
            category: categoryResponse,
            categoryInfo: categoryInfoResponse,
            isLoading: false
        });
    }

    async handlePageChange (page: number) {
        this.setState({
            ...this.state,
            isLoading: true
        });
        const request = this.buildSearchRequest(
            this.props.match.params.identifier,
            this.state.minPrice,
            this.state.maxPrice);
        await this.fetchNewProducts(request, page);
        this.setState({...this.state, activePage: page, isLoading: false});
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
            isLoading: true,
            minPrice,
            maxPrice
        });
        const request = this.buildSearchRequest(this.props.match.params.identifier, minPrice, maxPrice);
        await this.fetchNewProducts(request, this.state.activePage);
        this.setState({...this.state, isLoading: false});
    }

    buildSearchRequest = (identifier: string, minPrice: number, maxPrice: number) => ({
        categoryIdentifier: identifier,
        minPrice,
        maxPrice
    })

    render () {
        return (
            <div>
                { this.state.isLoading
                ? <Loader active inline="centered" />
                : <ProductContainer
                    totalProductCount={this.state.categoryInfo.productCount}
                    availableProperties={this.state.categoryInfo.availableProperties}
                    products={this.state.products}
                    category={this.state.category}
                    activePage={this.state.activePage}
                    onPageChange={this.handlePageChange.bind(this)}
                    onFilterChange={this.handleFilterChange.bind(this)}
                    minPrice={this.state.categoryInfo.minPrice}
                    maxPrice={this.state.categoryInfo.maxPrice}
                />
                }
            </div>
            );
    }
}
