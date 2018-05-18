import * as React from "react";

import { Button, Icon } from "semantic-ui-react";

import "../../../../../style/cart.css";

import {NavLink} from "react-router-dom";

interface ApprovalProps {
    onStepComplete: () => void;
}
export const Approval = (props: ApprovalProps) => (
    <div id="approval">
        <h3 className="approvalText">Your purchase was successful! </h3>
        <br/>
        <h3 className="approvalText">
            To check your purchase info and delivery status open your profile. 
            It is displayed in the "Order history" table.
        </h3>
        <br/>
        <h3 className="approvalText">You can now continue shopping.</h3>
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
