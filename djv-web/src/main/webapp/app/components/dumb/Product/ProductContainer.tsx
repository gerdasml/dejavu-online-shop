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
}

interface ProductContainerState {
    isLoading: boolean;
    cart?: Cart;
}

export class ProductContainer extends React.Component <ProductContainerProps, ProductContainerState> {
    state: ProductContainerState = {
        isLoading: true
    };

    componentWillReceiveProps (props: ProductContainerProps) {
        this.setState({
            isLoading: true
        });
    }

    async componentDidMount () {
        this.setState({...this.state, isLoading: true});
        const cartInfo = await CartManager.getCart();
        if(api.isError(cartInfo)) {
            notification.error({message: "Failed to fetch cart", description: cartInfo.message});
        } else {
            this.setState({
                ...this.state,
                cart: cartInfo
            });
        }
        this.setState({...this.state, isLoading: false});
    }

    async addProductToCart (addedProduct: Product) {
        const response = await CartManager.addProduct(addedProduct, 1);
        if(api.isError(response)) {
            notification.error({message: "Failed to add product to cart", description: response.message});
        } else {
            this.setState({
                ...this.state,
                cart: response
            });
            message.success("Successfully added product to cart");
        }
    }

    handlePaginationChange = (e: any, x: any) => this.props.onPageChange(x.activePage);

    render () {
        return (
            <div>
                <Card.Group itemsPerRow={5} doubling>
                    {this.props.products.map((x, i) =>
                        <ProductCard
                        key={i}
                        product={x}
                        onProductAddToCart={this.addProductToCart.bind(this)}
                        />
                    )}
                </Card.Group>
                <Pagination
                    floated="left"
                    activePage={this.props.activePage}
                    onPageChange={this.handlePaginationChange}
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
