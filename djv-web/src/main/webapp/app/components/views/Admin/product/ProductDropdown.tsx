import * as React from "react";

import { Button, Dropdown, Icon, Menu } from "antd";

const menu = (
    <Menu>
      <Menu.Item key="1">1st menu item</Menu.Item>
      <Menu.Item key="2">2nd menu item</Menu.Item>
      <Menu.Item key="3">3rd item</Menu.Item>
    </Menu>
  );

export const ProductDropdown = () => (
    <Dropdown overlay={menu}>
      <Button>
        Button <Icon type="down" />
      </Button>
    </Dropdown>
);
