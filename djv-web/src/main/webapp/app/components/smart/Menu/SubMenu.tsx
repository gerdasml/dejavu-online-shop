import * as React from "react";
import { Link } from "react-router-dom";
import { Divider } from "semantic-ui-react";

import "../../../../style/submenu.css";
import { CategoryTree } from "../../../model/CategoryTree";

export interface SubMenuPosition { top: number; left: number; }
interface SubMenuProps {
    onHover: (c: CategoryTree, p?: SubMenuPosition) => void;
    categoryTree: CategoryTree;
    position: SubMenuPosition;
}

const mapChildChild = (category: CategoryTree, key: number, onItemClick: () => void) =>
    category.children.map((x, i) =>
    <div
        className="item"
        key={key.toString()+"_"+i.toString()}>
            <Link to={`/category/${x.category.identifier}`} onClick={onItemClick}>{x.category.name}</Link>
    </div>
    );

const mapChild = (categoryTree: CategoryTree, onItemClick: () => void) =>
    categoryTree.children.map((x, i) =>
        [<div
            className="item main"
            key={i}
        >
        {x.children === undefined || x.children.length === 0
        ?   <Link to={`/category/${x.category.identifier}`} onClick={onItemClick}>{x.category.name}</Link>
        :   <span>
                {x.category.name}
                <Divider fitted className="divider"/>
            </span>
        }
        </div>]
        .concat(mapChildChild(x,i, onItemClick))
    );

export const SubMenu = (props: SubMenuProps) => (
    <div
        className="submenu"
        onMouseEnter={() => props.onHover(props.categoryTree, props.position)}
        onMouseLeave={() => props.onHover(undefined)}
        style={{top: props.position.top, left: props.position.left}}
    >
        <div className="item category-name">
            {props.categoryTree.category.name}
        </div>
        {mapChild(props.categoryTree, () => props.onHover(undefined))}
    </div>
);
