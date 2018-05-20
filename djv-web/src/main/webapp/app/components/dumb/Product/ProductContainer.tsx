import * as React from "react";

import { message, notification, Slider } from "antd";
import { Card, Icon, Grid, Pagination, Accordion, Button } from "semantic-ui-react";

import * as api from "../../../api";

import { Cart } from "../../../model/Cart";
import { Product } from "../../../model/Product";
import { ProductCard } from "../Home/ProductCard";
import { Category } from "../../../model/Category";
import { getProperties, ProductFilter, transform, getMin, getMax } from "../../../utils/product/productFilter";
import { Filter } from "./Filter";
import { FilterBuilder, hasProperties, priceInRange } from "../../../utils/product/filter";

import "../../../../style/filter.css";

interface ProductContainerProps {
    products: Product[];
    category?: Category;
}

interface ProductContainerState {
    isLoading: boolean;
    cart?: Cart;
    activePage: number;
    properties?: ProductFilter[];
    filterOptions: Map<string, string[]>;
    filteredProducts: Product[];
    activeIndex: boolean;
    minPrice: number;
    maxPrice: number;
}

const PRODUCTS_PER_PAGE = 20;

export class ProductContainer extends React.Component <ProductContainerProps, {}> {
    state: ProductContainerState = {
        activeIndex: false,
        activePage: 1,
        isLoading: true,
        filterOptions: new Map<string, string[]>(),
        filteredProducts: this.props.products,
        minPrice: getMin(this.props.products),
        maxPrice: getMax(this.props.products)
    };

    async componentDidMount () {
        this.setState({...this.state, isLoading: true});
        const cartInfo = await api.cart.getCart();
        if(api.isError(cartInfo)) {
            notification.error({message: "Failed to fetch cart", description: cartInfo.message});
            // TODO: save to local storage if not logged in
        } else {
            this.setState({
                ...this.state,
                cart: cartInfo
            });
        }
        this.setState({...this.state, isLoading: false});
    }

    async addProductToCart (addedProduct: Product) {
        const response = await api.cart.addToCart({
            amount: 1,
            productId: addedProduct.id,
        });
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

    mapProducts () {
        const start = (this.state.activePage-1)*PRODUCTS_PER_PAGE;
        const end = start+PRODUCTS_PER_PAGE;
        return this.state.filteredProducts.slice(start,end).map( (x, i) =>
            <ProductCard
            key={i}
            product={x}
            onProductAddToCart={this.addProductToCart.bind(this)}
            />
        );
    }

    filterProducts () {
        const newState = this.state;
        const prop = transform(newState.filterOptions);
        const result = new FilterBuilder()
                    .add(hasProperties(prop))
                    .add(priceInRange(this.state.minPrice, this.state.maxPrice))
                    .apply(this.props.products);
        newState.filteredProducts = result;
        newState.activePage = 1;
        this.setState(newState);
    }

    handleFilterOpenChange () {
        if(this.state.activeIndex) {
            this.setState({...this.state, activeIndex: false});
        } else {
            this.setState({...this.state, activeIndex: true});
        }
    }

    handlePaginationChange = (e: any, x: any) => this.setState({ ...this.state, activePage: x.activePage });

    render () {
        const properties = getProperties(this.props.category, this.props.products);
        const min = Math.floor(getMin(this.props.products));
        const max = Math.ceil(getMax(this.props.products));
        return (
                <div>
                    <Accordion className="filter-accordion">
                        <Accordion.Title
                            className="filter-title"
                            active={this.state.activeIndex}
                            onClick={this.handleFilterOpenChange.bind(this)}>
                            <Button className="filter-accordion-button">Filter</Button>
                        </Accordion.Title>
                        <Accordion.Content active={this.state.activeIndex}>
                            <Grid doubling stackable columns={5} className="accordion-content">
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
                                {properties.map(x =>
                                    <Grid.Column className="filter">
                                        <Filter
                                            properties={x}
                                            onSelectChange={y => {
                                                const newState = this.state;
                                                newState.filterOptions.set(x.property, y);
                                                this.setState(newState, ()=> this.filterProducts());
                                            }}/>
                                    </Grid.Column>)}
                            </Grid>
                        </Accordion.Content>
                    </Accordion>
                    <Card.Group itemsPerRow={5} doubling>
                        {this.mapProducts()}
                    </Card.Group>
                    <Pagination
                        floated="left"
                        activePage={this.state.activePage}
                        onPageChange={this.handlePaginationChange}
                        ellipsisItem={{ content: <Icon name="ellipsis horizontal" />, icon: true }}
                        firstItem={{ content: <Icon name="angle double left" />, icon: true }}
                        lastItem={{ content: <Icon name="angle double right" />, icon: true }}
                        prevItem={{ content: <Icon name="angle left" />, icon: true }}
                        nextItem={{ content: <Icon name="angle right" />, icon: true }}
                        totalPages={Math.ceil(this.props.products.length/PRODUCTS_PER_PAGE)}
                    />
                </div>
        );
    }
}
