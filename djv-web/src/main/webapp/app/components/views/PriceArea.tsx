import * as React from "react";

import { Button, Grid, Icon, Input, List } from "semantic-ui-react";
import { Amount } from "../dumb/Cart/Amount";

import "../../../style/product.css";

export const PriceArea = () => (
    <div>
            <List horizontal floated="right">
                <List.Item className="productColumn">
                    <Amount amount={1}/>
                </List.Item>
                <List.Item >
                    <Button className="buyButton">Į krepšelį <Icon name="in cart"/></Button>
                </List.Item>
            </List>
    </div>
);
