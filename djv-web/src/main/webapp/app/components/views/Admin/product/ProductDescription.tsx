import * as React from "react";

import { Input } from "antd";

export interface ProductDescriptionProps {
    description: string;
    onChange: (s: string) => void;
}

export const ProductDescription = (props: ProductDescriptionProps) => (
    <Input.TextArea
        placeholder="Enter product description here"
        value={props.description}
        onChange={e => props.onChange(e.target.value)}
        rows={5}
    />
);
