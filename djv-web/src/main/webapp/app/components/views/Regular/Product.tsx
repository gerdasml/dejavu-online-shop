import * as React from "react";

import { notification } from "antd";
import { Grid, Header, Label, List, Loader} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";
import * as ProductModel from "../../../model/Product";
import { Carousel } from "../../dumb/Product/Carousel";
import { PropertiesTable } from "../../smart/Product/PropertiesTable";
import { PriceArea } from "./PriceArea";

import "../../../../style/product.css";
import * as api from "../../../api";

interface ProductRouteProps { id: number; }

interface ProductState {
    loading: boolean;
    product?: ProductModel.Product;
}

export class Product extends React.Component<RouteComponentProps<ProductRouteProps>, ProductState> {
    state: ProductState = {
        loading: true
    };
    handleProductInfo = async (props: RouteComponentProps<ProductRouteProps>): Promise<void> => {
        const response = await api.product.getProduct(props.match.params.id);
        if(api.isError(response)) {
            notification.error({message: "Failed to load data", description: response.message});
            this.setState({
                ...this.state, loading: false
            });
            return undefined;
        }
        this.setState({
            ...this.state,
            product: response
        });
    }
    async componentWillReceiveProps (nextProps: RouteComponentProps<ProductRouteProps>) {
        if (await this.handleProductInfo(nextProps) !== undefined) {
            this.setState({...this.state, loading: true});
        }
        this.setState({...this.state, loading: false});
    }

    // required to load data on initial render
    async componentDidMount () {
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
                            <PriceArea />
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
                            {this.state.product.description}
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
