import * as React from "react";

import { notification, Spin } from "antd";

import * as api from "../../../../api";

import { CategoryTree } from "../../../../model/CategoryTree";
import { CategoryTreeView } from "./CategoryTreeView";

interface CategoriesState {
    categories: CategoryTree[];
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

    render () {
        return (
            <Spin spinning={this.state.categories.length === 0}>
                <CategoryTreeView categories={this.state.categories} />
            </Spin>
        );
    }
}
