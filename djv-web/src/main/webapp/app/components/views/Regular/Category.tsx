import * as React from "react";

import { Card, Loader} from "semantic-ui-react";

import { RouteComponentProps } from "react-router-dom";
import {Product} from "../../../model/Product";
import {ProductCard} from "../../dumb/Home/ProductCard";

import { notification } from "antd";
import * as api from "../../../api";

interface CategoryRouteProps {
    id: number;
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
        const response = await api.product.getProductsByCategory(props.match.params.id);
        if (api.isError(response)) {
            notification.error({ message: "Failed to fetch product data", description: response.message });
            this.setState({ error: response.message, isLoading: false });
            return;
        }
        this.setState({ products: response, isLoading: false });
    }
    render () {
        return (
            <div>
            {this.state.isLoading?
            <Loader active inline="centered" /> :
            <Card.Group itemsPerRow={5} doubling>
                {this.state.products.map((x, i) => <ProductCard key={i} product={x} />)}
            </Card.Group>}
            </div>
            );
    }
}
