import * as React from "react";
import { Button } from "semantic-ui-react";
import "../../../../../style/cart.css";

import { Cart as CartModel } from "../../../../model/Cart";
import { OrderTable } from "../../Order/OrderTable";

interface ConfirmationProps {
    cart: CartModel;
    onStepComplete: () => void;
}

export const Confirmation = (props: ConfirmationProps) => (
    <div>
        <OrderTable data={props.cart}/>
        <Button id="confirmButton" floated="right" positive onClick={props.onStepComplete}>Confirm</Button>
    </div >
);
