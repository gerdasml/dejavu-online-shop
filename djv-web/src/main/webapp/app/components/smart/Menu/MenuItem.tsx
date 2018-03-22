import * as React from "react";
import { Icon, Menu, SemanticICONS } from "semantic-ui-react";
import { ICategory } from "../../../model/Category";

export interface IMenuItemProps { category: ICategory; onHover: (c: ICategory) => void; }

export class MenuItem extends React.Component<IMenuItemProps, {}> {
    constructor (props: IMenuItemProps) {
        super(props);
    }
    render () {
        return (
            <Menu.Item name={this.props.category.name} onMouseEnter={() => this.props.onHover(this.props.category)}
                onMouseLeave={() => this.props.onHover(undefined)}>
                <div>
                    <Icon name={this.props.category.icon as SemanticICONS}/>
                    {this.props.category.displayName}
                </div>
            </Menu.Item>
        );
    }
}
