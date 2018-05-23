import * as React from "react";

import { Slider } from "antd";
import { Grid, Accordion, Button } from "semantic-ui-react";

import { Category, PropertySummary } from "../../../model/Category";
import { transform } from "../../../utils/product/productFilter";
import { Filter } from "./Filter";

import { ProductProperties } from "../../../model/ProductProperties";

import "../../../../style/filter.css";

interface ProductFilterProps {
    minPrice: number;
    maxPrice: number;
    availableProperties: PropertySummary[];
    onFilterChange: (minPrice: number, maxPrice: number, properties: ProductProperties[]) => void;
}

interface ProductFilterState {
    filterOptions: PropertySummary[];
    isFilterExpanded: boolean;
    minPrice: number;
    maxPrice: number;
}

export class ProductFilter extends React.Component <ProductFilterProps, ProductFilterState> {
    state: ProductFilterState = {
        isFilterExpanded: false,
        filterOptions: [],
        minPrice: this.props.minPrice,
        maxPrice: this.props.maxPrice
    };

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

    render () {
        const min = this.props.minPrice === undefined ? undefined : Math.floor(this.props.minPrice);
        const max = this.props.maxPrice === undefined ? undefined : Math.ceil(this.props.maxPrice);
        return (
            <Accordion className="filter-accordion">
                <Accordion.Title
                    className="filter-title"
                    active={this.state.isFilterExpanded}
                    onClick={this.handleFilterOpenChange.bind(this)}>
                    <Button className="filter-accordion-button">Filter</Button>
                </Accordion.Title>
                <Accordion.Content active={this.state.isFilterExpanded}>
                    <Grid doubling stackable columns={5} id="filter-father" className="accordion-content">
                            { min !== undefined && max !== undefined
                            ?
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
                                    }}
                                />
                            </Grid.Row>
                            : ""
                            }
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
        );
    }
}
