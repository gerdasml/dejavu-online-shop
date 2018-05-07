import * as React from "react";

import { Input } from "antd";

export interface ProductNameProps {
      name: string;
      onChange: (s: string) => void;
}

export interface ProductNameState {
      name: string;
}

export class ProductName extends React.Component<ProductNameProps,ProductNameState> {
      state: ProductNameState = { name: this.props.name };
      handleChange (e: React.ChangeEvent<HTMLInputElement>) {
            const value = e.target.value;
            this.setState({...this.state, name: value});
            this.props.onChange(value);
      }
      render () {
            return (
                  <Input
                        addonBefore="Product name:"
                        placeholder="Enter product name..."
                        value={this.state.name}
                        onChange={this.handleChange.bind(this)}/>
            );
      }
}
