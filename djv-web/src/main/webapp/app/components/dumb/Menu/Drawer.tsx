import * as React from "react";

import { Grid, Menu, Message } from "semantic-ui-react";

import "../../../../style/drawer.css";
import * as api from "../../../api";
import { CategoryTree } from "../../../model/CategoryTree";
import { MenuItem } from "../../smart/Menu/MenuItem";
import { SubMenu, SubMenuPosition } from "../../smart/Menu/SubMenu";

interface CategorySettings {
    categoryTree: CategoryTree;
    position: SubMenuPosition;
}
export interface DrawerMenuState {
    visible: boolean;
    current: CategorySettings;
    categories: CategoryTree[];
}

export class DrawerMenu extends React.Component<{}, DrawerMenuState> {
    constructor (props: {}) {
        super(props);
        this.state = {
            categories: [],
            current: undefined,
            visible: true
        };
    }

    onHover = (cat: CategoryTree, pos?: SubMenuPosition) => {
        if(pos === undefined) {
            this.setState({
                ...this.state,
                current: undefined
            });
        } else {
            this.setState({
                ...this.state,
                current: {
                    categoryTree: cat,
                    position: {
                        left: pos.left,
                        top: pos.top,
                    }
                }
            });
        }
    }
    async getAllCategories () {
        const response = await api.category.getCategoryTree();
        if(api.isError(response)) {
            console.error(response);
            return [];
        }
        return response;
    }
    async componentWillMount () {
        const newCategories = await this.getAllCategories();
        this.setState({...this.state, categories: newCategories});
    }
    render () {
        return (
            <div>
                <Grid id="allPageContent">
                    <Grid.Row id="drawerRow">
                        <Grid.Column width={2} id="sidebar" stretched>
                            <Menu vertical fluid inverted id="sidebarItems">
                                {this.state.categories.map((itemCategory, itemIndex) =>
                                    <MenuItem
                                                categoryTree={itemCategory}
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
                            categoryTree={this.state.current.categoryTree}
                            onHover={this.onHover} />}
            </div>
        );
    }
}
