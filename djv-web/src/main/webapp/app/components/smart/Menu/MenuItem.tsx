import * as React from "react";
import * as ReactDOM from "react-dom";
import { Link } from "react-router-dom";
import { Icon, Menu, SemanticICONS } from "semantic-ui-react";
import { CategoryTree } from "../../../model/CategoryTree";
import { SubMenuPosition } from "./SubMenu";

export interface MenuItemProps {
    categoryTree: CategoryTree;
    onHover: (c: CategoryTree, p?: SubMenuPosition) => void;
}

export class MenuItem extends React.Component<MenuItemProps, never> {
    constructor (props: MenuItemProps) {
        super(props);
    }

    getPosition () {
        const rect = ReactDOM.findDOMNode(this)
                            .getBoundingClientRect();
        return {
            left: rect.left + window.pageXOffset + rect.width,
            top: rect.top + window.pageYOffset,
        };
    }

    render () {
        if (this.props.categoryTree.children === undefined ||
            this.props.categoryTree.children.length === 0) {
            return (
                <Link to={`/category/${this.props.categoryTree.category.identifier}`}>
                    <Menu.Item
                        className="menu-item"
                        name={this.props.categoryTree.category.identifier}
                    >
                        <div>
                            <Icon name={this.props.categoryTree.category.icon as SemanticICONS}/>
                            {this.props.categoryTree.category.name}
                        </div>
                    </Menu.Item>
                </Link>
            );
        } else {
            return (
                <Menu.Item
                    className="menu-item"
                    name={this.props.categoryTree.category.identifier}
                    onMouseEnter={() => { this.props.onHover(this.props.categoryTree, this.getPosition()); }}
                    onMouseLeave={() => this.props.onHover(undefined)}>
                    <div>
                        <Icon name={this.props.categoryTree.category.icon as SemanticICONS}/>
                        {this.props.categoryTree.category.name}
                    </div>
                </Menu.Item>
            );
        }
    }
}
