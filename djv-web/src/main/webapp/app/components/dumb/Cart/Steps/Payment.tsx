import * as React from "react";

import { notification } from "antd";

import Card from "react-credit-cards";
import "react-credit-cards/es/styles-compiled.css";

import { Button, Form, List, Message, Icon } from "semantic-ui-react";

import { clearNumber, formatCardNumber, formatExpirationDate } from "../../../../utils/cardInput";

import * as model from "../../../../model/Payment";

import * as api from "../../../../api";

import "../../../../../style/payment.css";

import { ApiError } from "../../../../api";

import { ShippingInformation } from "../../../../model/ShippingInformation";

import { fromString } from "../../../../utils/enum";

interface PaymentProps {
    onStepComplete: () => void;
    shippingInformation: ShippingInformation;
}

interface PaymentState {
    number: string;
    name: string;
    expiry: string;
    cvc: string;
    focused: CardField;

    isLoading: boolean;
    errors: ReadonlyArray<model.ValidationError>;
}

enum CardField {
    NUMBER = "number",
    NAME = "name",
    EXPIRY = "expiry",
    CVC = "cvc"
}

type CardFieldStr = "number" | "name" | "expiry" | "cvc";

export class Payment extends React.Component<PaymentProps, PaymentState> {
    state: PaymentState = {
        cvc: "",
        errors: [],
        expiry: "",
        focused: CardField.NUMBER,
        isLoading: false,
        name: "",
        number: "",
    };

    handleInputFocus = (event: React.FormEvent<HTMLInputElement>) => {
        const val: string = event.currentTarget.name;
        const field = fromString(CardField, val);
        this.setState({...this.state, focused: field});
    }

    handleInputChange = (event: React.FormEvent<HTMLInputElement>) => {
        const field = fromString(CardField, event.currentTarget.name);
        const value = event.currentTarget.value;
        const newState = {...this.state};
        switch(field) {
            case CardField.NUMBER:
                newState.number = formatCardNumber(value);
                break;
            case CardField.NAME:
                newState.name = value;
                break;
            case CardField.EXPIRY:
                newState.expiry = formatExpirationDate(value);
                break;
            case CardField.CVC:
                newState.cvc = value;
                break;
        }
        this.setState(newState);
    }

    buildExpiration = (): model.Expiration => {
        const parts = this.state.expiry.split("/");
        const month = Number(parts[0]);
        const year = Number("20"+parts[1]);
        return {month, year};
    }

    buildCard = (): model.Card => ({
        cvv: this.state.cvc,
        expiration: this.buildExpiration(),
        holder: this.state.name,
        number: clearNumber(this.state.number)

    })

    showValidationErrors = (errors: model.ValidationError[]) => {
        const arr = this.state.errors.concat(errors);
        this.setState({...this.state, errors: arr});
    }

    showError = (prefix: string, err: ApiError) =>
        this.setState({...this.state, errors: [{
            location: "payment",
            message: prefix + ". Error: " + err.type
        }]})

    isErrorPresent = (name: string) => this.state.errors.some(e => e.location === name);

    handleFormSubmit = async () => {
        this.setState({
            ...this.state,
            errors: [],
            isLoading: true,
        });
        if(await this.validate() && await this.pay()) {
            this.props.onStepComplete();
        }
        this.setState({
            ...this.state,
            isLoading: false,
        });
    }

    pay = async () => {
        const paymentCard = this.buildCard();
        const shippingInformation = this.props.shippingInformation;
        const checkoutInfo = {
            card: paymentCard,
            shippingInformation,
        };
        const checkoutResponse = await api.cart.checkout(checkoutInfo);
        if(api.isError(checkoutResponse)) {
            notification.error({message: "Failed to checkout", description: checkoutResponse.message});
            return false;
        }
        return true;
    }

    validate = async () => {
        const card = this.buildCard();
        const validationResponse = await api.payment.validate(card);
        if(api.isError(validationResponse)) {
            this.showError("Validation has failed", validationResponse);
            return false;
        }
        if(validationResponse.length !== 0) {
            this.showValidationErrors(validationResponse);
            return false;
        }
        return true;
    }

    render () {
        return (
            <div>
                <div className="paymentInputContainer">
                <List horizontal verticalAlign="middle">
                    <List.Item>
                        <Card
                            number={this.state.number}
                            name={this.state.name}
                            expiry={this.state.expiry}
                            cvc={this.state.cvc}
                            focused={this.state.focused.toString() as CardFieldStr}
                        />
                    </List.Item>
                    <List.Item className="payment-form">
                        <Form size="mini" loading={this.state.isLoading}>
                            <Form.Input
                                required
                                placeholder="Card number"
                                name={CardField.NUMBER.toString()}
                                onFocus={this.handleInputFocus}
                                pattern="[\d| ]{16,19}"
                                value={this.state.number}
                                onChange={e => this.handleInputChange(e)}
                                error={this.isErrorPresent("card.number")}
                            />
                            <Form.Input
                                required
                                placeholder="Name"
                                name={CardField.NAME.toString()}
                                onFocus={this.handleInputFocus}
                                value={this.state.name}
                                onChange={e => this.handleInputChange(e)}
                                error={this.isErrorPresent("card.holder")}
                            />
                            <Form.Group widths="equal">
                                <Form.Input
                                    required
                                    placeholder="MM/YY"
                                    pattern="\d\d/\d\d"
                                    name={CardField.EXPIRY.toString()}
                                    onFocus={this.handleInputFocus}
                                    value={this.state.expiry}
                                    onChange={e => this.handleInputChange(e)}
                                    error={
                                        this.isErrorPresent("expiration.month") ||
                                        this.isErrorPresent("expiration.year")}
                                />
                                <Form.Input
                                    required
                                    placeholder="CVC"
                                    name={CardField.CVC.toString()}
                                    onFocus={this.handleInputFocus}
                                    value={this.state.cvc}
                                    onChange={e => this.handleInputChange(e)}
                                    error={this.isErrorPresent("card.cvv")}
                                />
                            </Form.Group>
                        </Form>
                    </List.Item>
                </List>
                </div>
                <Button
                    icon
                    type="submit"
                    positive
                    labelPosition="right"
                    floated="right"
                    onClick={this.handleFormSubmit}>
                    Next
                    <Icon name="chevron right" />
                    </Button>
                    <div className="payment-messages">
                {
                    this.state.errors.map((x, i) =>
                        <Message
                            key={i}
                            error
                            content={x.message}
                        />)
                }
                </div>
            </div>
        );
    }
}
