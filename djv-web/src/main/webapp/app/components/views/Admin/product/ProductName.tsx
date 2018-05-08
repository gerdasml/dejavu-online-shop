import * as React from "react";

import { Input } from "antd";

export interface ProductNameProps {
      name: string;
      onChange: (s: string) => void;
}

export class ProductName extends React.Component<ProductNameProps,never> {
      handleChange (e: React.ChangeEvent<HTMLInputElement>) {
            const value = e.target.value;
            this.props.onChange(value);
      }
      render () {
            return (
                  <Input
                        addonBefore="Product name:"
                        placeholder="Enter product name..."
                        value={this.props.name}
                        onChange={this.handleChange.bind(this)}/>
            );
      }
}
