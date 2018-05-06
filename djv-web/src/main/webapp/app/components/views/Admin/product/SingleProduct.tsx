import * as React from "react";
import { Grid, Header } from "semantic-ui-react";

import { ProductName } from "./ProductName";
import { ProductPictures } from "./ProductPictures";
import { ProductPrice } from "./ProductPrice";
import { ProductPropertiesTable } from "./ProductPropertiesTable";

export const SingleProduct = () => (
    <Grid columns="two">
        <Grid.Column>
            <Grid.Row>
                <ProductName />
            </Grid.Row>
            <Grid.Row>
                <ProductPictures />
            </Grid.Row>
            <Grid.Row>
                <ProductPropertiesTable properties={[{name: "te", value: "st"}]}/>
            </Grid.Row>
        </Grid.Column>
        <Grid.Column>
            <Grid.Row>
                <ProductPrice />
            </Grid.Row>
        </Grid.Column>
    </Grid>
);
