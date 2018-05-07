import * as React from "react";

import { Icon, Input } from "antd";

export interface ProductPriceProps {
    price: number;
    onChange: (n: number) => void;
}

export interface ProductPriceState {
    price: number;
}

export class ProductPrice extends React.Component<ProductPriceProps,ProductPriceState> {
    state: ProductPriceState = { price: this.props.price };
    handleChange (e: React.ChangeEvent<HTMLInputElement>) {
        const value = +(e.target.value);
        this.setState({...this.state, price: value});
        this.props.onChange(value);
    }
    render () {
        return (
            <Input
                placeholder="Enter product price"
                prefix={<Icon type="tag" />}
                value={this.state.price}
                onChange={this.handleChange.bind(this)}
            />
        );
    }
}
