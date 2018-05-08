import * as React from "react";

import { Input } from "antd";

export interface ProductNameProps {
      name: string;
      onChange: (s: string) => void;
}

export const ProductName = (props: ProductNameProps) => (
    <Input
        addonBefore="Product name:"
        placeholder="Enter product name..."
        value={props.name}
        onChange={e => props.onChange(e.target.value)}/>
);
