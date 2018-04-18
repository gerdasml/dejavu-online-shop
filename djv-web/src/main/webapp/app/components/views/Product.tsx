import * as React from "react";

import {Header} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";
import { Carousel } from "../dumb/Product/Carousel";

import "../../../style/product.css";

interface ProductRouteProps { id: number; }
export const Product = (props: RouteComponentProps<ProductRouteProps>) => (
    <div className="product">
        <Header size="large">Product {props.match.params.id}</Header>
        <Carousel />
    </div>
);
