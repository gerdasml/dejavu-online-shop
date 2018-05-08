import * as React from "react";

import { Button, Cascader, Icon, Select } from "antd";
import { CascaderOptionType } from "antd/lib/cascader";
import { Category } from "../../../../model/Category";
import { CategoryTree } from "../../../../model/CategoryTree";

export interface ProductDropdownProps {
  categories: CategoryTree[];
  onChange: (n?: number) => void;
}

const mapToOption = (c: CategoryTree): CascaderOptionType => ({
    children: c.children ? c.children.map(mapToOption) : [],
    label: c.category.name,
    value: c.category.id.toString()
});

export const ProductDropdown = (props: ProductDropdownProps) => (
  <Cascader
    onChange={values => values.length === 0 ? undefined : props.onChange(+values[values.length-1])}
    //TODO: move style to css file
    options={props.categories.map(mapToOption)}
    placeholder="Select category">
  </Cascader>
);

