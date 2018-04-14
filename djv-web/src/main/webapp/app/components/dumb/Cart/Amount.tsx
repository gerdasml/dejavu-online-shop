import * as React from "react";
import { Button, Input } from "semantic-ui-react";

import "../../../../style/cart.css";

export interface IAmountProps {amount: number;}
export interface IAmountState { clicks: number; }

export class Amount extends React.Component<IAmountProps, IAmountState> {
    constructor (props: IAmountProps) {
        super(props);
        this.state = {
          clicks: props.amount
        };
      }

    render () {
        return(
            <Input type="text" action>
                <Button id="amountButtonMinus" disabled={this.state.clicks===0}
                    onClick={this.decrementItem}><span className="amountButton">-</span></Button>
                <input id="amountInput" type="text" value={ this.state.clicks}
                    onChange={e => this.checkInput(e.target.value)}></input>
                <Button id="amountButtonPlus"
                    onClick={this.incrementItem}><span className="amountButton">+</span></Button>
            </Input>
        );
    }
    incrementItem = () => {
        this.setState({ clicks: this.state.clicks + 1 });
    }
    decrementItem = () => {
        this.setState({ clicks: this.state.clicks - 1 });
    }
    checkInput = (value: string) => {
        const numValue = Number(value);
        if(!isNaN(numValue)) {
            this.setState({clicks:numValue});
        }
    }
}
