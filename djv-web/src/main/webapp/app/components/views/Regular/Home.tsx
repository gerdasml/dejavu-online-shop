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

export class Home extends React.Component< {}, never> {
    render () {
        return (
            <ProductContainer query={{}} />
        );
    }
}
