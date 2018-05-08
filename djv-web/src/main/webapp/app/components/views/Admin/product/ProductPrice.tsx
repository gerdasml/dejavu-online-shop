import * as React from "react";

import { Icon, InputNumber } from "antd";

export interface ProductPriceProps {
    price: number;
    onChange: (n: number) => void;
}

export interface ProductPriceState {
    price: number;
}

export class ProductPrice extends React.Component<ProductPriceProps,ProductPriceState> {
    state: ProductPriceState = { price: this.props.price };
    handleChange (value: number) {
        this.setState({...this.state, price: value});
        this.props.onChange(value);
    }
    render () {
        return (
            <InputNumber
                formatter={value => `$ ${value}`}
                value={this.state.price}
                onChange={this.handleChange.bind(this)}
            />
        );
    }
}
