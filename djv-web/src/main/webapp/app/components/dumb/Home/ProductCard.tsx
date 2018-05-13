import * as React from "react";

import {Button, Card, Header, Icon, Image, Label} from "semantic-ui-react";
import "../../../../style/header.css";

import { Product } from "../../../model/Product";

interface ProductCardProps { product: Product; }

export const ProductCard = (props: ProductCardProps) => (
    <Card link>
        <Header attached>{props.product.price}€</Header>
        <Image src={props.product.mainImageUrl} />
        <Card.Content>
            <Card.Header>
                {props.product.name}
            </Card.Header>
            <Card.Description>
                {props.product.description}
            </Card.Description>
        </Card.Content>
        <Card.Content extra textAlign="center">
            <Label pointing="right">Add to cart: </Label>
            <Button positive animated="fade">
                <Button.Content visible>
                    <Icon name="shop"/>
                </Button.Content>
                <Button.Content hidden>
                    <Icon name="plus"/>
                </Button.Content>
            </Button>
        </Card.Content>
    </Card>
);
