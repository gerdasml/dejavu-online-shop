import * as React from "react";

import { Icon, InputNumber } from "antd";

export interface ProductPriceProps {
    price: number;
    onChange: (n: number) => void;
}

export class ProductPrice extends React.Component<ProductPriceProps,{}> {
    handleChange (value: number) {
        this.props.onChange(value);
    }
    render () {
        return (
            <InputNumber
                formatter={value => `$ ${value}`}
                value={this.props.price}
                onChange={this.handleChange.bind(this)}
            />
        );
    }
}
