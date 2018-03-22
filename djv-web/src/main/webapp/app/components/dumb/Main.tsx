import * as React from "react";
import {Image} from "semantic-ui-react";
import { DrawerMenu } from "./Menu/Drawer";

const myImage = require("../../assets/placeholder_350x150.png");

export const Main = () => (
    <DrawerMenu>
        <p>Content</p>
        <Image src={myImage} />
    </DrawerMenu>
);
