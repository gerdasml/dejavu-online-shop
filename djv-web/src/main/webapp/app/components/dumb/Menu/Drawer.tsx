import * as React from "react";

import { Container, Image, Menu, Segment, Sidebar, Sticky } from "semantic-ui-react";

import "../../../../style/drawer.css";
import { MenuItem } from "../../smart/Menu/MenuItem";

import {categories} from "../../../data/categories";
import { ICategory } from "../../../model/Category";
import { SubMenu } from "../../smart/Menu/SubMenu";
const myImage = require("../../../assets/placeholder_350x150.png");

export interface IDrawerMenuState { visible: boolean; current: ICategory; }

export class DrawerMenu extends React.Component<{}, IDrawerMenuState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            current: undefined,
            visible: true,
        };
    }

    onHover = (cname: ICategory) => {
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
                            <MenuItem   category={x}
                                        key={i}
                                        onHover={this.onHover}/>
                                    )}
                </Sidebar>
                <Sidebar.Pusher>
                    <Segment basic className="mainContainer">
                        {this.state.current === undefined ? "" :
                            <SubMenu category={this.state.current} onHover={this.onHover} />}
                        <Container className="content">
                            <button onClick={() => this.setState({visible: !this.state.visible})}>go</button>
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                            <Image src={myImage} />
                        </Container>
                    </Segment>
                </Sidebar.Pusher>
                </Sidebar.Pushable>
            </div>
        );
    }
}
