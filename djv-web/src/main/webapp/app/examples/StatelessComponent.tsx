import * as React from "react";

export const StatelessComponentWithoutProps = () => <p>Stateless component with no props</p>;

export interface MyPropType { greeting: string; }
export const StatelessComponentWithProps =
    (props: MyPropType) => <p>Stateless component with props (they say {props.greeting}).</p>;
