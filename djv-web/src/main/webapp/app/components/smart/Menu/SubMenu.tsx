import * as React from "react";

import "../../../../style/submenu.css";
import { ICategory } from "../../../model/Category";

interface ISubMenuProps { onHover: (c: ICategory) => void; category: ICategory; }

const mapChildChild = (category: ICategory, key: number) =>
    category.children.map((x, i) =>
    <div
        className="item"
        key={key.toString()+"_"+i.toString()}>
            {x.displayName}
    </div>
    );

const mapChild = (category: ICategory) =>
    category.children.map((x, i) =>
        [<div
            className="item main"
            key={i}
        >
            {x.displayName}
        </div>]
        .concat(mapChildChild(x,i))
    );

export const SubMenu = (props: ISubMenuProps) => (
    <div className="submenu" onMouseEnter={() => props.onHover(props.category)}
        onMouseLeave={() => props.onHover(undefined)}>
        <div className="item main">
            {props.category.name}
        </div>
        {/* For demo purpose only */}
        {/* TODO: replace with mapping of categories */}
        {mapChild(props.category)}
    </div>
);
