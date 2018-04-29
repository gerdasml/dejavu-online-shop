import * as React from "react";
import { Card, Header } from "semantic-ui-react";

import {products} from "../../../data/products";
import { ProductCard } from "../../dumb/Home/ProductCard";

export const Home = () => (
    <div>
        <Header size="large">Home</Header>
        <Card.Group itemsPerRow={5} doubling>
            {products.map((x, i) => <ProductCard key={i} product={x} />)}
        </Card.Group>
    </div>
);
