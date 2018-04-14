import * as React from "react";
import { Button, Input } from "semantic-ui-react";

import "../../../../style/cart.css";

export interface IMyProps {}
export interface IMyState { clicks: number; }

export class Amount extends React.Component<IMyProps, IMyState> {
    constructor (props: {}) {
        super(props);
        this.state = {
          clicks:0
        };
      }

    render () {
        return(
            <Input type="text" placeholder="0" action>
                <Button id="amountButtonMinus" disabled={this.state.clicks===0}
                    onClick={this.DecreaseItem}><span className="amountButton">-</span></Button>
                <input id="amountInput" type="text" value={ this.state.clicks }
                    onChange={e => this.CheckInput(e.target.value)}></input>
                <Button id="amountButtonPlus"
                    onClick={this.IncrementItem}><span className="amountButton">+</span></Button>
            </Input>
        );
    }
    IncrementItem = () => {
        this.setState({ clicks: this.state.clicks + 1 });
    }
    DecreaseItem = () => {
        this.setState({ clicks: this.state.clicks - 1 });
    }
    CheckInput = (value: string) => {
        if(!isNaN(Number(value))) {
            this.setState({clicks:Number(value)});
        }
    }
}
