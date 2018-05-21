import * as React from "react";

import { Grid, Menu } from "semantic-ui-react";

import { notification } from "antd";
import "../../../../style/drawer.css";
import * as api from "../../../api";
import { CategoryTree } from "../../../model/CategoryTree";
import { MenuItem } from "../../smart/Menu/MenuItem";
import { SubMenu, SubMenuPosition } from "../../smart/Menu/SubMenu";
import { DesktopMenu } from "./DesktopMenu";
import MediaQuery from "react-responsive";
import { MobileMenu } from "./MobileMenu";

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
    getAllCategories= async () => {
        const response = await api.category.getCategoryTree();
        if(api.isError(response)) {
            notification.error({message: "Failed to fetch categories", description: response.message});
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
                            <MediaQuery query="(min-width: 1000px)">
                                <DesktopMenu categories={this.state.categories}
                                            onHover={this.onHover} />
                            </MediaQuery>
                            <MediaQuery query="(max-width: 999px)">
                                <MobileMenu categories={this.state.categories} />
                            </MediaQuery>
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
