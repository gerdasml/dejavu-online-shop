import * as React from "react";
import { CategoryTree } from "../../../model/CategoryTree";
import { Menu } from "antd";
import { Icon, SemanticICONS } from "semantic-ui-react";
import { Link } from "react-router-dom";

interface MobileMenuProps {
    categories: CategoryTree[];
}

const categoryToMenu = (category: CategoryTree, index: number, indexPrefix: string): any => {
    const key = indexPrefix + "_" + index;
    if(category.children.length === 0) {
        return (
                <Menu.Item key={key}>
                    <Link to={`/category/${category.category.identifier}`}>
                        <Icon name={category.category.icon as SemanticICONS} />
                        <span>{category.category.name}</span>
                    </Link>
                </Menu.Item>
            );
    } else {
        return (
            <Menu.SubMenu
                key={key}
                title={
                    <span>
                        <Icon name={category.category.icon as SemanticICONS} />
                        <span>
                            {category.category.name}
                        </span>
                    </span>
                }>
                {category.children.map((c, i) => categoryToMenu(c, i, key))}
            </Menu.SubMenu>
        );
    }
};

export const MobileMenu = (props: MobileMenuProps) => (
        <Menu
          mode="inline"
          theme="dark"
        >
            <Menu.SubMenu id="mobile-menu-title" title={
                    <span>
                        <Icon name="bars" />
                        <span>
                            Menu
                        </span>
                    </span>
                }>
                {props.categories.map((c, i) => categoryToMenu(c, i, ""))}
            </Menu.SubMenu>
        </Menu>
);
