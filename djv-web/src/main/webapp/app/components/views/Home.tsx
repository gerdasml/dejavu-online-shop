import * as React from "react";
import { Header, Image } from "semantic-ui-react";

const myImage = require("../../assets/placeholder_350x150.png");

export const Home = () => (
    <div>
        <Header size="large">Home</Header>
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
        <Image src={myImage} />
    </div>
);
