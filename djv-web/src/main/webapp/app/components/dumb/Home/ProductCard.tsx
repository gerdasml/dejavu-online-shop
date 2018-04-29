import * as React from "react";

import {Card, Header, Image} from "semantic-ui-react";
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
        <Card.Content extra>
            TODO: Add to cart...
        </Card.Content>
    </Card>
);
