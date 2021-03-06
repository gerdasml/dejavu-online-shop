import * as React from "react";

export interface MyProps { prop1: string; }
export interface MyState { prop1: number; }

// Uncomment this for the other example
/*export class StatefulComponentPropsNoState extends React.Component<IMyProps, {}> {
    render () {
        return <p>Stateful component with props but no state (prop1 = {this.props.prop1}).</p>;
    }
}*/

export class StatefulComponentPropsState extends React.Component<MyProps, MyState> {
    render () {
        return (
            <p>
                Stateful component with props and state
                (props.prop1 = {this.props.prop1},
                state.prop1 = {this.state.prop1}).
            </p>
        );
    }
}
