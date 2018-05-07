import * as React from "react";

import {Button, Grid, Header, Image } from "semantic-ui-react";
import { ImageUpload } from "./ImageUpload";
import { ProductPicturesModal } from "./ProductPicturesModal";

export interface ProductPicturesProps {
    pictures: string[];
    onChange: (s: string[]) => void;
}

export interface ProductPicturesState {
    pictures: string[];
}

export class ProductPictures extends React.Component<ProductPicturesProps, ProductPicturesState> {
    state: ProductPicturesState = { pictures: this.props.pictures };
    handleChange (e: React.ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        this.setState({...this.state, pictures: value});
        this.props.onChange(value);
    }
    render () {
        return (
            <Grid>
                <Grid.Row>
                    {/* <ProductPicturesModal /> */}
                    <ImageUpload
                        onUpdate={imgs => this.props.onChange(imgs.map(x => x.url))}
                        onChange={this.handleChange.bind(this)}/>
                </Grid.Row>
            </Grid>
        );
    }
}
