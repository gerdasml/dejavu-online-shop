import * as React from "react";

import { CartStepHeader, CartStep } from "../../dumb/Cart/CartStepHeader";

interface ShoppingCartState {
    currentStep: CartStep;
}

export class ShoppingCart extends React.Component <{}, ShoppingCartState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            currentStep: CartStep.CART
        };
    }

    nextStep = () => {
        const step = this.state.currentStep;
        if(step === CartStep.PAYMENT) {
            console.log("Finished");
            // TODO: finish this somehow
            return;
        }
        this.setState({currentStep: this.state.currentStep+1});
    }

    render () {
        const childrenCount = React.Children.count(this.props.children);
        return (
            <div>
                <CartStepHeader active={this.state.currentStep} />
                {
                    this.state.currentStep >= childrenCount
                        ? "Finished"
                        : React.Children.toArray(this.props.children)[this.state.currentStep]
                }
            </div>
        );
    }
}
