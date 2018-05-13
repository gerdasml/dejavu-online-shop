import * as React from "react";

import {Button, Card, Header, Icon, Image, Label, Loader} from "semantic-ui-react";
import "../../../../style/header.css";

import { Product } from "../../../model/Product";

import { NavLink } from "react-router-dom";
import { shortenString } from "../../../utils/common";

interface ProductCardProps {
    product: Product;
    onProductAddToCart?: (product: Product) => void;
    productSituation: ProductSituation;
}

export enum ProductSituation {
    cartNotLoadedYet,
    productInCart,
    productNotInCart
}

export const ProductCard = (props: ProductCardProps) => (
        <Card>
            <NavLink to={`/product/${props.product.id}`}>
                <Header attached>{props.product.price}€</Header>
                <Image src={props.product.mainImageUrl} />
                <Card.Content>
                    <Card.Header>
                        {props.product.name}
                    </Card.Header>
                    <Card.Description>
                        {shortenString(props.product.description)}
                    </Card.Description>
                </Card.Content>
            </NavLink>
            <Card.Content extra textAlign="center">
                { props.productSituation === ProductSituation.cartNotLoadedYet
                ?
                <Loader active inline="centered" />
                :
                props.productSituation === ProductSituation.productInCart
                ?
                <Label>Item is already in cart!</Label>
                :
                <div>
                    <Label pointing="right">Add to cart: </Label>
                    <Button
                        positive
                        animated="fade"
                        onClick={() => props.onProductAddToCart(props.product)}
                    >
                        <Button.Content visible>
                            <Icon name="shop"/>
                        </Button.Content>
                        <Button.Content hidden>
                            <Icon name="plus"/>
                        </Button.Content>
                    </Button>
                </div>
                }
            </Card.Content>
        </Card>
);
