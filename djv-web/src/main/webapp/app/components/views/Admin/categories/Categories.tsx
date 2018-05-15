import * as React from "react";

import { Button, Col, message, notification, Row, Spin } from "antd";

import * as api from "../../../../api";

import { Category } from "../../../../model/Category";
import { CategoryTree } from "../../../../model/CategoryTree";
import { CategoryForm } from "./CategoryForm";
import { CategoryPropertiesTable } from "./CategoryPropertiesTable";
import { CategoryTreeView } from "./CategoryTreeView";

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
        let response;
        let errorMessage;
        let successMessage;
        if (id) {
            response = await api.category.updateCategory(id, newCategory);
            errorMessage = "Failed to update category data";
            successMessage = "Successfully updated category";
        } else {
            response = await api.category.createCategory(newCategory);
            errorMessage = "Failed to create category";
            successMessage = "Successfully created category";
        }

        if (api.isError(response)) {
            notification.error({ message: errorMessage, description: response.message});
            return;
        }
        message.success(successMessage);
        this.setState({...this.state, selectedCategory: category});
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
                        <Button
                            icon="plus"
                            onClick={() => this.setState({
                                ...this.state,
                                selectedCategory: {
                                    icon: undefined,
                                    identifier: undefined,
                                    name: undefined,
                                }
                            })}
                        >
                            Add category
                        </Button>
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
                        <CategoryPropertiesTable
                            properties={this.state.selectedCategory?this.state.selectedCategory.properties: []}
                            onChange={(x => {console.log(x)})}/>
                    </Col>
                </Row>
            </Spin>
        );
    }
}
