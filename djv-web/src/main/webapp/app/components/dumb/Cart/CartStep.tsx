import * as React from "react";

import { Icon, Step } from "semantic-ui-react";

import "../../../../style/cart.css";

export class CartStep extends React.Component {

    render() {
        return (
            <Step.Group size="mini" id="stepGroup">

                <Step  active>
                    <Icon name="cart"/>
                    <Step.Content>
                        <Step.Title>Cart</Step.Title>
                    </Step.Content>
                </Step>

                <Step  disabled>
                    <Icon name="truck"/>
                    <Step.Content>
                        <Step.Title>Delivery info</Step.Title>
                    </Step.Content>
                </Step>

                <Step disabled>
                    <Icon name="payment" />
                    <Step.Content>
                        <Step.Title>Payment</Step.Title>
                    </Step.Content>
                </Step>

                <Step disabled>
                    <Icon name="info" />
                    <Step.Content>
                        <Step.Title>Confirmation</Step.Title>
                    </Step.Content>
                </Step>

                <Step disabled>
                    <Icon name="check" />
                    <Step.Content>
                        <Step.Title>Approvement</Step.Title>
                    </Step.Content>
                </Step>
            </Step.Group>
        );
    }
}
