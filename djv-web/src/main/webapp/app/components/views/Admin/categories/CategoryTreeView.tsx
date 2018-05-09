import * as React from "react";

import { Tree } from "antd";
import { Icon, SemanticICONS } from "semantic-ui-react";

import { Category } from "../../../../model/Category";
import { CategoryTree } from "../../../../model/CategoryTree";

interface CategoryTreeViewProps {
    categories: CategoryTree[];
    onCategoryMove: (category: Category, newParent?: number) => void;
}

const toTreeNode = (category: CategoryTree): any => {
    const title = category.category.name;
    const key = category.category.id;
    const children = category.children.map(toTreeNode);
    const icon = category.category.icon ? <Icon name={category.category.icon as SemanticICONS} /> : undefined;
    return (
        <Tree.TreeNode title={title} key={key} icon={icon}>
            {children}
        </Tree.TreeNode>
    );
};

const findCategoryFromTree = (tree: CategoryTree[], id: number): Category => {
    const cat: CategoryTree = tree.filter(t => t.category.id === id || findCategoryFromTree(t.children, id))[0];
    if(cat === undefined) {
        return undefined;
    }
    if (cat.category.id === id) {
        return cat.category;
    }
    return findCategoryFromTree(cat.children, id);
};

export class CategoryTreeView extends React.Component<CategoryTreeViewProps, never> {
    handleDrop (from: number, to: number, onGap?: boolean) {
        const fromCategory = findCategoryFromTree(this.props.categories, from);
        const toCategory = findCategoryFromTree(this.props.categories, to);
        this.props.onCategoryMove(fromCategory, onGap ? toCategory.parentCategoryId : toCategory.id);
    }
    render () {
        return (
            <Tree
                showIcon
                draggable
                onDrop={(info: any) =>
                    this.handleDrop(+info.dragNode.props.eventKey, +info.node.props.eventKey, info.dropToGap)}
                onSelect={info => console.log("SELECT", info)}
            >
                {this.props.categories.map(toTreeNode)}
            </Tree>
        );
    }
}
