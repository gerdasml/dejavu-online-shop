import * as React from "react";

interface DeliveryInfoProps {
    onComplete: () => void;
}
export const DeliveryInfo = (props: DeliveryInfoProps) => (
    <div>
        Delivery Info Page<br/>
        <button onClick={props.onComplete}>Next</button>
    </div >
);
