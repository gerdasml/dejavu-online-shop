import * as React from "react";

import { Button, Card, Grid, Header, Icon, Image } from "semantic-ui-react";

import { Product } from "../../../model/Product";
import { NavLink } from "react-router-dom";
import { shortenString, formatPrice } from "../../../utils/common";

import { isNullOrUndefined } from "util";

import "../../../../style/productCard.css";

interface ProductCardProps {
    product: Product;
    onProductAddToCart?: (product: Product) => void;
}

export const ProductCard = (props: ProductCardProps) => (
    <Card link
       as={NavLink}
        to={`/product/${props.product.identifier}`}
    >
        <Header
            className="card-header"
            attached >
            <h3>{props.product.name}</h3>
        </Header>
        {isNullOrUndefined(props.product.discount)
        ?
            <Image
                src={props.product.mainImageUrl}/>
        :
        props.product.discount.type === "PERCENTAGE"
        ?
            <Image
                src={props.product.mainImageUrl}
                label={{
                className: "discount-ribbon",
                as: "a",
                ribbon: "right",
                size: "large",
                content: "- "+props.product.discount.value+"%"
                }} />
        :
        <Image
            src={props.product.mainImageUrl}
            label={{
            className: "discount-ribbon",
            as: "a",
            ribbon: "right",
            size: "large",
            content: "- " + props.product.discount.value+"€"
            }} />

        }
        <Card.Content>
            <Card.Description>
                {shortenString(props.product.description)}
            </Card.Description>
        </Card.Content>
        <Card.Content 
            extra className="card-price-cart">
            <Grid columns={2}>
                <Grid.Column>
                    {isNullOrUndefined(props.product.discount)
                    ?
                        <Header as="h3" className="priceHeader">{props.product.price}€
                        <Header.Subheader style={{visibility: "hidden"}} as="del" content="."/>
                        </Header>
                        :
                        <Header as="h3" className="priceHeader">{props.product.discount.finalPrice}€
                            <Header.Subheader as="del" content={props.product.price + "€"} />
                        </Header>
                    }
                </Grid.Column>
                <Grid.Column>
                    <Button
                        className="add-to-cart"
                        floated="right"
                        positive
                        animated="fade"
                        onClick={e => {
                            e.preventDefault();
                            props.onProductAddToCart(props.product);
                        }}
                    >
                        <Button.Content visible className="button-text">
                            <Icon name="shop" />
                        </Button.Content>
                        <Button.Content hidden className="button-text">
                            <Icon name="plus" />1
                        </Button.Content>
                    </Button>
                </Grid.Column>
            </Grid>
        </Card.Content>
    </Card>
);
