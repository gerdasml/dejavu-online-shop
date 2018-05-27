import * as React from "react";

import { InputNumber } from "antd";
import { formatPrice } from "../../../../utils/common";

import "../../../../../style/admin/products.css";

export interface ProductPriceProps {
    price: number;
    onChange: (n: number) => void;
}

export const ProductPrice = (props: ProductPriceProps) => (
    <InputNumber
        className="inputNumber"
        formatter={formatPrice}
        value={props.price}
        onChange={props.onChange}
        min={0}
    />
);
