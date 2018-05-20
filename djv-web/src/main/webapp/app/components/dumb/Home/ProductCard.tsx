import * as React from "react";

import {Button, Card, Header, Icon, Image, Label} from "semantic-ui-react";
import "../../../../style/header.css";

import { Product } from "../../../model/Product";

import { NavLink } from "react-router-dom";
import { shortenString } from "../../../utils/common";

interface ProductCardProps {
    product: Product;
    onProductAddToCart?: (product: Product) => void;
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
                        <Icon name="plus"/>1
                    </Button.Content>
                </Button>
            </Card.Content>
        </Card>
);
