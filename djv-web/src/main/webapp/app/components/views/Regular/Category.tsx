import * as React from "react";

import { Loader} from "semantic-ui-react";

import { RouteComponentProps } from "react-router-dom";
import {Product} from "../../../model/Product";

import { notification } from "antd";
import * as api from "../../../api";
import { ProductContainer } from "../../dumb/Product/ProductContainer";

interface CategoryRouteProps {
    category: string;
    subcategory?: string;
    subsubcategory?: string;
}

interface CategoryState {
    products: Product[];
    isLoading: boolean;
    error?: string;
}
export class Category extends React.Component<RouteComponentProps<CategoryRouteProps>, CategoryState> {
    state: CategoryState = {
        error: "",
        isLoading: true,
        products: []
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
        const identifier = this.buildIdentifier();
        const response = await api.product.searchForProducts({categoryIdentifier: identifier});
        if (api.isError(response)) {
            notification.error({ message: "Failed to fetch product data", description: response.message });
            this.setState({ error: response.message, isLoading: false });
            return;
        }
        this.setState({ products: response, isLoading: false });
    }

    buildIdentifier () {
        const {category, subcategory, subsubcategory} = this.props.match.params;
        let result = category;
        if (subcategory !== undefined) {
            result += "/" + subcategory;
        }
        if (subsubcategory !== undefined) {
            result += "/" + subsubcategory;
        }
        return result;
    }

    render () {
        return (
            <div>
                { this.state.isLoading
                ? <Loader active inline="centered" />
                : <ProductContainer products={this.state.products}/>
                }
            </div>
            );
    }
}
