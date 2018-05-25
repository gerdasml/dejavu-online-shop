import * as React from "react";
import { Button, Icon } from "semantic-ui-react";
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
        <Button
            icon
            type="submit"
            positive
            labelPosition="right"
            floated="right"
            onClick={props.onStepComplete}>
            Confirm
            <Icon name="chevron right" />
        </Button>
    </div >
);
