import * as React from "react";

import { Grid, Menu } from "semantic-ui-react";

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
                <Grid id="allPageContent">
                    <Grid.Row stretched id="drawerRow">
                        <Grid.Column width={2} id="sidebar">
                            <Menu vertical fluid inverted id="sidebarItems">
                                {categories.map((itemCategory, itemIndex) =>
                                    <MenuItem
                                                category={itemCategory}
                                                key={itemIndex}
                                                onHover={this.onHover}/>
                                            )}
                            </Menu>
                        </Grid.Column>
                        <Grid.Column width={14} id="mainContent">
                        {this.props.children}
                        </Grid.Column>
                    </Grid.Row>
                </Grid>
                {this.state.current === undefined ? "" :
                        <SubMenu
                            position={this.state.current.position}
                            category={this.state.current.category}
                            onHover={this.onHover} />}
            </div>
        );
    }
}
