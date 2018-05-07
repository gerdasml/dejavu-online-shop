import * as React from "react";

import { Icon, Input } from "antd";

export interface ProductDescriptionProps {
    description: string;
    onChange: (s: string) => void;
}

export interface ProductDescriptionState {
    description: string;
}

export class ProductDescription extends React.Component<ProductDescriptionProps,ProductDescriptionState> {
    state: ProductDescriptionState = { description: this.props.description };
    handleChange (e: React.ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        this.setState({...this.state, description: value});
        this.props.onChange(value);
    }
    render () {
        return (
            <Input
                placeholder="Enter product description here"
                value={this.state.description}
                onChange={this.handleChange.bind(this)}
            />
        );
    }
}
