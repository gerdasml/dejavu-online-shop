import * as React from "react";

import { Button, Icon, List } from "semantic-ui-react";
import { Amount } from "../../dumb/Cart/Amount";

import "../../../../style/product.css";

export const PriceArea = () => (
            <List horizontal floated="right">
                <List.Item className="productColumn">
                    {/* Implementuojant amount keitimo veikimą reikės atsikomentuoti
                    Amount componentą ↓ ir implmentuoti onAmountChange funkciją*/}
                    {/* <Amount amount={1}/> */}
                </List.Item>
                <List.Item >
                    <Button className="buyButton">Add to cart<Icon name="in cart"/></Button>
                </List.Item>
            </List>
);
