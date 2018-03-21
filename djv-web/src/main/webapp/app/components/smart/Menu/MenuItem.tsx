import * as React from "react";
import { Icon, Image, Menu, SemanticICONS} from "semantic-ui-react";

export interface IMenuItemProps { name: string; icon: string; itemName: string; onHover: Function;}

export class MenuItem extends React.Component<IMenuItemProps, {}> {
    constructor (props: IMenuItemProps) {
        super(props);
    }
    render () {
        return (
            <Menu.Item name={this.props.name} onMouseEnter={() => this.props.onHover(this.props.name)}
                onMouseLeave={() => this.props.onHover(undefined)}>
                <div>
                    <Icon name={this.props.icon as SemanticICONS}/>
                    {this.props.itemName}
                </div>
            </Menu.Item>
        );
    }
}
