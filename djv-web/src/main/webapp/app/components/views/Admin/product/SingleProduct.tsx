import * as React from "react";
import { Grid, Header } from "semantic-ui-react";

import { Button } from "antd";
import { ProductDescription } from "./ProductDescription";
import { ProductDropdown } from "./ProductDropdown";
import { ProductName } from "./ProductName";
import { ProductPictures } from "./ProductPictures";
import { ProductPrice } from "./ProductPrice";
import { ProductPropertiesTable } from "./ProductPropertiesTable";

export const SingleProduct = () => (
    <Grid>
        <Grid.Row>
            <Grid.Column width="eight">
                <ProductName />
            </Grid.Column>
            <Grid.Column width="eight">
                <ProductPrice />
            </Grid.Column>
        </Grid.Row>
        <Grid.Row>
            <Grid.Column width="eight">
                <ProductPictures />
            </Grid.Column>
            <Grid.Column width="eight">
                <ProductDescription />
            </Grid.Column>
        </Grid.Row>
        <Grid.Row>
            <Grid.Column width="eight">
                <ProductPropertiesTable properties={[{name: "te", value: "st"}]}/>
            </Grid.Column>
            <Grid.Column width="eight">
                <ProductDropdown />
                <ProductDropdown />
                <ProductDropdown />
            </Grid.Column>
        </Grid.Row>
        <Grid.Row>
            <Button>Save</Button>
        </Grid.Row>
    </Grid>
);
