import * as React from "react";

import { Button, Icon, Message } from "semantic-ui-react";

import "../../../../../style/cart.css";

import {NavLink} from "react-router-dom";

interface ApprovalProps {
    onStepComplete: () => void;
}
export const Approval = (props: ApprovalProps) => (
    <div id="approval">
        <Message
            className="approvalText"
            icon="check"
            header="Your purchase was successful!"
            content={
                <span>
                    <p>
                        To check your purchase info and delivery status open your profile.
                    </p>
                    <p>
                        It is displayed in the "Order history" table.
                    </p>
                    <p>
                        You can now continue shopping.
                    </p>
                </span>
            }
        />
        <Button
            id="continueButton"
            positive icon labelPosition="right"
            floated="right"
            as={NavLink}
            to="/"
            onClick={props.onStepComplete}>
                Continue
                <Icon name="chevron right" />
        </Button>
    </div >
);
