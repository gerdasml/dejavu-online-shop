import * as React from "react";

import { Col, message, notification, Row, Spin } from "antd";

import * as api from "../../../../api";

import { Category } from "../../../../model/Category";
import { CategoryTree } from "../../../../model/CategoryTree";
import { CategoryTreeView } from "./CategoryTreeView";
import { CategoryForm } from "./CategoryForm";

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
                        <CategoryForm category={this.state.selectedCategory} onSave={()=>{}} onDelete={() => {}} />
                    </Col>
                </Row>
            </Spin>
        );
    }
}
