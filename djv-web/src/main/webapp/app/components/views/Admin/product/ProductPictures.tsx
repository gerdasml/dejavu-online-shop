import * as React from "react";

import {Button, Grid, Header, Image } from "semantic-ui-react";
import { ProductPicturesModal } from "./ProductPicturesModal";
import { ImageUpload } from "./ImageUpload";

export class ProductPictures extends React.Component<{}, {}> {
    render () {
        return (
            <Grid>
                <Grid.Row>
                    {/* <ProductPicturesModal /> */}
                    <ImageUpload onUpdate={imgs => console.log(imgs)} />
                </Grid.Row>
            </Grid>
        );
    }
}
