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
import { ProductContainer } from "../../smart/Product/ProductContainer";

interface CategoryRouteProps {
    identifier: string;
}

interface CategoryRouteState {
    categoryInfo?: CategoryInfo;
}

export class Category extends React.Component<RouteComponentProps<CategoryRouteProps>, CategoryRouteState> {
    state: CategoryRouteState = {};

    async componentWillMount () {
        await this.fetchData(this.props);
    }

    async componentWillReceiveProps (props: RouteComponentProps<CategoryRouteProps>) {
        await this.fetchData(props);
    }

    async fetchData (props: RouteComponentProps<CategoryRouteProps>) {
        const categoryResponse = await api.category.getCategoryByIdentifier(props.match.params.identifier);
        if (api.isError(categoryResponse)) {
            notification.error({message: "Failed to fetch category data", description: categoryResponse.message});
            return;
        }
        const infoResponse = await api.category.getCategoryInfo(categoryResponse.id);
        if (api.isError(infoResponse)) {
            notification.error({message: "Failed to fetch category info", description: infoResponse.message});
            return;
        }
        this.setState({...this.state, categoryInfo: infoResponse});
    }

    render () {
        return (
            <ProductContainer
                filterData={this.state.categoryInfo}
                query={{categoryIdentifier: this.props.match.params.identifier}}
            />
        );
    }
}
