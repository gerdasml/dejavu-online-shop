import * as React from "react";

import { Tree } from "antd";

import { CategoryTree } from "../../../../model/CategoryTree";

interface CategoryTreeViewProps {
    categories: CategoryTree[];
}

const toTreeNode = (category: CategoryTree): any => {
    const title = `${category.category.name} (${category.category.id})`;
    const key = category.category.id;
    const children = category.children.map(toTreeNode);
    return (
        <Tree.TreeNode title={title} key={key}>
            {children}
        </Tree.TreeNode>
    );
};

export const CategoryTreeView = (props: CategoryTreeViewProps) => (
    <Tree
        draggable
        onDragEnter={info => console.log("START", info)}
        onDrop={(info: any) => console.log("STOP", info.node.props.eventKey, " -> ", info.dragNode.props.eventKey, info.node.props.pos, info.dropPosition, info.dropToGap)}
        onSelect={info => console.log("SELECT", info)}
    >
        {props.categories.map(toTreeNode)}
    </Tree>
);
