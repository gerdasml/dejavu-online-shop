import * as React from "react";

import { Image, Menu, Segment, Sidebar } from "semantic-ui-react";

import "../../../style/drawer.css";

const myImage = require("../../assets/placeholder_350x150.png");

export interface IDrawerMenuState { visible: boolean; }

export class DrawerMenu extends React.Component<{}, IDrawerMenuState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            visible: true
        };
    }
    render () {
        return (
            <div>
                <Sidebar.Pushable as={Segment}>
                <Sidebar className="drawer"
                         as={Menu}
                         animation="push"
                         width="thin"
                         visible={this.state.visible}
                         icon="labeled"
                         vertical
                         inverted
                />
                <Sidebar.Pusher>
                    <Segment basic className="content">
                        <button onClick={() => this.setState({visible: !this.state.visible})}>go</button>
                        <Image src={myImage} />
                    </Segment>
                </Sidebar.Pusher>
                </Sidebar.Pushable>
            </div>
        );
    }
}
