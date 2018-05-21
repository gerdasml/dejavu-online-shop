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

import * as CartManager from "../../../utils/cart";

interface ProductRouteProps {
    identifier: string;
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
        const addToCartInfo = await CartManager.addProduct(this.state.product, amount);
        if(api.isError(addToCartInfo)) {
            notification.error({message: "Failed to update cart", description: addToCartInfo.message});
            return;
        }
        message.success("Successfully added product to cart");
    }
    handleProductInfo = async (props: RouteComponentProps<ProductRouteProps>): Promise<void> => {
        const identifier = props.match.params.identifier;
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