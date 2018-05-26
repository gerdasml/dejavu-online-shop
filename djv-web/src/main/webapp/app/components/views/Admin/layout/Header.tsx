import * as React from "react";
import { NavLink, Link } from "react-router-dom";
import { Menu } from "semantic-ui-react";

import "../../../../../style/admin/adminHeader.css";

export interface MenuHeaderState {
    activeItem: String;
}

export interface MenuHeaderProps {
    onLogout: () => void;
}

export class MenuHeader extends React.Component<MenuHeaderProps, MenuHeaderState> {
  state = { activeItem: "home" };

  handleItemClick = (e: any, { name }: any ) =>
  this.setState({ activeItem: name })

  render () {
    const { activeItem } = this.state;
    return (
      <div id="adminHeader">
        <Menu pointing secondary>
            <Menu.Item
                name="orders"
                active={activeItem === "orders"}
                as={NavLink} to="/admin/orders"
                onClick={this.handleItemClick.bind(this)}
                className="menuItem"
            />
            <Menu.Item
                name="users"
                active={activeItem === "users"}
                as={NavLink} to="/admin/users"
                onClick={this.handleItemClick.bind(this)}
                className="menuItem"
            />
            <Menu.Item
                name="products"
                active={activeItem === "products"}
                as={NavLink} to="/admin/products"
                onClick={this.handleItemClick.bind(this)}
                className="menuItem"
            />
            <Menu.Item
                name="categories"
                active={activeItem === "categories"}
                as={NavLink} to="/admin/categories"
                onClick={this.handleItemClick.bind(this)}
                className="menuItem"
            />
            <Menu.Item
                name="importHistory"
                active={activeItem === "importHistory"}
                as={NavLink} to="/admin/imports"
                onClick={this.handleItemClick.bind(this)}
                className="menuItem"
            />
            <Menu.Menu position="right">
                <Menu.Item
                    name="backToShop"
                    active={false}
                    as={Link} to="/"
                    onClick={this.handleItemClick.bind(this)}
                    className="menuItem"
                />
                <Menu.Item
                    name="logout"
                    active={activeItem === "logout"}
                    onClick={this.props.onLogout}
                    className="menuItem" />
            </Menu.Menu>
        </Menu>
      </div>
    );
  }
}
