import * as React from "react";

import { Icon, InputNumber } from "antd";

export interface ProductPriceProps {
    price: number;
    onChange: (n: number) => void;
}

export const ProductPrice = (props: ProductPriceProps) => (
    <InputNumber
        formatter={value => `$ ${value}`}
        value={props.price}
        onChange={props.onChange}
    />
);
