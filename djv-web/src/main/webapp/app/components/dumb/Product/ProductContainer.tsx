import * as React from "react";

import { message, notification, Slider } from "antd";
import { Card, Icon, Grid, Pagination, Accordion, Button } from "semantic-ui-react";

import * as api from "../../../api";

import { Cart } from "../../../model/Cart";
import { Product } from "../../../model/Product";
import { ProductCard } from "../Home/ProductCard";
import { Category, PropertySummary } from "../../../model/Category";
import { getProperties, ProductFilter, transform, getMin, getMax } from "../../../utils/product/productFilter";
import { Filter } from "./Filter";
import { FilterBuilder, hasProperties, priceInRange } from "../../../utils/product/filter";

import * as CartManager from "../../../utils/cart";

import "../../../../style/filter.css";
import { ProductProperties } from "../../../model/ProductProperties";

import { config } from "../../../config";

interface ProductContainerProps {
    totalProductCount: number;
    products: Product[];
    onPageChange: (pageNumber: number) => void;
    activePage: number;
    cart: Cart;
    onAddToCart: (product: Product) => void;
}

export class ProductContainer extends React.Component <ProductContainerProps, never> {
    render () {
        return (
            <div>
                <Card.Group itemsPerRow={5} doubling>
                    {this.props.products.map((x, i) =>
                        <ProductCard
                        key={i}
                        product={x}
                        onProductAddToCart={this.props.onAddToCart}
                        />
                    )}
                </Card.Group>
                <Pagination
                    floated="left"
                    activePage={this.props.activePage}
                    onPageChange={((e, x) => this.props.onPageChange(x.activePage as number))}
                    ellipsisItem={{ content: <Icon name="ellipsis horizontal" />, icon: true }}
                    firstItem={{ content: <Icon name="angle double left" />, icon: true }}
                    lastItem={{ content: <Icon name="angle double right" />, icon: true }}
                    prevItem={{ content: <Icon name="angle left" />, icon: true }}
                    nextItem={{ content: <Icon name="angle right" />, icon: true }}
                    totalPages={Math.ceil(this.props.totalProductCount / config.productsPerPage)}
                />
            </div>
        );
    }
}
