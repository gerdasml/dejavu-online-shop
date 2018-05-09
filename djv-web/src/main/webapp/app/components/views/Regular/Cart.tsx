import * as React from "react";

import { OrderItem } from "../../../model/Order";

import { Cart as CartModel } from "../../../model/Cart";

import "../../../../style/cart.css";

import * as Step from "../../dumb/Cart/Step";

import * as api from "../../../api";

import { Loader } from "semantic-ui-react";

import { CartStep, CartStepHeader } from "../../dumb/Cart/CartStepHeader";

const purchases: OrderItem[] = [{
    amount: 2,
    product: {name: "something", mainImageUrl:"", description:"", price: 5, properties: []},
    total: 100
},
{
    amount: 3,
    product: {name: "something2", mainImageUrl:"", description:"", price: 5, properties: []},
    total: 90
}];

interface CartState {
    currentStep: CartStep;
    error?: string;
    isLoading: boolean;
    // purchases: OrderItem[];
    cart: CartModel;
}

export class Cart extends React.Component<{}, CartState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            cart: undefined,
            currentStep: CartStep.CART,
            error: undefined,
            isLoading: true,
            // purchases: [],
        };
    }

    async componentDidMount () {
        const cartInfo = await api.cart.getCart();
        if(api.isError(cartInfo)) {
            this.setState({
                ...this.state,
                error: cartInfo.message,
                isLoading: false,
            });
        } else {
            // console.log(cartInfo);
            /*cartInfo.items.forEach( (x,_) => // reikia key={i} panaudot, kad warningo nebutu
                this.state.purchases.push({
                    amount: x.amount,
                    product: x.product,
                    total: x.total
                })
            );*/
            this.setState({
                ...this.state,
                cart: cartInfo,
                isLoading: false,
            });
        }
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
                { this.state.currentStep === CartStep.CART
                ?
                    ( this.state.isLoading
                    ? <Loader active inline="centered" />
                    : <Step.Cart cart={this.state.cart} onStepComplete={this.nextStep} />
                    )
                : ""
                }
                {this.state.currentStep === CartStep.DELIVERY_INFO
                ? <Step.DeliveryInfo onStepComplete={this.nextStep} />
                : ""
                }
                {this.state.currentStep === CartStep.CONFIRMATION
                ? <Step.Confirmation purchases={purchases} onStepComplete={this.nextStep}/>
                : ""
                }
                {this.state.currentStep === CartStep.PAYMENT
                ? <Step.Payment onStepComplete={this.nextStep} />
                : ""
                }
                {this.state.currentStep === CartStep.APPROVAL
                ? <Step.Approval onStepComplete={this.nextStep} />
                : ""
                }
            </div>
        );
    }
}
