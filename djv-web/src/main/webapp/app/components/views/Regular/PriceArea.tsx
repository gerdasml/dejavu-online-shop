import * as React from "react";

import { Button, Icon, List } from "semantic-ui-react";
import { Amount } from "../../dumb/Cart/Amount";

import "../../../../style/product.css";

export const PriceArea = () => (
            <List horizontal floated="right">
                <List.Item className="productColumn">
                    <Amount amount={1}/>
                </List.Item>
                <List.Item >
                    <Button className="buyButton">Add to cart<Icon name="in cart"/></Button>
                </List.Item>
            </List>
);
