import * as React from "react";
import { NavLink } from "react-router-dom";
import { Menu } from "semantic-ui-react";

export interface MenuHeaderState {
    activeItem: String;
}

export class MenuHeader extends React.Component<{}, MenuHeaderState> {
  state = { activeItem: "home" };

  handleItemClick = (e: any, { name }: any ) =>
  this.setState({ activeItem: name })

  render () {
    const { activeItem } = this.state;
    return (
      <div>
        <Menu pointing secondary>
            <Menu.Item
                name="orders"
                active={activeItem === "orders"}
                as={NavLink} to="orders"
                onClick={this.handleItemClick.bind(this)} />
            <Menu.Item
                name="users"
                active={activeItem === "users"}
                as={NavLink} to="users"
                onClick={this.handleItemClick.bind(this)} />
            <Menu.Item
                name="products"
                active={activeItem === "products"}
                as={NavLink} to="product"
                onClick={this.handleItemClick.bind(this)} />
            <Menu.Item
                name="categories"
                active={activeItem === "categories"}
                as={NavLink} to="categories"
                onClick={this.handleItemClick.bind(this)}/>
            <Menu.Menu position="right">
                <Menu.Item
                    name="logout"
                    active={activeItem === "logout"}
                    onClick={this.handleItemClick.bind(this)} />
            </Menu.Menu>
        </Menu>
      </div>
    );
  }
}
