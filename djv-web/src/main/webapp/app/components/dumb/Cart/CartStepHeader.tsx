import * as React from "react";

import { Icon, SemanticICONS, Step } from "semantic-ui-react";

import "../../../../style/cart.css";

export enum CartStep {
    CART,
    DELIVERY_INFO,
    CONFIRMATION,
    PAYMENT,
    APPROVAL
}

interface StepItemProps {
    activeStep: CartStep;
    step: CartStep;
    text: string;
    onStepChange: (cs: CartStep) => void;
    icon: string;
}

const StepItem = (props: StepItemProps) =>
    (
        <Step
            active={props.activeStep === props.step}
            disabled={props.activeStep < props.step}
            link
            onClick={()=>props.onStepChange(props.step)}
        >
            <Icon name={props.icon as SemanticICONS}/>
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
        <Step.Group size="mini" id="stepGroup" widths={5}>
            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.CART}
                text = "Cart"
                icon = "cart"
            />

            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.DELIVERY_INFO}
                text = "Delivery Info"
                icon = "truck"
            />

            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.CONFIRMATION}
                text = "Confirmation"
                icon = "info"
            />

            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.PAYMENT}
                text = "Payment"
                icon = "payment"
            />

            <StepItem
                activeStep = {props.active}
                onStepChange = {props.onStepChange}
                step = {CartStep.APPROVAL}
                text = "Approval"
                icon = "check"
            />
        </Step.Group>
    );
