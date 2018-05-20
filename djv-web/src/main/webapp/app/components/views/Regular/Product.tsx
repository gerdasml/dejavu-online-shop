import * as React from "react";

import { message, notification } from "antd";
import { Grid, Header, Label, List, Loader} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";
import * as ProductModel from "../../../model/Product";
import { Carousel } from "../../dumb/Product/Carousel";
import { Expander} from "../../smart/Product/Expander";
import { PropertiesTable } from "../../smart/Product/PropertiesTable";
import { PriceArea } from "./PriceArea";

import "../../../../style/product.css";
import * as api from "../../../api";

interface ProductRouteProps {
    category?: string;
    subcategory?: string;
    subsubcategory?: string;
    product: string;
}

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
        const identifier = this.buildIdentifier(props);
        const response = await api.product.getProductByIdentifier(identifier);
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

    buildIdentifier = (props: RouteComponentProps<ProductRouteProps>): string => {
        const {category, subcategory, subsubcategory, product} = props.match.params;
        let result = "";
        if (category !== undefined) result += category + "/";
        if (subcategory !== undefined) result += subcategory + "/";
        if (subsubcategory !== undefined) result += subsubcategory + "/";
        result += product;
        return result;
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
                </Grid>
            }
            </div>
        );
    }
}