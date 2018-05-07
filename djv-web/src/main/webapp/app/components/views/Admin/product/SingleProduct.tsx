import * as React from "react";
import { Grid, Header } from "semantic-ui-react";

import { Button } from "antd";
import { ProductProperties } from "../../../../model/ProductProperties";
import { ProductDescription } from "./ProductDescription";
import { ProductDropdown } from "./ProductDropdown";
import { ProductName } from "./ProductName";
import { ProductPictures } from "./ProductPictures";
import { ProductPrice } from "./ProductPrice";
import { ProductPropertiesTable } from "./ProductPropertiesTable";

export interface SingleProductState {
    name: string;
    price: number;
    pictures: string[];
    description: string;
    properties: ProductProperties[];
    category: number;
    subcategory: number;
    subsubcategory: number;
}

export class SingleProduct extends React.Component<{},SingleProductState> {
    render () {
        return (
            <Grid>
                <Grid.Row>
                    <Grid.Column width="eight">
                        <ProductName
                            onChange={newName => this.setState({
                                ...this.state, name: newName
                            })}/>
                    </Grid.Column>
                    <Grid.Column width="eight">
                        <ProductPrice
                            onChange={newPrice => this.setState({
                                ...this.state, price: newPrice
                            })}/>
                    </Grid.Column>
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column width="eight">
                        <ProductPictures
                            onChange={newPictures => this.setState({
                                ...this.state, pictures: newPictures
                            })}/>
                    </Grid.Column>
                    <Grid.Column width="eight">
                        <ProductDescription
                            onChange={newDescription => this.setState({
                                ...this.state, description: newDescription
                            })}/>
                    </Grid.Column>
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column width="eight">
                        <ProductPropertiesTable
                            properties={[{name: "te", value: "st"}]}
                            onChange={newProperties => this.setState({
                                ...this.state, properties: newProperties
                            })}/>
                    </Grid.Column>
                    <Grid.Column width="eight">
                        <ProductDropdown
                            onChange={newCategory => this.setState({
                                ...this.state, category: newCategory
                            })}/>
                        <ProductDropdown
                            onChange={newSubcategory => this.setState({
                                ...this.state, subcategory: newSubcategory
                            })}/>
                        <ProductDropdown
                            onChange={newSubsubcategory => this.setState({
                                ...this.state, subsubcategory: newSubsubcategory
                            })}/>
                    </Grid.Column>
                </Grid.Row>
                <Grid.Row>
                    <Button>Save</Button>
                </Grid.Row>
            </Grid>
        );
    }
}
