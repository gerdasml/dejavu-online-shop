import * as React from "react";

import {Card, Image} from "semantic-ui-react";
import "../../../../style/header.css";

import * as api from "../../../api";

import { IProduct } from "../../../model/Product";

interface IProductCardProps { product: IProduct; }

export const ProductCard = (props: IProductCardProps) => (
    <Card link>
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
            {props.product.price}€
        </Card.Content>
    </Card>
);
