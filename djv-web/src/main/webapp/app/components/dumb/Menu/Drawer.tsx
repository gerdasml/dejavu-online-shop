import * as React from "react";

import { Icon, Image, Menu, Segment, Sidebar } from "semantic-ui-react";

import "../../../../style/drawer.css";
import { MenuItem } from "../../smart/Menu/MenuItem";

import {categories} from "../../../data/categories";
const myImage = require("../../../assets/placeholder_350x150.png");

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
                <Sidebar
                    className="drawer"
                    direction="left"
                    as={Menu}
                    animation="push"
                    width="thin"
                    visible={this.state.visible}
                    vertical inverted>
                    {categories.map((x, i) =>
                            <MenuItem   name={x.name}
                                        itemName={x.displayName}
                                        icon={x.icon}
                                        key={i}/>
                                    )}
                </Sidebar>
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
