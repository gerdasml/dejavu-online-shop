import * as React from "react";
import { CategoryTree } from "../../../model/CategoryTree";
import { SubMenuPosition } from "../../smart/Menu/SubMenu";
import { Menu } from "semantic-ui-react";
import { MenuItem } from "../../smart/Menu/MenuItem";

interface DesktopMenuProps {
    categories: CategoryTree[];
    onHover: (cat: CategoryTree, pos?: SubMenuPosition) => void;
}

export const DesktopMenu = (props: DesktopMenuProps) => (
    <Menu vertical fluid inverted id="sidebarItems">
        {props.categories.map((itemCategory, itemIndex) =>
            <MenuItem
                        categoryTree={itemCategory}
                        key={itemIndex}
                        onHover={props.onHover}/>
                    )}
    </Menu>
);
