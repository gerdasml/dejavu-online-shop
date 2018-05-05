import * as React from "react";

import Card from "react-credit-cards";
import "react-credit-cards/es/styles-compiled.css";

import { Button, Form, List, Message } from "semantic-ui-react";

import { clearNumber, formatCardNumber, formatExpirationDate } from "../../../../utils/cardInput";

import * as model from "../../../../model/Payment";

import * as api from "../../../../api";

import "../../../../../style/payment.css";
import { ApiError } from "../../../../api";

interface PaymentProps {
    onStepComplete: () => void;
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

    fieldTypeFromString = (val: string) => CardField[val.toUpperCase() as keyof typeof CardField];

    handleInputFocus = (event: React.FormEvent<HTMLInputElement>) => {
        const val: string = event.currentTarget.name;
        const field = this.fieldTypeFromString(val);
        this.setState({...this.state, focused: field});
    }

    handleInputChange = (event: React.FormEvent<HTMLInputElement>) => {
        const field = this.fieldTypeFromString(event.currentTarget.name);
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

    buildPayment = (): model.Payment => ({
        amount: 10, // TODO: figure out how to get this
        card: {
            cvv: this.state.cvc,
            expiration: this.buildExpiration(),
            holder: this.state.name,
            number: clearNumber(this.state.number)
        },
    })

    sleep = async (ms: number) => await new Promise(r => setTimeout(r, ms));

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

    setLoading = (isLoading: boolean) => this.setState({...this.state, isLoading});

    handleFormSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        this.setLoading(true);
        const isOk = await this.checkData();
        this.setLoading(false);
        if(isOk) {
            this.props.onStepComplete();
        }
    }
    checkData = async () => {
        const payment = this.buildPayment();
        const validationResponse = await api.payment.validate(payment);
        console.log(payment, validationResponse);
        await this.sleep(1000);
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
                    <List.Item>
                        <Form size="mini" onSubmit={this.handleFormSubmit} loading={this.state.isLoading}>
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
                                    error={this.isErrorPresent("expiration.month") || this.isErrorPresent("expiration.year")}
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
                            <Button type="submit" fluid positive>Next</Button>
                        </Form>
                    </List.Item>
                </List>
                {
                    this.state.errors.map((x, i) =>
                        <Message
                            key={i}
                            error
                            content={x.message}
                        />)
                }
            </div>
        );
    }
}
