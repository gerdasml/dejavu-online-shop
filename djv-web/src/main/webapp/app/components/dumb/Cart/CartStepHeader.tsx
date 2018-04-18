import * as React from "react";

import { Icon, Step } from "semantic-ui-react";

import "../../../../style/cart.css";

interface CartStepProps { active: CartStep; }

export enum CartStep {
    CART,
    DELIVERY_INFO,
    PAYMENT,
    CONFIRMATION,
    APPROVAL
}

export const CartStepHeader = (props: CartStepProps) =>
    (
        <Step.Group size="mini" id="stepGroup">

            <Step
                active={props.active === CartStep.CART}
                disabled={props.active < CartStep.CART}
            >
                <Icon name="cart"/>
                <Step.Content>
                    <Step.Title>Cart</Step.Title>
                </Step.Content>
            </Step>

            <Step
                active={props.active === CartStep.DELIVERY_INFO}
                disabled={props.active < CartStep.DELIVERY_INFO}
            >
                <Icon name="truck"/>
                <Step.Content>
                    <Step.Title>Delivery info</Step.Title>
                </Step.Content>
            </Step>

            <Step
                active={props.active === CartStep.PAYMENT}
                disabled={props.active < CartStep.PAYMENT}
            >
                <Icon name="payment" />
                <Step.Content>
                    <Step.Title>Payment</Step.Title>
                </Step.Content>
            </Step>

            <Step
                active={props.active === CartStep.CONFIRMATION}
                disabled={props.active < CartStep.CONFIRMATION}
            >
                <Icon name="info" />
                <Step.Content>
                    <Step.Title>Confirmation</Step.Title>
                </Step.Content>
            </Step>

            <Step
                active={props.active === CartStep.APPROVAL}
                disabled={props.active < CartStep.APPROVAL}
            >
                <Icon name="check" />
                <Step.Content>
                    <Step.Title>Approvement</Step.Title>
                </Step.Content>
            </Step>
        </Step.Group>
    );
