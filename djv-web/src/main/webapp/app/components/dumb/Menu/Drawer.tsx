import * as React from "react";

import { Container, Menu, Segment, Sidebar } from "semantic-ui-react";

import "../../../../style/drawer.css";
import { MenuItem } from "../../smart/Menu/MenuItem";

import {categories} from "../../../data/categories";
import { ICategory } from "../../../model/Category";
import { ISubMenuPosition, SubMenu } from "../../smart/Menu/SubMenu";

interface ICategorySettings {
    category: ICategory;
    position: ISubMenuPosition;
}
export interface IDrawerMenuState {
    visible: boolean;
    current: ICategorySettings;
}

export class DrawerMenu extends React.Component<{}, IDrawerMenuState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            current: undefined,
            visible: true
        };
    }

    onHover = (cat: ICategory, pos?: ISubMenuPosition) => {
        if(pos === undefined) {
            this.setState({
                ...this.state,
                current: undefined
            });
        } else {
            this.setState({
                ...this.state,
                current: {
                    category: cat,
                    position: {
                        left: pos.left,
                        top: pos.top,
                    }
                }
            });
        }
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
                            <MenuItem
                                        category={x}
                                        key={i}
                                        onHover={this.onHover}/>
                                    )}
                </Sidebar>
                <Sidebar.Pusher>
                    <Segment basic className="mainContainer">
                        <Container className="content">
                            <button onClick={() => this.setState({visible: !this.state.visible})}>go</button>
                            {this.props.children}
                        </Container>
                    </Segment>
                </Sidebar.Pusher>
                </Sidebar.Pushable>
                {this.state.current === undefined ? "" :
                    <SubMenu
                        position={this.state.current.position}
                        category={this.state.current.category}
                        onHover={this.onHover} />}
            </div>
        );
    }
}
