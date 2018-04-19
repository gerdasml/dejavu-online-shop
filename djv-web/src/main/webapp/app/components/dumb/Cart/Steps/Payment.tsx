import * as React from "react";

import Card from "react-credit-cards";
import "react-credit-cards/es/styles-compiled.css";

import { Button, Form, List, Dimmer, Loader, Message } from "semantic-ui-react";

import { clearNumber, formatCardNumber, formatExpirationDate } from "../../../../utils/cardInput";

import * as model from "../../../../model/Payment";

import * as api from "../../../../api";

import "../../../../../style/payment.css";

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
            holder: this.state.name,
            number: clearNumber(this.state.number)
        },
        expiration: this.buildExpiration()
    })

    sleep = async (ms: number) => await new Promise(r => setTimeout(r, ms));

    checkData = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const payment = this.buildPayment();
        this.setState({...this.state, isLoading: true});
        const response = await api.payment.validate(payment);
        await this.sleep(5000);
        this.setState({...this.state, isLoading: false});
        if(api.isError(response)) {
            console.error(response);
            return;
        }
        if(response.length !== 0) {
            console.error(response); // TODO: show validation errors in UI
            return;
        }
        this.props.onStepComplete();
        return {};
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
                        <Form size="mini" onSubmit={this.checkData} loading={this.state.isLoading}>
                            <Form.Input
                                required
                                placeholder="Card number"
                                name={CardField.NUMBER.toString()}
                                onFocus={this.handleInputFocus}
                                pattern="[\d| ]{16,19}"
                                value={this.state.number}
                                onChange={e => this.handleInputChange(e)}
                                error
                            />
                            <Form.Input
                                required
                                placeholder="Name"
                                name={CardField.NAME.toString()}
                                onFocus={this.handleInputFocus}
                                value={this.state.name}
                                onChange={e => this.handleInputChange(e)}
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
                                />
                                <Form.Input
                                    required
                                    placeholder="CVC"
                                    name={CardField.CVC.toString()}
                                    onFocus={this.handleInputFocus}
                                    value={this.state.cvc}
                                    onChange={e => this.handleInputChange(e)}
                                />
                            </Form.Group>
                            <Button type="submit" fluid positive>Next</Button>
                        </Form>
                    </List.Item>
                </List>
                <Message
                    error
                    content="This is an error"
                />
            </div>
        );
    }
}
