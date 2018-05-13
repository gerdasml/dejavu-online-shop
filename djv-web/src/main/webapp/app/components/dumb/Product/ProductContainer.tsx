import * as React from "react";

import { Card } from "semantic-ui-react";

import { Product } from "../../../model/Product";
import { ProductCard } from "../Home/ProductCard";

interface ProductContainerProps {
    products: Product[];
}

export const ProductContainer = (props: ProductContainerProps) => (
    <Card.Group itemsPerRow={5} doubling>
        {props.products.map((x, i) => <ProductCard key={i} product={x} />)}
    </Card.Group>
);
