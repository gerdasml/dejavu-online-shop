import * as React from "react";

import { Button, Icon, List } from "semantic-ui-react";
import { Amount } from "../../dumb/Cart/Amount";

import "../../../../style/product.css";

export interface PriceAreaProps {
    onAmountChange: (amount: number) => void;
}

export interface PriceAreaState {
    amount: number;
}

export class PriceArea extends React.Component<PriceAreaProps, PriceAreaState> {
    state: PriceAreaState = {
        amount: 1
    };
    render () {
        return (
            <List horizontal className="price-area-container">
                <List.Item className="productColumn">
                    <Amount onAmountChange={amount => this.setState({amount})} amount={this.state.amount}/>
                </List.Item>
                <List.Item >
                    <Button
                        className="add-to-cart"
                        positive
                        animated="fade"
                        onClick={() => this.props.onAmountChange(this.state.amount)}
                    >
                        <Button.Content visible>
                            <Icon name="shop" className="button-text"/>
                        </Button.Content>
                        <Button.Content hidden>
                            <Icon name="plus" className="button-text"/>
                        </Button.Content>
                    </Button>
                </List.Item>
            </List>
        );
    }
}
