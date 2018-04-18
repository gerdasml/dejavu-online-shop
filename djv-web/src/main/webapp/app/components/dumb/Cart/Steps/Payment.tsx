import * as React from "react";

// This lib has no types yet :(
import * as cards from "react-credit-cards";
import "react-credit-cards/es/styles-compiled.css";

interface PaymentProps {
    onComplete: () => void;
}

interface PaymentState {

}

export class Payment extends React.Component<PaymentProps, PaymentState> {
    render() {
        return (
            <div>
                <cards.default
                    number="4111111111111111"
                    name="Vardenis Pavardenis"
                    expiry="09/18"
                    cvc="123"
                    focused="number"
                />
                <button onClick={props.onComplete}>Next</button>
            </div >
        );
    }
}
