import * as React from "react";

import { Button, Icon, Table } from "semantic-ui-react";

import "../../../../../style/cart.css";

interface ApprovalProps {
    onStepComplete: () => void;
}
export const Approval = (props: ApprovalProps) => (
    <div>
        <h3 id="approvalText">Yor purchase was successful! You can now continue shopping.</h3>
        <Button id="continueButton" positive icon labelPosition="right" floated="right"
            onClick={props.onStepComplete}>
                Continue
                <Icon name="chevron right" />
        </Button>
    </div >
);
