import * as React from "react";

import { notification } from "antd";

import { Cart as CartModel } from "../../../model/Cart";

import "../../../../style/cart.css";

import * as Step from "../../dumb/Cart/Step";

import * as api from "../../../api";

import { Loader } from "semantic-ui-react";

import { CartStep, CartStepHeader } from "../../dumb/Cart/CartStepHeader";

import * as CartManager from "../../../utils/cart";
import { ShippingInformation } from "../../../model/ShippingInformation";

interface CartState {
    currentStep: CartStep;
    isLoading: boolean;
    cart: CartModel;
    shippingInformation?: ShippingInformation;
    saveToProfile: boolean;
}

const EMPTY_STATE: CartState = {
    cart: {
        items: [],
        total: 0,
        user: undefined,
    },
    currentStep: CartStep.CART,
    isLoading: true,
    saveToProfile: false
};

export class Cart extends React.Component<{}, CartState> {
    state: CartState = EMPTY_STATE;

    buildShippingInformation = (cart: CartModel) => {
        const info: ShippingInformation = {
            recipientFirstName: undefined,
            recipientLastName: undefined,
            shippingAddress: {}
        };
        const user = cart.user;
        if(user !== undefined) {
            info.recipientFirstName = user.firstName;
            info.recipientLastName = user.lastName;
            info.shippingAddress = user.address;
        }
        return info;
    }

    async componentDidMount () {
        const cartInfo = await CartManager.getCart();
        if(api.isError(cartInfo)) {
            notification.error({message: "Failed to fetch cart.", description: cartInfo.message});
        } else {
            this.setState({
                ...this.state,
                cart: cartInfo,
                shippingInformation: this.buildShippingInformation.bind(this)(cartInfo)
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

    nextStep = async () => {
        const step = this.state.currentStep;
        if (step+1 === CartStep.APPROVAL) {
            if (this.state.saveToProfile) {
                const newUser = {
                    ...this.state.cart.user,
                    address: this.state.shippingInformation.shippingAddress
                };
                const response = await api.user.updateUser(newUser);
                if (api.isError(response)) {
                    notification.error({message: "Failed to update profile", description: response.message});
                    return;
                }
            }
            this.setState({...EMPTY_STATE, currentStep: step, isLoading: false});
        }
        if(step === CartStep.APPROVAL) {
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
                    shippingInformation={this.state.shippingInformation}
                    onShippingInfoChange={info => this.setState({...this.state, shippingInformation: info})}
                    saveToProfile={this.state.saveToProfile}
                    onSaveToProfileChange={val => this.setState({...this.state, saveToProfile: val})}
                />
                : ""
                }
                {this.state.currentStep === CartStep.CONFIRMATION
                ? <Step.Confirmation cart={this.state.cart} onStepComplete={this.nextStep}/>
                : ""
                }
                {this.state.currentStep === CartStep.PAYMENT
                ? <Step.Payment onStepComplete={this.nextStep} shippingInformation={this.state.shippingInformation}/>
                : ""
                }
                {this.state.currentStep === CartStep.APPROVAL
                ? <Step.Approval onStepComplete={this.nextStep}/>
                : ""
                }
            </div>
        );
    }
}
