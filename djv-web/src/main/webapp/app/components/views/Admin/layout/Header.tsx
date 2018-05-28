import * as React from "react";
import { NavLink, Link } from "react-router-dom";
import { Menu } from "semantic-ui-react";
import MediaQuery from "react-responsive";
import { Icon } from "antd";

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
        <div>
            <MediaQuery query="(min-width: 700px)">
                <Menu pointing secondary className="adminHeader">
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
                    <Menu.Item
                        name="discounts"
                        active={activeItem === "discounts"}
                        as={NavLink} to="/admin/discounts"
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
                            className="menuItem"/>
                    </Menu.Menu>
                </Menu>
            </MediaQuery>
            <MediaQuery query="(max-width: 699px)">
                <Menu pointing secondary className="adminHeader">
                    <Menu.Item
                        name="orders"
                        active={activeItem === "orders"}
                        as={NavLink} to="/admin/orders"
                        onClick={this.handleItemClick.bind(this)}
                        className="menuItem">
                        <Icon type="shopping-cart"/>
                    </Menu.Item>
                    <Menu.Item
                        name="users"
                        active={activeItem === "users"}
                        as={NavLink} to="/admin/users"
                        onClick={this.handleItemClick.bind(this)}
                        className="menuItem">
                        <Icon type="team"/>
                    </Menu.Item>
                    <Menu.Item
                        name="products"
                        active={activeItem === "products"}
                        as={NavLink} to="/admin/products"
                        onClick={this.handleItemClick.bind(this)}
                        className="menuItem">
                        <Icon type="barcode"/>
                    </Menu.Item>
                    <Menu.Item
                        name="categories"
                        active={activeItem === "categories"}
                        as={NavLink} to="/admin/categories"
                        onClick={this.handleItemClick.bind(this)}
                        className="menuItem">
                        <Icon type="bars"/>
                    </Menu.Item>
                    <Menu.Item
                        name="importHistory"
                        active={activeItem === "importHistory"}
                        as={NavLink} to="/admin/imports"
                        onClick={this.handleItemClick.bind(this)}
                        className="menuItem">
                        <Icon type="file-excel"/>
                    </Menu.Item>
                    <Menu.Item
                        name="discounts"
                        active={activeItem === "discounts"}
                        as={NavLink} to="/admin/discounts"
                        onClick={this.handleItemClick.bind(this)}
                        className="menuItem">
                        <Icon type="tag-o" />
                    </Menu.Item>
                    <Menu.Menu position="right">
                        <Menu.Item
                            name="backToShop"
                            active={false}
                            as={Link} to="/"
                            onClick={this.handleItemClick.bind(this)}
                            className="menuItem">
                            <Icon type="home"/>
                        </Menu.Item>
                        <Menu.Item
                            name="logout"
                            active={activeItem === "logout"}
                            onClick={this.props.onLogout}
                            className="menuItem">
                            <Icon type="logout"/>
                        </Menu.Item>
                    </Menu.Menu>
                </Menu>
            </MediaQuery>
        </div>
    );
  }
}
