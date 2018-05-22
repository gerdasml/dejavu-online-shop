import * as React from "react";

import { InputNumber } from "antd";
import { formatPrice } from "../../../../utils/common";

export interface ProductPriceProps {
    price: number;
    onChange: (n: number) => void;
}

export const ProductPrice = (props: ProductPriceProps) => (
    <InputNumber
        formatter={formatPrice}
        value={props.price}
        onChange={props.onChange}
        min={0}
    />
);
