import * as React from "react";

import { Button, Form, Input } from "antd";
import { FormComponentProps } from "antd/lib/form/Form";

import { Category } from "../../../../model/Category";

import { toUrlFriendlyString } from "../../../../utils/common";

interface CategoryFormProps {
    category: Category;
    onSave: (category: Category) => void;
    onDelete: (id: number) => void;
}

class CategoryFormInner extends React.Component<CategoryFormProps & FormComponentProps, never> {
    handleSubmit (e: any) {
        e.preventDefault();
        const props = this.props;
        this.props.form.validateFields((err, values) => {
            if (!err) {
                const cat: Category = {
                    icon: values.icon,
                    id: props.category.id,
                    identifier: toUrlFriendlyString(values.name),
                    name: values.name,
                    parentCategoryId: props.category.parentCategoryId,
                };
                props.onSave(cat);
            }
        });
    }
    render () {
        const { getFieldDecorator } = this.props.form;
        return (
            <Form onSubmit={this.handleSubmit.bind(this)}>
                <Form.Item>
                    {getFieldDecorator("name", {
                        rules: [{required: true, message: "Please enter a category name"}]
                    })(
                        <Input placeholder="Category name" />
                    )}
                </Form.Item>
                <Form.Item>
                    {getFieldDecorator("icon", {
                        rules: [{required: true, message: "Please choose an icon"}]
                    })(
                        <Input placeholder="Icon" />
                    )}
                </Form.Item>
                <Button type="primary" htmlType="submit">Save</Button>
            </Form>
        );
    }
}

export const CategoryForm = Form.create<CategoryFormProps>()(CategoryFormInner);
