import * as React from "react";
import * as ReactDOM from "react-dom";
import { Icon, Menu, SemanticICONS } from "semantic-ui-react";
import { Category } from "../../../model/Category";
import { SubMenuPosition } from "./SubMenu";

export interface MenuItemProps {
    category: Category;
    onHover: (c: Category, p?: SubMenuPosition) => void;
}

interface MenuItemState { rect: ClientRect; }

export class MenuItem extends React.Component<MenuItemProps, MenuItemState> {
    constructor (props: MenuItemProps) {
        super(props);
        this.state = {
            rect: undefined
        };
    }

    getPosition () {
        return {
            left: this.state.rect.left + this.state.rect.width,
            top: this.state.rect.top,
        };
    }

    componentDidMount () {
        const rect = ReactDOM.findDOMNode(this)
                            .getBoundingClientRect();
        this.setState({rect});
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
