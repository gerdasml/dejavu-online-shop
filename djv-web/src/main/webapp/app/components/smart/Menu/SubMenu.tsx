import * as React from "react";
import { Divider } from "semantic-ui-react";

import "../../../../style/submenu.css";
import { Category } from "../../../model/Category";

export interface SubMenuPosition { top: number; left: number; }
interface SubMenuProps {
    onHover: (c: Category, p?: SubMenuPosition) => void;
    category: Category;
    position: SubMenuPosition;
}

const mapChildChild = (category: Category, key: number) =>
    category.children.map((x, i) =>
    <div
        className="item"
        key={key.toString()+"_"+i.toString()}>
            {x.displayName}
    </div>
    );

const mapChild = (category: Category) =>
    category.children.map((x, i) =>
        [<div
            className="item main"
            key={i}
        >
            {x.displayName}
                <Divider fitted className="divider"/>
        </div>]
        .concat(mapChildChild(x,i))
    );

export const SubMenu = (props: SubMenuProps) => (
    <div
        className="submenu"
        onMouseEnter={() => props.onHover(props.category, props.position)}
        onMouseLeave={() => props.onHover(undefined)}
        style={{top: props.position.top, left: props.position.left}}
    >
        <div className="item category-name">
            {props.category.displayName}
        </div>
        {mapChild(props.category)}
    </div>
);
