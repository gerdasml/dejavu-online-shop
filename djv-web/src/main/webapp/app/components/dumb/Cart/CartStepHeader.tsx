import * as React from "react";

import { Icon, Step } from "semantic-ui-react";

import "../../../../style/cart.css";

export enum CartStep {
    CART,
    DELIVERY_INFO,
    PAYMENT,
    CONFIRMATION,
    APPROVAL
}

interface StepItemProps {
    activeStep: CartStep;
    step: CartStep;
    text: string;
    onStepChange: (cs: CartStep) => void;
}

const StepItem = (props: StepItemProps) =>
    (
        <Step
            active={props.activeStep === props.step}
            disabled={props.activeStep < props.step}
            link
            onClick={()=>props.onStepChange(props.step)}
        >
            <Icon name="cart"/>
            <Step.Content>
                <Step.Title>{props.text}</Step.Title>
            </Step.Content>
        </Step>
    );

interface CartStepHeaderProps {
    active: CartStep;
    onStepChange: (cs: CartStep) => void;
}

export const CartStepHeader = (props: CartStepHeaderProps) =>
    (
        <Step.Group size="mini" id="stepGroup">
            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.CART}
                text = "Cart"
            />

            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.DELIVERY_INFO}
                text = "Delivery Info"
            />

            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.PAYMENT}
                text = "Payment"
            />

            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.CONFIRMATION}
                text = "Confirmation"
            />

            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.APPROVAL}
                text = "Approval"
            />
        </Step.Group>
    );
