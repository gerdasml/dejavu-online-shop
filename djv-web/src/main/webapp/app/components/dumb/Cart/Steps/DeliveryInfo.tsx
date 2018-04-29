import * as React from "react";

interface DeliveryInfoProps {
    onStepComplete: () => void;
}
export const DeliveryInfo = (props: DeliveryInfoProps) => (
    <div>
        Delivery Info Page<br/>
        <button onClick={props.onStepComplete}>Next</button>
    </div >
);
