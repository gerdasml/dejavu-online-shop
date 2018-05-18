import * as React from "react";

import { notification } from "antd";

import { Cart as CartModel } from "../../../model/Cart";

import "../../../../style/cart.css";

import * as Step from "../../dumb/Cart/Step";

import * as api from "../../../api";

import { Loader } from "semantic-ui-react";

import { Address } from "../../../model/Address";
import { User } from "../../../model/User";
import { CartStep, CartStepHeader } from "../../dumb/Cart/CartStepHeader";

interface CartState {
    currentStep: CartStep;
    isLoading: boolean;
    cart: CartModel;
    shippingAddress?: Address;
}

export class Cart extends React.Component<{}, CartState> {
    state: CartState = {
        cart: {
            items: [],
            total: 0,
            user: undefined,
        },
        currentStep: CartStep.CART,
        isLoading: true,
    };

    async componentDidMount () {
        const cartInfo = await api.cart.getCart();
        if(api.isError(cartInfo)) {
            notification.error({message: "Failed to upload cart", description: cartInfo.message});
        } else {
            this.setState({
                ...this.state,
                cart: cartInfo,
                shippingAddress: cartInfo.user.address
            });
        }
        this.setState({
            ...this.state,
            isLoading: false,
        });
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
                    :
                    <Step.Cart
                        cart={this.state.cart}
                        onStepComplete={this.nextStep}
                        onCartUpdate={cart => this.setState({...this.state, cart})}
                    />
                    )
                : ""
                }
                {this.state.currentStep === CartStep.DELIVERY_INFO
                ?
                <Step.DeliveryInfo
                    onStepComplete={this.nextStep}
                    shippingAddress={this.state.shippingAddress}
                    onShippingInfoChange={address => this.setState({...this.state, shippingAddress: address})}
                />
                : ""
                }
                {this.state.currentStep === CartStep.CONFIRMATION
                ? <Step.Confirmation cart={this.state.cart} onStepComplete={this.nextStep}/>
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
