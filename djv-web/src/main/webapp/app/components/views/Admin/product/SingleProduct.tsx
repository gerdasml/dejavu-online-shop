import * as React from "react";
import { Grid, Header } from "semantic-ui-react";

import { Button, notification } from "antd";
import * as api from "../../../../api";
import { CategoryTree } from "../../../../model/CategoryTree";
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
    categories: CategoryTree[];
}

export class SingleProduct extends React.Component<{},SingleProductState> {
    state: SingleProductState = {
        categories: [],
        description: "",
        name: "",
        pictures: [],
        properties: [{name: "te", value: "st"}]
    };
    async componentWillMount () {
        const categories = await api.category.getCategoryTree();
        if(api.isError(categories)) {
            notification.error({message: "Failed to fetch category data", description: categories.message});
            return;
        }
        this.setState({...this.state, categories});
    }
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
                            categories={this.state.categories}
                            onChange={newCategory => this.setState({
                                ...this.state, category: newCategory
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
