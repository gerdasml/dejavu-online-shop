import * as React from "react";

import { Button, Dropdown, Icon, Menu } from "antd";

export interface ProductDropdownProps {
  category: number;
  onChange: (n: number) => void;
}

export interface ProductDropdownState {
  category: number;
}

const menu = (
    <Menu>
      <Menu.Item key="1">1st menu item</Menu.Item>
      <Menu.Item key="2">2nd menu item</Menu.Item>
      <Menu.Item key="3">3rd item</Menu.Item>
    </Menu>
  );

export class ProductDropdown extends React.Component<ProductDropdownProps,ProductDropdownState> {
  state: ProductDropdownState = { category: this.props.category };
  handleChange (e: React.ChangeEvent<HTMLInputElement>) {
        const value = +e.target.value;
        this.setState({...this.state, category: value});
        this.props.onChange(value);
  }
  render () {
    return (
      <Dropdown overlay={menu}>
        <Button
          value={this.state.category}
          onChange={this.handleChange.bind(this)}
        >
          Button <Icon
                    type="down"/>
        </Button>
      </Dropdown>
    );
  }
}
