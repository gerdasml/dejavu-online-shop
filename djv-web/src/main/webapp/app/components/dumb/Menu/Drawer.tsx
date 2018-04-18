import * as React from "react";

import { Grid, Menu } from "semantic-ui-react";

import "../../../../style/drawer.css";
import { MenuItem } from "../../smart/Menu/MenuItem";

import {categories} from "../../../data/categories";
import { Category } from "../../../model/Category";
import { SubMenuPosition, SubMenu } from "../../smart/Menu/SubMenu";

interface CategorySettings {
    category: Category;
    position: SubMenuPosition;
}
export interface DrawerMenuState {
    visible: boolean;
    current: CategorySettings;
}

export class DrawerMenu extends React.Component<{}, DrawerMenuState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            current: undefined,
            visible: true
        };
    }

    onHover = (cat: Category, pos?: SubMenuPosition) => {
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
