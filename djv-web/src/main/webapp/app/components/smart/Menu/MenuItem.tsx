import * as React from "react";
import * as ReactDOM from "react-dom";
import { Icon, Menu, SemanticICONS } from "semantic-ui-react";
import { ICategory } from "../../../model/Category";
import { ISubMenuPosition } from "./SubMenu";

export interface IMenuItemProps {
    category: ICategory;
    onHover: (c: ICategory, p?: ISubMenuPosition) => void;
}

interface IMenuItemState { rect: ClientRect; }

export class MenuItem extends React.Component<IMenuItemProps, IMenuItemState> {
    constructor (props: IMenuItemProps) {
        super(props);
        this.state = {
            rect: undefined
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
                this.props.onHover(this.props.category, {
                    left: this.state.rect.left + this.state.rect.width,
                    top: this.state.rect.top,
                });
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
