import * as React from "react";

import {Card, Image, Header} from "semantic-ui-react";
import "../../../../style/header.css";

import * as api from "../../../api";

import { IProduct } from "../../../model/Product";

interface IProductCardProps { product: IProduct; }

export const ProductCard = (props: IProductCardProps) => (
    <Card link>
        <Header attached>{props.product.price}€</Header>
        <Image src={props.product.imageUrl} />
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
