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
    price?: number;
    pictures: string[];
    description: string;
    properties: ProductProperties[];
    category?: number;
    subcategory?: number;
    subsubcategory?: number;
}

export class SingleProduct extends React.Component<{},SingleProductState> {
    state: SingleProductState = {
        description: "",
        name: "",
        pictures: [],
        properties: [{name: "te", value: "st"}],
    };

    render () {
        return (
            <Grid>
                <Grid.Row>
                    <Grid.Column width="eight">
                        <ProductName
                            name={this.state.name}
                            onChange={newName => this.setState({
                                ...this.state, name: newName
                            })}/>
                    </Grid.Column>
                    <Grid.Column width="eight">
                        <ProductPrice
                            price={this.state.price}
                            onChange={newPrice => this.setState({
                                ...this.state, price: newPrice
                            })}/>
                    </Grid.Column>
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column width="eight">
                        <ProductPictures
                            pictures={this.state.pictures}
                            onChange={newPictures => this.setState({
                                ...this.state, pictures: newPictures
                            })}/>
                    </Grid.Column>
                    <Grid.Column width="eight">
                        <ProductDescription
                            description={this.state.description}
                            onChange={newDescription => this.setState({
                                ...this.state, description: newDescription
                            })}/>
                    </Grid.Column>
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column width="eight">
                        <ProductPropertiesTable
                            properties={this.state.properties}
                            onChange={newProperties => this.setState({
                                ...this.state, properties: newProperties
                            })}
                            />
                    </Grid.Column>
                    <Grid.Column width="eight">
                        <ProductDropdown
                            categories={[{id: 1, parentId: 1, identifier: "bla", iconName: "medis", name: "medis"},
                                         {id: 2, parentId: 1, identifier: "blabla", iconName: "medis2", name: "medis2"}]}
                            onChange={newCategory => this.setState({
                                ...this.state, category: newCategory
                            })}/>
                        <ProductDropdown
                            categories={[{id: 1, parentId: 1, identifier: "bla", iconName: "medis", name: "medis"}]}
                            onChange={newCategory => this.setState({
                                ...this.state, subcategory: newCategory
                            })}/>
                        <ProductDropdown
                            categories={[{id: 1, parentId: 1, identifier: "bla", iconName: "medis", name: "medis"}]}
                            onChange={newCategory => this.setState({
                                ...this.state, subsubcategory: newCategory
                            })}/>
                        <div>
                            {JSON.stringify(this.state)}
                        </div>
                    </Grid.Column>
                </Grid.Row>
                <Grid.Row>
                    <Button>Save</Button>
                </Grid.Row>
            </Grid>
        );
    }
}
