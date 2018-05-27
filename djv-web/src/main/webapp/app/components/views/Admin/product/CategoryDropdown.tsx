import * as React from "react";

import { Cascader } from "antd";
import { CascaderOptionType } from "antd/lib/cascader";
import { CategoryTree } from "../../../../model/CategoryTree";

import "../../../../../style/admin/products.css";

export interface CategoryDropdownProps {
    categories: CategoryTree[];
    onChange: (n?: number) => void;
    selected?: number;
}

const mapToOption = (c: CategoryTree): CascaderOptionType => ({
    children: c.children ? c.children.map(mapToOption) : [],
    label: c.category.name,
    value: c.category.id.toString()
});

const buildDefaultValue = (categories: CategoryTree[], id?: number): string[] => {
    if (id === undefined) return [];
    const result = categories.filter(x => x.category.id === id || buildDefaultValue(x.children, id).length !== 0)[0];
    if (result === undefined) return [];
    if (result.category.id === id) return [id.toString()];
    const res = [result.category.id.toString(), ...buildDefaultValue(result.children, id)];
    return res;
};

export const CategoryDropdown = (props: CategoryDropdownProps) => (
    <Cascader className="categoryDropdown"
        onChange={values => values.length === 0 ? undefined : props.onChange(+values[values.length-1])}
        options={props.categories.map(mapToOption)}
        placeholder="Select category"
        value={buildDefaultValue(props.categories, props.selected)}
    />
);
