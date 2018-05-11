import * as React from "react";
import { Button, Input } from "semantic-ui-react";

import "../../../../style/cart.css";

export interface AmountProps {
    amount: number;
    onAmountChange: (a: number) => void;
}

export const Amount = (props: AmountProps) => (
    <Input type="text" action className="mainInput">
        <Button
            id="amountButtonMinus"
            icon="minus"
            disabled={props.amount===0}
            onClick={() => props.onAmountChange(props.amount-1)}>
        </Button>
        <input
            id="amountInput"
            type="text"
            value={ props.amount}
            disabled>
        </input>
        <Button
            id="amountButtonPlus"
            icon="plus"
            onClick={() => props.onAmountChange(props.amount+1)}>
        </Button>
    </Input>
);
