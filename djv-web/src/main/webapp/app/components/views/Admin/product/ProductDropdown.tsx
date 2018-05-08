import * as React from "react";

import { Button, Icon, Select } from "antd";
import { Category } from "../../../../model/Category";

export interface ProductDropdownProps {
  categories: Category[];
  onChange: (n: number) => void;
}

export const ProductDropdown = (props: ProductDropdownProps) => (
  <Select
    onChange={props.onChange}
    placeholder="Select a category"
    //TODO: move style to css file
    style={{ width: 200 }}>
    {props.categories.map(x =>
      <Select.Option value={x.id}>
        {x.name}
      </Select.Option>)}
  </Select>
);

