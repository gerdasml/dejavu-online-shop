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
                <div className="label-and-number">
                    <span className="input-title ant-input-group-addon">{props.title}</span>
                    <InputNumber
                        className="inputNumber"
                        formatter={formatPrice}
                        value={props.price}
                        onChange={props.onChange}
                        min={0}
                    />
                </div>
);
