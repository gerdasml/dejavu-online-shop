import * as React from "react";
import * as ReactDOM from "react-dom";
import { Icon, Menu, SemanticICONS } from "semantic-ui-react";
import { Category } from "../../../model/Category";
import { SubMenuPosition } from "./SubMenu";

export interface MenuItemProps {
    category: Category;
    onHover: (c: Category, p?: SubMenuPosition) => void;
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
        return (
            <Menu.Item className="menu-item" name={this.props.category.name} onMouseEnter={() => {
                this.props.onHover(this.props.category, this.getPosition());
            }}
                onMouseLeave={() => this.props.onHover(undefined)}>
                <div>
                    <Icon name={this.props.category.icon as SemanticICONS}/>
                    {this.props.category.displayName}
                </div>
            </Menu.Item>
        );
    }
}
