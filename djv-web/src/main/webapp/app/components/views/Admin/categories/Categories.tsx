import * as React from "react";

import { Col, message, notification, Row, Spin } from "antd";

import * as api from "../../../../api";

import { Category } from "../../../../model/Category";
import { CategoryTree } from "../../../../model/CategoryTree";
import { CategoryTreeView } from "./CategoryTreeView";
import { CategoryForm } from "./CategoryForm";
import { IconSelect } from "./IconSelect";

interface CategoriesState {
    categories: CategoryTree[];
    selectedCategory?: Category;
}

export class Categories extends React.Component<never, CategoriesState> {
    state: CategoriesState = {
        categories: []
    };

    async componentDidMount () {
        await this.loadData();
    }

    async loadData () {
        const response = await api.category.getCategoryTree();
        if (api.isError(response)) {
            notification.error({message: "Failed to fetch category data", description: response.message});
            return;
        }
        this.setState({categories: response});
    }

    async handleParentUpdate (category: Category, newParentId?: number) {
        const {id, ...newCategory} = category;
        newCategory.parentCategoryId = newParentId;
        const response = await api.category.updateCategory(category.id, newCategory);
        if (api.isError(response)) {
            notification.error({message: "Failed to update category data", description: response.message});
            return;
        }
        message.success("Successfully moved category");
        await this.loadData();
    }

    async handleSave (category: Category) {
        const {id, ...newCategory} = category;
        const response = await api.category.updateCategory(id, newCategory);
        if (api.isError(response)) {
            notification.error({ message: "Failed to update category data", description: response.message});
            return;
        }
        message.success("Successfully updated category");
        await this.loadData();
    }

    async handleDelete (id: number) {
        const response = await api.category.deleteCategory(id);
        if (api.isError(response)) {
            notification.error({ message: "Failed to delete category", description: response.message});
            return;
        }
        message.success("Successfully removed category");
        this.setState({...this.state, selectedCategory: undefined});
        await this.loadData();
    }

    render () {
        return (
            <Spin spinning={this.state.categories.length === 0}>
                <Row>
                    <Col span={10}>
                        <CategoryTreeView
                            categories={this.state.categories}
                            onCategoryMove={(cat, parent) => this.handleParentUpdate(cat, parent)}
                            onSelect={cat => this.setState({...this.state, selectedCategory: cat})}
                        />
                    </Col>
                    <Col span={this.state.selectedCategory ? 14 : 0}>
                        <CategoryForm
                            category={this.state.selectedCategory}
                            onSave={this.handleSave.bind(this)}
                            onDelete={this.handleDelete.bind(this)} />
                    </Col>
                </Row>
            </Spin>
        );
    }
}
