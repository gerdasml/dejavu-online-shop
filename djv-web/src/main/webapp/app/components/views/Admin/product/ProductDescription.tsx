import * as React from "react";

import { Icon, Input } from "antd";

export interface ProductDescriptionProps {
    description: string;
    onChange: (s: string) => void;
}

export const ProductDescription = (props: ProductDescriptionProps) => (
    <Input
        placeholder="Enter product description here"
        value={props.description}
        onChange={e => props.onChange(e.target.value)}
    />
);
