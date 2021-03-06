import * as React from "react";
import { Grid } from "semantic-ui-react";

import { Button, message, notification, Input } from "antd";
import * as api from "../../../../api";
import { CategoryTree } from "../../../../model/CategoryTree";
import { Product } from "../../../../model/Product";
import { ProductProperties } from "../../../../model/ProductProperties";
import { ProductDescription } from "./ProductDescription";
import { CategoryDropdown } from "./CategoryDropdown";
import { ProductName } from "./ProductName";
import { ProductPictures } from "./ProductPictures";
import { ProductPrice } from "./ProductPrice";
import { ProductPropertiesTable } from "./ProductPropertiesTable";

import "../../../../../style/admin/products.css";

export interface ProductFormProps {
    product?: Product;
    onSubmit?: () => void;
    categories?: CategoryTree[];
}

export interface ProductFormState {
    name: string;
    price?: number;
    minimalPrice?: number;
    pictures: string[];
    description: string;
    properties: ProductProperties[];
    category?: number;
    categories: CategoryTree[];
    skuCode: string;
}

export class ProductForm extends React.Component<ProductFormProps,ProductFormState> {
    state: ProductFormState =
    {
        categories: [],
        description: "",
        name: "",
        pictures: [],
        properties: [],
        skuCode: ""
    };

    componentWillReceiveProps (nextProps: ProductFormProps) {
        if (nextProps.product) {
            // HACK: chain two state changes. This is required to properly clear
            // all the child components' states (e.g. image upload)
            this.setState({
                pictures: [],
                properties: []
            }, () =>
            this.setState(this.buildNewStateFromProps(nextProps)));
        }
    }

    async componentWillMount () {
        const categories = this.props.categories || await this.resolveCategories();
        const newState = this.buildNewStateFromProps(this.props);
        newState.categories = categories;
        this.setState(newState);
    }

     resolveCategories = async () => {
        const categoryResponse = await api.category.getCategoryTree();
        if(api.isError(categoryResponse)) {
            notification.error({message: "Failed to fetch category data", description: categoryResponse.message});
            return [];
        }
        return categoryResponse;
    }

    buildNewStateFromProps = (props: ProductFormProps): ProductFormState => {
        const newState = this.state;
        if (props.product !== undefined) {
            newState.category = props.product.categoryId;
            newState.skuCode = props.product.skuCode;
            newState.description = props.product.description;
            newState.name = props.product.name;
            newState.price = props.product.price;
            newState.properties = props.product.properties;
            newState.minimalPrice = props.product.minimalPrice;
            newState.pictures = [];
            if (props.product.mainImageUrl !== undefined) {
                newState.pictures.push(props.product.mainImageUrl);
            }
            if (props.product.additionalImagesUrls !== undefined) {
                newState.pictures.push(...props.product.additionalImagesUrls);
            }
        }
        return newState;
    }
    isValid () {
        if(this.state.name &&
           this.state.price &&
           this.state.skuCode &&
           this.state.description &&
           this.state.pictures.length > 0  &&
           this.state.category) return true;
        return false;
    }

    async createProduct (product: Product) {
        const response = await api.product.createProduct(product);
        if(api.isError(response)) {
            notification.error({message: "Failed to save data", description: response.message});
            return false;
        }
        message.success("Data was saved successfully");
        this.setState({
            category: undefined,
            description: "",
            name: "",
            pictures: [],
            price: undefined,
            properties: [],
            skuCode: ""
        });
        return true;
    }

    updateProduct = async (id: number, product: Product) => {
        const response = await api.product.updateProduct(id, product);
        if(api.isError(response)) {
            notification.error({message: "Failed to save data", description: response.message});
            return false;
        }
        message.success("Data was saved successfully");
        return true;
    }

    async handleSave () {
        if (this.isValid()) {
            const product: Product = {
                additionalImagesUrls: this.state.pictures.slice(1),
                categoryId: this.state.category,
                description: this.state.description,
                mainImageUrl: this.state.pictures[0],
                name: this.state.name,
                price: this.state.price,
                properties: this.state.properties,
                skuCode: this.state.skuCode,
                minimalPrice: this.state.minimalPrice,
            };
            if (this.props.product === undefined ||
                this.props.product.id === undefined ||
                api.isError(await api.product.getProduct(this.props.product.id))) {
                if (await this.createProduct(product) && this.props.onSubmit !== undefined) {
                    this.props.onSubmit();
                }
            } else {
                if (await this.updateProduct(this.props.product.id, product) && this.props.onSubmit !== undefined) {
                    this.props.onSubmit();
                }
            }
            return;
        }
        notification.warning({message: "Failed to save product", description: "Not all fields are filled"});
    }

    async handleCategoryChange (categoryId: number) {
        this.setState({
            ...this.state, category: categoryId
        });
        const response = await api.category.getCategoryProperties(categoryId);
        if (api.isError(response)) {
            notification.error({message: "Failed to fetch category data", description: response.message});
            return;
        }
        this.setState({
            ...this.state,
            properties: response.map(
                prop => ({
                    name: prop.name,
                    value: "",
                    propertyId: prop.propertyId
                })
            )
        });
    }
    render () {
        return (
            <Grid stackable id="productFormGrid">
                <Grid.Row >
                    <Grid.Column width="eight">
                        <ProductName
                            name={this.state.name}
                            onChange={newName => this.setState({
                                ...this.state, name: newName
                            })}/>
                    </Grid.Column>
                    <Grid.Column width="eight">
                        <ProductPrice
                            title="Product price"
                            price={this.state.price}
                            onChange={newPrice => this.setState({
                                ...this.state, price: newPrice
                            })}/>
                        <ProductPrice
                            title="Minimal price"
                            price={this.state.minimalPrice}
                            onChange={newMinPrice => this.setState({
                                ...this.state, minimalPrice: newMinPrice
                            })}/>
                        <Input className="inputSku"
                            addonBefore="Sku code:"
                            placeholder="Enter sku code..."
                            value={this.state.skuCode}
                            onChange={e => this.setState({
                                ...this.state, skuCode: e.target.value
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
                        <CategoryDropdown
                            selected={this.state.category}
                            categories={this.state.categories}
                            onChange={this.handleCategoryChange.bind(this)}/>
                    </Grid.Column>
                    <Grid.Column width="eight">
                        <ProductPropertiesTable
                            properties={this.state.properties}
                            onChange={newProperties => this.setState({
                                ...this.state, properties: newProperties
                            })}
                        />
                    </Grid.Column>
                </Grid.Row>
                <Grid.Row>
                    <Button className="saveButton"
                        onClick={this.handleSave.bind(this)}
                    >Save</Button>
                </Grid.Row>
            </Grid>
        );
    }
}
