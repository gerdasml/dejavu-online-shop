import * as React from "react";

import { notification, message } from "antd";
import { Grid, Header, Label, List, Loader} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";
import { Cart } from "../../../model/Cart";
import * as ProductModel from "../../../model/Product";
import { Carousel } from "../../dumb/Product/Carousel";
import { Expander} from "../../smart/Product/Expander";
import { PropertiesTable } from "../../smart/Product/PropertiesTable";
import { PriceArea } from "./PriceArea";

import "../../../../style/product.css";
import * as api from "../../../api";

interface ProductRouteProps { id: number; }

interface ProductState {
    amount: number;
    loading: boolean;
    product?: ProductModel.Product;
}

export class Product extends React.Component<RouteComponentProps<ProductRouteProps>, ProductState> {
    state: ProductState = {
        amount: 1,
        loading: true
    };
    async updateCart (amount: number) {
        const addToCartInfo = await api.cart.addToCart({
            amount,
            productId: this.state.product.id
        });
        if(api.isError(addToCartInfo)) {
            notification.error({message: "Failed to update cart", description: addToCartInfo.message});
            return;
        }
        message.success("Successfully added product to cart");
    }
    handleProductInfo = async (props: RouteComponentProps<ProductRouteProps>): Promise<void> => {
        const response = await api.product.getProduct(props.match.params.id);
        if(api.isError(response)) {
            notification.error({message: "Failed to load data", description: response.message});
            return;
        }
        this.setState({
            ...this.state,
            product: response
        });
    }
    async componentWillReceiveProps (nextProps: RouteComponentProps<ProductRouteProps>) {
        this.setState({...this.state, loading: true});
        await this.handleProductInfo(nextProps);
        this.setState({...this.state, loading: false});
    }

    // required to load data on initial render
    async componentDidMount () {
        this.setState({...this.state, loading: true});
        await this.handleProductInfo(this.props);
        this.setState({...this.state, loading: false});
    }
    render () {
        return (
            <div className="product">
            {
                this.state.loading
                ?
                <Loader active={true}/>
                :
                <Grid>
                    <Grid.Row>
                        <Grid.Column width="eight">
                            <List horizontal>
                                <List.Item>
                                    <Header size="large">{this.state.product.name}</Header>
                                </List.Item>
                                <List.Item>
                                    <Label tag>{this.state.product.price}</Label>
                                </List.Item>
                            </List>
                        </Grid.Column>
                        <Grid.Column width="eight">
                            <PriceArea onAmountChange={amount => this.updateCart(amount)}/>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width="eight">
                            <Carousel
                                mainPicture={this.state.product.mainImageUrl}
                                additionalPictures={this.state.product.additionalImagesUrls}/>
                        </Grid.Column>
                        <Grid.Column width="one"/>
                        <Grid.Column width="seven" className="description">
                            <Expander text={this.state.product.description}/>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid className="propertiesTable">
                            <PropertiesTable properties={this.state.product.properties}/>
                        </Grid>
                    </Grid.Row>
const product = {
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec aliquam porta consequat. Nullam suscipit, nisi eu aliquam euismod, dui diam sagittis eros, ut fermentum diam lectus ac risus. Integer ornare arcu nisl, eget condimentum sapien mattis in. Duis hendrerit diam id facilisis rutrum. Vivamus quam lacus, sollicitudin a sagittis ac, feugiat tincidunt tellus. Aenean consectetur, mi vel ultrices bibendum, neque ex volutpat purus, quis posuere diam mauris id libero. Fusce a sem eget nulla porta vulputate. Etiam rutrum hendrerit lorem et tempus. Nullam malesuada dui nunc, quis convallis ex euismod non.",
    mainImageUrl: "///////",
    name: "Telefonas",
    price: 43,
    properties: [
       {
           name: "spalva",
           value: "raudona"
       },
       {
            name: "spalva",
            value: "raudona"
        },
       {
            name: "spalva",
            value: "raudona"
        },
        {
            name: "spalva",
            value: "raudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudonaraudona"
        },
        {
            name: "spalva",
            value: "raudona"
        },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       }
    ]
};
export const Product = (props: RouteComponentProps<ProductRouteProps>) => (
    <div className="product">
        <Grid>
            <Grid.Row>
                <Grid.Column width={8}>
                    <List horizontal>
                        <List.Item>
                            <Header size="large">Product {props.match.params.id}</Header>
                        </List.Item>
                        <List.Item>
                            <Label tag>$10.00</Label>
                        </List.Item>
                    </List>
                </Grid.Column>
                <Grid.Column width={8}>
                    <PriceArea />
                </Grid.Column>
            </Grid.Row>
            <Grid.Row>
                <Grid.Column width={8}>
                    <Carousel />
                </Grid.Column>
                <Grid.Column width={1}/>
                <Grid.Column width={7} className="description">
                    {product.description}
                </Grid.Column>
            </Grid.Row>
            <Grid.Row>
                <Grid className="propertiesTable">
                </Grid>
            }
            </div>
        );
    }
}
