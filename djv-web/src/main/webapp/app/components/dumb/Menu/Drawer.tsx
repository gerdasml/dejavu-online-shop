import * as React from "react";

import { Container, Image, Menu, Segment, Sidebar } from "semantic-ui-react";

import "../../../../style/drawer.css";
import { MenuItem } from "../../smart/Menu/MenuItem";

import {categories} from "../../../data/categories";
import { SubMenu } from "../../smart/Menu/SubMenu";

export interface IDrawerMenuState { visible: boolean; current: string; }

export class DrawerMenu extends React.Component<{}, IDrawerMenuState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            current: undefined,
            visible: true,
        };
    }

    onHover = (cname: string) => {
        this.setState({...this.state, current: cname});
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
                                        key={i}
                                        onHover={(n: string) => this.onHover(n)}/>
                                    )}
                </Sidebar>
                <Sidebar.Pusher>
                    <Segment basic className="mainContainer">
                        {this.state.current === undefined ? "" :
                        <SubMenu category={this.state.current} onHover={this.onHover} /> }
                        <Container className="content">
                            {this.props.children}
                        </Container>
                    </Segment>
                </Sidebar.Pusher>
                </Sidebar.Pushable>
            </div>
        );
    }
}
