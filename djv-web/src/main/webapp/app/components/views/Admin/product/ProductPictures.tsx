import * as React from "react";

import {Button, Grid, Header, Image } from "semantic-ui-react";
import { ImageUpload } from "./ImageUpload";
import { ProductPicturesModal } from "./ProductPicturesModal";

export interface ProductPicturesProps {
    pictures: string[];
    onChange: (s: string[]) => void;
}

export const ProductPictures = (props: ProductPicturesProps) => (
    <Grid>
        <Grid.Row>
            {/* <ProductPicturesModal /> */}
            <ImageUpload
                images={props.pictures}
                onUpdate={imgs => props.onChange(imgs.map(x => x.url))}
            />
        </Grid.Row>
    </Grid>
);
