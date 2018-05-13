import * as React from "react";

import { Card, Loader } from "semantic-ui-react";

import * as api from "../../../api";

import { Cart } from "../../../model/Cart";
import { Product } from "../../../model/Product";
import { ProductCard, ProductSituation } from "../Home/ProductCard";

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
        const cartInfo = await api.cart.getCart();
        if(api.isError(cartInfo)) {
            console.log(cartInfo.message);
            this.setState({
                ...this.state,
                error: cartInfo.message,
                isLoading: false,
            });
        } else {
            this.setState({
                ...this.state,
                cart: cartInfo,
                isLoading: false,
            });
        }
    }

    async addProductToCart (addedProduct: Product) {
        // TODO: think about pretty loaders instead of state change before api call

        const oldCart = this.state.cart;

        const newCart = this.state.cart;
        const newOrderItem = {
            amount: 1,
            product: addedProduct,
            total: addedProduct.price,
        };
        newCart.items.push(newOrderItem);
        this.setState({
            ...this.state,
            cart: newCart,
        });

        const addToCartInfo = await api.cart.addToCart({
            amount: 1,
            productId: addedProduct.id,
        });
        if(api.isError(addToCartInfo)) {
            this.setState({
                ...this.state,
                cart: oldCart,
                error: addToCartInfo.message,
            });
        }
    }

    render () {
        return (
                <Card.Group itemsPerRow={5} doubling>
                    {this.props.products.map((x, i) =>
                    this.state.cart === undefined
                    ?
                    <ProductCard key={i} product={x} productSituation={ProductSituation.cartNotLoadedYet}/>
                    :
                    this.state.cart.items.findIndex( item => item.product.id === x.id) !== -1
                    ?
                    <ProductCard key={i} product={x} productSituation={ProductSituation.productInCart}/>
                    :
                    <ProductCard key={i} product={x} productSituation={ProductSituation.productNotInCart}
                        onProductAddToCart={product => this.addProductToCart(product)}
                    />
                    )}
                </Card.Group>
        );
    }
}
