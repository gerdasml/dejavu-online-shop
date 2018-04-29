import * as React from "react";

import {Button, Grid, Header, Image } from "semantic-ui-react";
import { ProductPicturesModal } from "./ProductPicturesModal";

export class ProductPictures extends React.Component<{}, {}> {
    render () {
        return (
            <Grid>
                <Grid.Row>
                    <Image.Group>
                        <Image src="http://via.placeholder.com/100x100" />
                        <Image src="http://via.placeholder.com/100x100" />
                        <Image src="http://via.placeholder.com/100x100" />
                        <Image src="http://via.placeholder.com/100x100" />
                    </Image.Group>
                </Grid.Row>
                <Grid.Row>
                    <ProductPicturesModal />
                </Grid.Row>
            </Grid>
        );
    }
}
