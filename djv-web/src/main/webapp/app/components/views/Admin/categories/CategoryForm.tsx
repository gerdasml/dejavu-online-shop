import * as React from "react";

import { Button, Col, Input, Modal, Popconfirm, Row } from "antd";

import { Category } from "../../../../model/Category";

import { Icon, SemanticICONS } from "semantic-ui-react";
import { toUrlFriendlyString } from "../../../../utils/common";
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
    isModalVisible: boolean;
}

export class CategoryForm extends React.Component<CategoryFormProps, CategoryFormState> {
    state: CategoryFormState = {
        isModalVisible: false
    };

    componentWillReceiveProps (nextProps: CategoryFormProps) {
        if(nextProps.category) {
            this.setState({name: nextProps.category.name, icon: nextProps.category.icon, isModalVisible: false});
        }
    }

    isValid () {
        return this.state.name && this.state.icon;
    }

    buildCategory = (): Category => ({
        ...this.props.category,
        icon: this.state.icon,
        identifier: toUrlFriendlyString(this.state.name),
        name: this.state.name,
    })

    render () {
        return (
            <div>
                <Row type="flex" align="middle">
                    <Col span={6}><h3>Category name:</h3></Col>
                    <Col span={18}>
                        <Input
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
                        <Button onClick={() => this.setState({...this.state, isModalVisible: true})}>
                            {this.state.icon ? <Icon name={this.state.icon as SemanticICONS} /> : "Select..."}
                        </Button>
                    </Col>
                </Row>
                <Row type="flex" align="middle">
                    <Button
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
                        <Button type="danger">Delete category</Button>
                    </Popconfirm>
                    : ""}
                </Row>
                <Modal
                    title="Icon selection"
                    visible={this.state.isModalVisible}
                    onCancel={() => this.setState({...this.state, isModalVisible: false})}
                    className="modalWrapper"
                    style={{top: 0}}
                >
                    <IconSelect onSelect={icon => this.setState({...this.state, icon, isModalVisible: false})} />
                </Modal>
            </div>
        );
    }
}