import * as React from "react";

// This lib has no types yet :(
import * as cards from "react-credit-cards";
import "react-credit-cards/es/styles-compiled.css";

import { Form, Grid, Button } from "semantic-ui-react";

interface PaymentProps {
    onComplete: () => void;
}

interface PaymentState {
    number: string;
    name: string;
    expiry: string;
    cvc: string;
    focused: CardField;
}

enum CardField {
    NUMBER = "number",
    NAME = "name",
    EXPIRY = "expiry",
    CVC = "cvc"
}

export class Payment extends React.Component<PaymentProps, PaymentState> {
    state = {
        cvc: "",
        expiry: "",
        focused: CardField.NUMBER,
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
                newState.number = value; break;
            case CardField.NAME:
                newState.name = value; break;
            case CardField.EXPIRY:
                newState.expiry = value; break;
            case CardField.CVC:
                newState.cvc = value; break;
        }
        this.setState(newState);
    }

    checkData = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // TODO: call api/payment/validate to validate input
        this.props.onComplete();
    }

    render() {
        return (
            <Grid>
                <Grid.Column width={12}>
                    <Form size="mini" onSubmit={this.checkData}>
                        <Form.Input
                            required
                            placeholder="Card number"
                            name={CardField.NUMBER.toString()}
                            onFocus={this.handleInputFocus}
                            pattern="[\d| ]{16}"
                            onChange={e => this.handleInputChange(e)}
                        />
                        <Form.Input
                            required
                            placeholder="Name"
                            name={CardField.NAME.toString()}
                            onFocus={this.handleInputFocus}
                            onChange={e => this.handleInputChange(e)}
                        />
                        <Form.Group widths="equal">
                            <Form.Input
                                required
                                placeholder="MM/YY"
                                pattern="\d\d/\d\d"
                                name={CardField.EXPIRY.toString()}
                                onFocus={this.handleInputFocus}
                                onChange={e => this.handleInputChange(e)}
                            />
                            <Form.Input
                                required
                                placeholder="CVC"
                                name={CardField.CVC.toString()}
                                onFocus={this.handleInputFocus}
                                onChange={e => this.handleInputChange(e)}
                            />
                        </Form.Group>
                        <Button type="submit">Next</Button>
                    </Form>
                </Grid.Column>
                <Grid.Column width={4}>
                    <cards.default
                        number={this.state.number}
                        name={this.state.name}
                        expiry={this.state.expiry}
                        cvc={this.state.cvc}
                        focused={this.state.focused.toString()}
                    />
                </Grid.Column>
            </Grid>
        );
    }
}
