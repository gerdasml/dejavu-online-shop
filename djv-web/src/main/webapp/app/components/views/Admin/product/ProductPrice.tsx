import * as React from "react";

import { InputNumber } from "antd";
import { formatPrice } from "../../../../utils/common";

import "../../../../../style/admin/products.css";

export interface ProductPriceProps {
    price: number;
    title?: string;
    onChange: (n: number) => void;
}

export const ProductPrice = (props: ProductPriceProps) => (
                <div>
                    <span>{props.title}</span>
                    <InputNumber
                        className="inputNumber"
                        formatter={formatPrice}
                        value={props.price}
                        onChange={props.onChange}
                    min={0}
                    />
                </div>
);
