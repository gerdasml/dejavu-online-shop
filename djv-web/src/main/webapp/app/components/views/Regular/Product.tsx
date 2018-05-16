import * as React from "react";

import { Grid, Header, Label, List} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";
import { Carousel } from "../../dumb/Product/Carousel";
import { PropertiesTable } from "../../smart/Product/PropertiesTable";
import { PriceArea } from "./PriceArea";

import "../../../../style/product.css";

interface ProductRouteProps { id: number; }
const product = {
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec aliquam porta consequat. Nullam suscipit, nisi eu aliquam euismod, dui diam sagittis eros, ut fermentum diam lectus ac risus. Integer ornare arcu nisl, eget condimentum sapien mattis in. Duis hendrerit diam id facilisis rutrum. Vivamus quam lacus, sollicitudin a sagittis ac, feugiat tincidunt tellus. Aenean consectetur, mi vel ultrices bibendum, neque ex volutpat purus, quis posuere diam mauris id libero. Fusce a sem eget nulla porta vulputate. Etiam rutrum hendrerit lorem et tempus. Nullam malesuada dui nunc, quis convallis ex euismod non.",
    mainImageUrl: "///////",
    name: "Telefonas",
    price: 43,
    properties: [
       {
           name: "spalva",
           value: "raudona"
       },
       {
            name: "spalva",
            value: "raudona"
        },
       {
            name: "spalva",
            value: "raudona"
        },
        {
            name: "spalva",
            value: "raudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudona"
        },
        {
            name: "spalva",
            value: "raudona"
        },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       }
    ]
};
export const Product = (props: RouteComponentProps<ProductRouteProps>) => (
    <div className="product">
        <Grid>
            <Grid.Row>
                <Grid.Column width={8}>
                    <List horizontal>
                        <List.Item>
                            <Header size="large">Product {props.match.params.id}</Header>
                        </List.Item>
                        <List.Item>
                            <Label tag>$10.00</Label>
                        </List.Item>
                    </List>
                </Grid.Column>
                <Grid.Column width={8}>
                    <PriceArea />
                </Grid.Column>
            </Grid.Row>
            <Grid.Row>
                <Grid.Column width={8}>
                    <Carousel />
                </Grid.Column>
                <Grid.Column width={1}/>
                <Grid.Column width={7} className="description">
                    {product.description}
                </Grid.Column>
            </Grid.Row>
            <Grid.Row>
                <Grid className="propertiesTable">
                    <PropertiesTable product={product}/>
                </Grid>
            </Grid.Row>
        </Grid>
    </div>
);
