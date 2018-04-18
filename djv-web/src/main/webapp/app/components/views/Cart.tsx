import * as React from "react";


import { IPurchase } from "../../model/Purchase";

import "../../../style/cart.css";

import * as Step from "../dumb/Cart/Step";

import { CartStep, CartStepHeader } from "../dumb/Cart/CartStepHeader";

const purchases: IPurchase[] = [{
  amount: 2,
  item: {name: "something", imageUrl:"", description:"", price: 5},
  total: 100,
  unitPrice: 50
}];

interface CartState {
    currentStep: CartStep;
}

export class Cart extends React.Component<{}, CartState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            currentStep: CartStep.CART
        };
    }

    setStep = (step: CartStep) => {
        this.setState({currentStep: step});
    }

    nextStep = () => {
        const step = this.state.currentStep;
        if(step === CartStep.APPROVAL) {
            console.log("Finished");
            // TODO: finish this somehow
            return;
        }
        this.setStep(this.state.currentStep+1);
    }

    render () {
        return (
            <div>
                <CartStepHeader active={this.state.currentStep} onStepChange={this.setStep} />
                {this.state.currentStep === CartStep.CART
                ? <Step.Cart purchases={purchases} onStepComplete={this.nextStep} />
                : ""
                }
                {this.state.currentStep === CartStep.DELIVERY_INFO
                ? <Step.DeliveryInfo onStepComplete={this.nextStep} />
                : ""
                }
                {this.state.currentStep === CartStep.PAYMENT
                ? <Step.Payment onStepComplete={this.nextStep} />
                : ""
                }
            </div>
        );
    }
}
