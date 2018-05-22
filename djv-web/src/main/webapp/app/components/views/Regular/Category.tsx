import * as React from "react";

import { Loader} from "semantic-ui-react";

import { RouteComponentProps } from "react-router-dom";
import {Product} from "../../../model/Product";
import {Category as CategoryModel} from "../../../model/Category";

import { notification } from "antd";
import * as api from "../../../api";
import { ProductContainer } from "../../dumb/Product/ProductContainer";

interface CategoryRouteProps {
    identifier: string;
}

interface CategoryState {
    products: Product[];
    category?: CategoryModel;
    isLoading: boolean;
    error?: string;
    activePage: number;
}

const PRODUCTS_PER_PAGE = 20;

export class Category extends React.Component<RouteComponentProps<CategoryRouteProps>, CategoryState> {
    state: CategoryState = {
        error: "",
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
                [ api.product.searchForProducts({categoryIdentifier: identifier}, 0, PRODUCTS_PER_PAGE)
                , api.category.getCategoryByIdentifier(props.match.params.identifier)
                ]);
        if (api.isError(productResponse)) {
            notification.error({ message: "Failed to fetch product data", description: productResponse.message });
            this.setState({ error: productResponse.message, isLoading: false });
            return;
        }
        if (api.isError(categoryResponse)) {
            notification.error({ message: "Failed to fetch category data", description: categoryResponse.message});
            this.setState({ error: categoryResponse.message, isLoading: false});
            return;
        }
        this.setState({
            activePage: 1,
            products: productResponse,
            category: categoryResponse,
            isLoading: false
        });
    }

    async handlePageChange (page: number) {
        this.setState({...this.state, isLoading: true});
        const offset = (page-1) * PRODUCTS_PER_PAGE;
        const limit = PRODUCTS_PER_PAGE;
        const products =
            await api.product.searchForProducts({
                categoryIdentifier: this.props.match.params.identifier
            }, offset, limit);
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
                ? <Loader active inline="centered" />
                : <ProductContainer
                    products={this.state.products}
                    category={this.state.category}
                    activePage={this.state.activePage}
                    onPageChange={this.handlePageChange.bind(this)}
                    onFilterChange={(mn, mx, props) => console.log(mn, mx, props)}
                />
                }
            </div>
            );
    }
}
