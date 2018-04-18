import * as React from "react";

import { Button, Icon, Table } from "semantic-ui-react";

import { IPurchase } from "../../model/Purchase";

import "../../../style/cart.css";
import { ShoppingCart } from "../smart/Cart/ShoppingCart";

import * as Step from "../dumb/Cart/Step";

const purchases: IPurchase[] = [{
  amount: 2,
  item: {name: "something", imageUrl:"", description:"", price: 5},
  total: 100,
  unitPrice: 50
}];

export const Cart = (props: {}) => (
    <ShoppingCart>
        <Step.Cart purchases={purchases} />
    </ShoppingCart>
);
