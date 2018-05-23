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

export class Category extends React.Component<RouteComponentProps<CategoryRouteProps>, never> {
    render () {
        return <ProductContainer categoryIdentifier={this.props.match.params.identifier} />;
    }
}
