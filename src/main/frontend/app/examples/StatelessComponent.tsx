import * as React from "react";

export const StatelessComponentWithoutProps = () => <p>Stateless component with no props</p>;

export interface IMyPropType { greeting: string; }
export const StatelessComponentWithProps =
    (props: IMyPropType) => <p>Stateless component with props (they say {props.greeting}).</p>;
