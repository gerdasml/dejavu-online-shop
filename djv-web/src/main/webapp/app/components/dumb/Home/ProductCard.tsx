import * as React from "react";

import { Button, Card, Grid, Header, Icon, Image, Label } from "semantic-ui-react";

import { Product } from "../../../model/Product";
import { NavLink } from "react-router-dom";
import { shortenString } from "../../../utils/common";
import { isNullOrUndefined } from "util";

import "../../../../style/productCard.css";

interface ProductCardProps {
    product: Product;
    onProductAddToCart?: (product: Product) => void;
}

export const ProductCard = (props: ProductCardProps) => (
    <Card as={NavLink} to={`/product/${props.product.identifier}`}>
        <Header as="h3" attached >{props.product.name}</Header>
        {isNullOrUndefined(props.product.discount)
        ?
            <Image src={props.product.mainImageUrl} />
        :
        props.product.discount.type === "PERCENTAGE"
        ?
            <Image src={props.product.mainImageUrl}
                label={{
                as: "a",
                color: "orange",
                ribbon: "right",
                size: "large",
                icon: "percent",
                content: props.product.discount.value
                }} />
        :
        <Image src={props.product.mainImageUrl}
            label={{
            as: "a",
            color: "orange",
            ribbon: "right",
            size: "large",
            icon: "euro",
            content: "- " + props.product.discount.value
            }} />

        }
        <Card.Content>
            <Card.Description>
                {shortenString(props.product.description)}
            </Card.Description>
        </Card.Content>
        <Card.Content extra>
            <Grid columns={2}>
                <Grid.Column>
                    {isNullOrUndefined(props.product.discount)
                    ?
                        <Header as="h3" id="priceHeader">{props.product.price}€</Header>
                        :
                        <Header as="h3">{props.product.discount.finalPrice}€
                            <Header.Subheader as="del" content={props.product.price + "€"} />
                        </Header>
                    }
                </Grid.Column>
                <Grid.Column>
                    <Button
                        floated="right"
                        positive
                        animated="fade"
                        onClick={() => props.onProductAddToCart(props.product)}
                    >
                        <Button.Content visible>
                            <Icon name="shop" />
                        </Button.Content>
                        <Button.Content hidden>
                            <Icon name="plus" />
                        </Button.Content>
                    </Button>
                </Grid.Column>
            </Grid>
        </Card.Content>
    </Card>
);
