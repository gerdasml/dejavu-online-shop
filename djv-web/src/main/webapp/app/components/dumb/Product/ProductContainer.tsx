import * as React from "react";

import { notification, message } from "antd";
import { Card } from "semantic-ui-react";

import * as api from "../../../api";

import { Cart } from "../../../model/Cart";
import { Product } from "../../../model/Product";
import { ProductCard } from "../Home/ProductCard";

interface ProductContainerProps {
    products: Product[];
}

interface ProductContainerState {
    isLoading: boolean;
    cart?: Cart;
}

export class ProductContainer extends React.Component <ProductContainerProps, {}> {
    state: ProductContainerState = {
        isLoading: true,
    };

    async componentDidMount () {
        this.setState({...this.state, isLoading: true});
        const cartInfo = await api.cart.getCart();
        if(api.isError(cartInfo)) {
            notification.error({message: "Failed to fetch cart", description: cartInfo.message});
            // TODO: save to local storage if not logged in
        } else {
            this.setState({
                ...this.state,
                cart: cartInfo
            });
        }
        this.setState({...this.state, isLoading: false});
    }

    async addProductToCart (addedProduct: Product) {
        const response = await api.cart.addToCart({
            amount: 1,
            productId: addedProduct.id,
        });
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

    render () {
        return (
                <Card.Group itemsPerRow={5} doubling>
                    {this.props.products.map((x, i) =>
                        <ProductCard
                            key={i}
                            product={x}
                            onProductAddToCart={this.addProductToCart.bind(this)}
                        />
                    )}
                </Card.Group>
        );
    }
}
