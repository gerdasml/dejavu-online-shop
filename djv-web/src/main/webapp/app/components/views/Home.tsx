import * as React from "react";
import { Header, Image, Card } from "semantic-ui-react";

import {products} from "../../data/products";
import { ProductCard } from "../dumb/Home/ProductCard";

const myImage = require("../../assets/placeholder_350x150.png");

export const Home = () => (
    <div>
        <Header size="large">Home</Header>
        {/* <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} /> */}
        <Card.Group itemsPerRow={5}>
            {products.map(x => <ProductCard product={x} />)}
        </Card.Group>
    </div>
);
