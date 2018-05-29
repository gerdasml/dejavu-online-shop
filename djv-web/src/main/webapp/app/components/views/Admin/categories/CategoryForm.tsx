import * as React from "react";

import { Button, Col, Input, Modal, Popconfirm, Row } from "antd";

import { Category } from "../../../../model/Category";
import { CategoryProperty } from "../../../../model/CategoryProperties";
import { CategoryPropertiesTable } from "./CategoryPropertiesTable";

import { Icon, SemanticICONS } from "semantic-ui-react";
import { IconSelect } from "./IconSelect";

import "../../../../../style/admin/category.css";

interface CategoryFormProps {
    category: Category;
    onSave: (category: Category) => void;
    onDelete: (id: number) => void;
}

interface CategoryFormState {
    name?: string;
    icon?: string;
    properties?: CategoryProperty[];
    isModalVisible: boolean;
}

export class CategoryForm extends React.Component<CategoryFormProps, CategoryFormState> {
    state: CategoryFormState = {
        isModalVisible: false
    };

    componentWillReceiveProps (nextProps: CategoryFormProps) {
        if(nextProps.category) {
            this.setState({
                icon: nextProps.category.icon,
                isModalVisible: false,
                name: nextProps.category.name,
                properties: nextProps.category.properties,
            });
        }
    }

    isValid () {
        return this.state.name;
    }

    buildCategory = (): Category => ({
        ...this.props.category,
        icon: this.state.icon,
        name: this.state.name,
        properties: this.state.properties,
    })

    render () {
        return (
            <div className="categoryForm">
                <Row type="flex" align="middle">
                    <Col span={6}><h3>Category name:</h3></Col>
                    <Col span={18}>
                        <Input className="categoryNameInput"
                            size="large"
                            placeholder="Category name"
                            value={this.state.name}
                            onChange={e => this.setState({...this.state, name: e.target.value})}
                        />
                    </Col>
                </Row>
                <Row type="flex" align="middle">
                    <Col span={6}><h3>Icon:</h3></Col>
                    <Col span={18}>
                        <Button onClick={() => this.setState({...this.state, isModalVisible: true})}
                        className="selectButton">
                            {this.state.icon?
                            <Icon name={this.state.icon as SemanticICONS} />
                            : "Select..."}
                        </Button>
                    </Col>
                </Row>
                <Row>
                    <CategoryPropertiesTable
                            categoryId={this.props.category ? this.props.category.id : -1}
                            properties={this.state.properties || []}
                            onChange={properties => this.setState({
                                ...this.state,
                                properties,
                            })}/>
                </Row>
                <Row type="flex" align="middle">
                    <Button
                        className="categoryFormButton"
                        disabled={!this.isValid()}
                        onClick={() => this.props.onSave(this.buildCategory())}
                        type="primary"
                    >
                        Save
                    </Button>
                    { this.props.category && this.props.category.id
                    ?
                    <Popconfirm
                        title="Are you sure you want to delete this category?"
                        onConfirm={() => this.props.onDelete(this.props.category.id)}
                    >
                        <Button className="categoryFormButton">Delete category</Button>
                    </Popconfirm>
                    : ""}
                </Row>
                <Modal
                    title="Icon selection"
                    visible={this.state.isModalVisible}
                    className="modalWrapper"
                    style={{top: 0}}
                    footer={[
                        <Button className="categoryFormButton"
                        onClick={() => this.setState({...this.state, isModalVisible: false})}>Cancel</Button>,
                      ]}
                >
                    <IconSelect onSelect={icon => this.setState({...this.state, icon, isModalVisible: false})} />
                </Modal>
            </div>
        );
    }
}
