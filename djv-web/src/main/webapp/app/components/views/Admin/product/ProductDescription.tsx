import * as React from "react";

import { Icon, Input } from "antd";

export interface ProductDescriptionProps {
    description: string;
    onChange: (s: string) => void;
}

export class ProductDescription extends React.Component<ProductDescriptionProps,{}> {
    handleChange (e: React.ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        this.props.onChange(value);
    }
    render () {
        return (
            <Input
                placeholder="Enter product description here"
                value={this.props.description}
                onChange={this.handleChange.bind(this)}
            />
        );
    }
}
