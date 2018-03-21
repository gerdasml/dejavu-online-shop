import * as React from "react";

import "../../../../style/submenu.css";

interface ISubMenuProps { onHover: Function; category: string; }

export const SubMenu = (props: ISubMenuProps) => (
    <div className="submenu" onMouseEnter={() => props.onHover(props.category)}
        onMouseLeave={() => props.onHover(undefined)}>
        <div className="item main">
            {props.category}
        </div>
        {/* For demo purpose only */}
        {/* TODO: replace with mapping of categories */}
        <div className="item">
            Def
        </div>
        <div className="item">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
        <div className="item main">
            Def
        </div>
    </div>
);
