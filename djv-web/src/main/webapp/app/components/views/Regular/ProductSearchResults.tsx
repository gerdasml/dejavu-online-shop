import * as React from "react";

import { notification, message } from "antd";

import { Header, Loader } from "semantic-ui-react";

import * as api from "../../../api";

import { Product } from "../../../model/Product";
import { ProductList } from "../../dumb/Product/ProductList";

import { config } from "../../../config";
import { Cart } from "../../../model/Cart";

import * as CartManager from "../../../utils/cart";
import { ProductContainer } from "../../smart/Product/ProductContainer";
import { RouteComponentProps } from "react-router";

import { parse } from "query-string";
import { NotFound } from "../NotFound";

export class ProductSearchResults extends React.Component< RouteComponentProps<{}>, never> {
    render () {
        const query = parse(this.props.location.search);
        if (query[config.searchQueryParamName] === undefined) {
            return <NotFound />;
        }
        return (
            <ProductContainer
                query={{name: query[config.searchQueryParamName]}}
                noResultsMessage={<h2>Your query produced no results</h2>} // TODO: pretty message
            />
        );
    }
}
