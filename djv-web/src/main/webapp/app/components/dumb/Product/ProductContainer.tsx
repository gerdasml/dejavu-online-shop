import * as React from "react";

import { message, notification, Slider } from "antd";
import { Card, Icon, Grid, Pagination, Accordion, Button } from "semantic-ui-react";

import * as api from "../../../api";

import { Cart } from "../../../model/Cart";
import { Product } from "../../../model/Product";
import { ProductCard } from "../Home/ProductCard";
import { Category, PropertySummary } from "../../../model/Category";
import { getProperties, ProductFilter, transform, getMin, getMax } from "../../../utils/product/productFilter";
import { Filter } from "./Filter";
import { FilterBuilder, hasProperties, priceInRange } from "../../../utils/product/filter";

import * as CartManager from "../../../utils/cart";

import "../../../../style/filter.css";
import { ProductProperties } from "../../../model/ProductProperties";

interface ProductContainerProps {
    totalProductCount: number;
    minPrice?: number;
    maxPrice?: number;
    availableProperties?: PropertySummary[];
    products: Product[];
    category?: Category;
    onPageChange: (pageNumber: number) => void;
    activePage: number;
    onFilterChange: (minPrice: number, maxPrice: number, properties: ProductProperties[]) => void;
}

interface ProductContainerState {
    isLoading: boolean;
    cart?: Cart;
    filterOptions: PropertySummary[];
    isFilterExpanded: boolean;
    minPrice: number;
    maxPrice: number;
}

const PRODUCTS_PER_PAGE = 20;

export class ProductContainer extends React.Component <ProductContainerProps, ProductContainerState> {
    state: ProductContainerState = {
        isFilterExpanded: false,
        isLoading: true,
        filterOptions: [],
        minPrice: this.props.minPrice,
        maxPrice: this.props.maxPrice
    };

    componentWillReceiveProps (props: ProductContainerProps) {
        this.setState({
            isFilterExpanded: false,
            isLoading: true,
            filterOptions: [],
            minPrice: props.minPrice,
            maxPrice: props.maxPrice
        });
    }

    async componentDidMount () {
        this.setState({...this.state, isLoading: true});
        const cartInfo = await CartManager.getCart();
        if(api.isError(cartInfo)) {
            notification.error({message: "Failed to fetch cart", description: cartInfo.message});
        } else {
            this.setState({
                ...this.state,
                cart: cartInfo
            });
        }
        this.setState({...this.state, isLoading: false});
    }

    async addProductToCart (addedProduct: Product) {
        const response = await CartManager.addProduct(addedProduct, 1);
        if(api.isError(response)) {
            notification.error({message: "Failed to add product to cart", description: response.message});
        } else {
            this.setState({
                ...this.state,
                cart: response
            });
            message.success("Successfully added product to cart");
        }
    }

    filterProducts () {
        const props = transform(this.state.filterOptions);
        this.props.onFilterChange(this.state.minPrice, this.state.maxPrice, props);
    }

    handleFilterOpenChange () {
        if(this.state.isFilterExpanded) {
            this.setState({...this.state, isFilterExpanded: false});
        } else {
            this.setState({...this.state, isFilterExpanded: true});
        }
    }

    handlePaginationChange = (e: any, x: any) => this.props.onPageChange(x.activePage);

    render () {
        const min = Math.floor(this.props.minPrice);
        const max = Math.ceil(this.props.maxPrice);
        return (
                <div>
                    {  this.props.category !== undefined
                    && this.props.products.length !== 0
                    && this.props.availableProperties !== undefined
                    ?
                    <Accordion className="filter-accordion">
                        <Accordion.Title
                            className="filter-title"
                            active={this.state.isFilterExpanded}
                            onClick={this.handleFilterOpenChange.bind(this)}>
                            <Button className="filter-accordion-button">Filter</Button>
                        </Accordion.Title>
                        <Accordion.Content active={this.state.isFilterExpanded}>
                            <Grid doubling stackable columns={5} id="filter-father" className="accordion-content">
                                <Grid.Row centered>
                                    <Slider
                                        className="filter-slider"
                                        range
                                        min={min}
                                        max={max}
                                        defaultValue={[min, max]}
                                        marks={{
                                            [min]: min + "Eur",
                                            [max]: max + "Eur"
                                        }}
                                        onAfterChange={(e: [number, number]) => {
                                            this.setState({
                                                ...this.state,
                                                minPrice: e[0],
                                                maxPrice: e[1]
                                            }, ()=> this.filterProducts());
                                        }}/>
                                </Grid.Row>
                                {this.props.availableProperties.map(x =>
                                    <Grid.Column className="filter">
                                        <Filter
                                            properties={x}
                                            onSelectChange={y => {
                                                const newState = this.state;
                                                newState.filterOptions =
                                                    [...newState.filterOptions.filter(
                                                        opt => opt.propertyId !== x.propertyId
                                                    ),
                                                    {
                                                        propertyId: x.propertyId,
                                                        propertyName: x.propertyName,
                                                        values: y
                                                    }];
                                                this.setState(newState, () => this.filterProducts());
                                            }}/>
                                    </Grid.Column>)}
                            </Grid>
                        </Accordion.Content>
                    </Accordion>
                    : ""
                    }
                    <Card.Group itemsPerRow={5} doubling>
                        {this.props.products.map((x, i) =>
                            <ProductCard
                            key={i}
                            product={x}
                            onProductAddToCart={this.addProductToCart.bind(this)}
                            />
                        )}
                    </Card.Group>
                    <Pagination
                        floated="left"
                        activePage={this.props.activePage}
                        onPageChange={this.handlePaginationChange}
                        ellipsisItem={{ content: <Icon name="ellipsis horizontal" />, icon: true }}
                        firstItem={{ content: <Icon name="angle double left" />, icon: true }}
                        lastItem={{ content: <Icon name="angle double right" />, icon: true }}
                        prevItem={{ content: <Icon name="angle left" />, icon: true }}
                        nextItem={{ content: <Icon name="angle right" />, icon: true }}
                        totalPages={Math.ceil(this.props.totalProductCount / PRODUCTS_PER_PAGE)}
                    />
                </div>
        );
    }
}
