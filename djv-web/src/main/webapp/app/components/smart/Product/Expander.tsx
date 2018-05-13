import * as React from "react";

import "../../../../style/product.css";

export interface ExpanderProps {
    text: string;
}

export interface ExpanderState {
    trimmedText: string;
    expand: boolean;
    collapseText: string;
    expandText: string;
    minSymbols: number;
}

export class Expander extends React.Component<ExpanderProps, ExpanderState> {
    state: ExpanderState = this.getInitialState(this.props);
    getInitialState (props: ExpanderProps) {
        const state = {
            collapseText: " ...show more",
            expand: false,
            expandText: " show less",
            minSymbols: 300,
            trimmedText: props.text
        };
        if(this.props.text.length > state.minSymbols) {
            state.trimmedText = props.text.substr(0, state.minSymbols);
        } else {
            state.collapseText =  "";
            state.expandText = "";
        }
        return state;
    }
    componentWillReceiveProps (nextProps: ExpanderProps) {
        this.setState(this.getInitialState(nextProps));
    }
    handleShowMore () {
        this.setState({...this.state, trimmedText: this.props.text, expand: true});
    }
    handleShowLess () {
        this.setState({
            ...this.state,
            expand: false,
            trimmedText: this.props.text.substr(0, this.state.minSymbols)});
    }
    render () {
        return (
            <div>
                {
                    this.state.expand
                    ? <span>
                        {this.state.trimmedText}
                        <span className="expander-color" onClick={this.handleShowLess.bind(this)}>
                            {this.state.expandText}
                        </span>
                    </span>
                    : <span>
                        {this.state.trimmedText}
                        <span className="expander-color" onClick={this.handleShowMore.bind(this)}>
                            {this.state.collapseText}
                        </span>
                    </span>
                }
            </div>
        );
    }
}
