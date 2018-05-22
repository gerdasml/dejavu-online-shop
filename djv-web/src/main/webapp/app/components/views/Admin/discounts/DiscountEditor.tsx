import * as React from "react";

import { Dropdown, Button, Icon, Menu, DatePicker, InputNumber, notification } from "antd";

import * as api from "../../../../api";

import { RangePickerValue } from "antd/lib/date-picker/interface";
import { CategoryDropdown } from "../product/CategoryDropdown";
import { Category } from "../../../../model/Category";
import { CategoryTree } from "../../../../model/CategoryTree";
import { timingSafeEqual } from "crypto";
import { Product } from "../../../../model/Product";
import { DiscountTarget, DiscountType, Discount } from "../../../../model/Discount";

interface DiscountEditorProps {
    discount?: Discount;
    onSubmit?: () => void;
}

interface DiscountEditorState {
    discountTarget?: DiscountTarget;
    discountType?: DiscountType;
    dateStart?: string;
    dateEnd?: string;
    discountValue: number;
    categories: CategoryTree[];
    category?: number;
    products: Product[];
}

export class DiscountEditor extends React.Component <DiscountEditorProps, DiscountEditorState> {
    state: DiscountEditorState = {
        categories: [],
        discountTarget: undefined,
        discountType: undefined,
        discountValue: 0,
        products: [],
    };

    discountTargetMenu = (
        <Menu onClick={e => this.updateDiscountTarget(this.DiscountTargetFromString(e.key))}>
          <Menu.Item key={DiscountTarget.EVERYTHING}>{DiscountTarget.EVERYTHING}</Menu.Item>
          <Menu.Item key={DiscountTarget.CATEGORY}>{DiscountTarget.CATEGORY}</Menu.Item>
          <Menu.Item key={DiscountTarget.PRODUCT}>{DiscountTarget.PRODUCT}</Menu.Item>
        </Menu>
    );

    discountTypeMenu = (
        <Menu onClick={e => this.updateDiscountType(this.DiscountTypeFromString(e.key))}>
          <Menu.Item key={DiscountType.ABSOLUTE}>{DiscountType.ABSOLUTE}</Menu.Item>
          <Menu.Item key={DiscountType.PERCENTAGE}>{DiscountType.PERCENTAGE}</Menu.Item>
        </Menu>
    );

    async componentWillMount () {
        const categories = await api.category.getCategoryTree();
        if(api.isError(categories)) {
            notification.error({message: "Failed to fetch category data", description: categories.message});
            return;
        }
        this.setState({
            ...this.state,
            categories,
        });
    }

    componentWillReceiveProps (props: DiscountEditorProps) {
        const newState = this.state;
        newState.discountTarget = props.discount.targetType;
        newState.discountType = props.discount.type;
        newState.dateStart = props.discount.activeFrom.toLocaleDateString();
        newState.dateEnd = props.discount.activeTo.toLocaleDateString();
        newState.discountValue = props.discount.value;
        this.setState(newState);
    }

    async getProductsForCategory (category: number) {
        const categoryProducts = await api.product.getProductsByCategory(category);
        if(api.isError(categoryProducts)) {
            notification.error({message: "Failed to fetch products data", description: categoryProducts.message});
            return;
        }
        this.setState({
            ...this.state,
            category,
            products: categoryProducts,
        });
    }

    updateDiscountTarget (newDiscountTarget: DiscountTarget) {
        this.setState({
            ...this.state,
            discountTarget: newDiscountTarget
        });
        // console.log(this.state.dateStart, this.state.dateEnd);
    }

    updateDiscountType (newDiscountType: DiscountType) {
        this.setState({
            ...this.state,
            discountType: newDiscountType,
            discountValue: 0,
        });
        // console.log(this.state.dateStart, this.state.dateEnd);
    }

    DiscountTargetFromString = (val: string) => DiscountTarget[val.toUpperCase() as keyof typeof DiscountTarget];

    DiscountTypeFromString = (val: string) => DiscountType[val.toUpperCase() as keyof typeof DiscountType];

    updateDate (date: RangePickerValue, dateString: [string, string]) {
        // console.log(date, dateString);
        this.setState({
            ...this.state,
            dateStart: dateString[0],
            dateEnd: dateString[1],
        });
    }

    render () {
        return (
            <div>
                <Dropdown overlay={this.discountTargetMenu}>
                    <Button style={{ marginLeft: 0 }}>
                        { this.state.discountTarget === undefined
                        ? "Discount target"
                        : this.state.discountTarget
                        }
                        <Icon type="down" />
                    </Button>
                </Dropdown>
                <DatePicker.RangePicker onChange={this.updateDate.bind(this)} />
                <Dropdown overlay={this.discountTypeMenu}>
                    <Button style={{ marginLeft: 0 }}>
                        { this.state.discountType === undefined
                        ? "Discount type"
                        : this.state.discountType
                        }
                        <Icon type="down" />
                    </Button>
                </Dropdown>
                { this.state.discountType === undefined
                ? ""
                :
                this.state.discountType === DiscountType.ABSOLUTE
                ?
                <InputNumber
                    defaultValue={this.state.discountValue}
                    value={this.state.discountValue}
                    formatter={value => `€ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
                    parser={value => +value.replace(/\€\s?|(,*)/g, "")}
                    onChange={(e: number) => this.setState({...this.state, discountValue: e})}
                />
                :
                <InputNumber
                    defaultValue={this.state.discountValue}
                    value={this.state.discountValue}
                    min={0}
                    max={100}
                    formatter={value => `${value}%`}
                    parser={value => +value.replace("%", "")}
                    onChange={(e: number) => this.setState({...this.state, discountValue: e})}
                />
                }
                <br/>
                { this.state.discountTarget === undefined
                ? ""
                :
                this.state.discountTarget === DiscountTarget.CATEGORY
                ?
                <CategoryDropdown
                    selected={this.state.category}
                    categories={this.state.categories}
                    onChange={newCategory => this.setState({
                        ...this.state,
                        category: newCategory
                        })
                    }
                />
                :
                this.state.discountTarget === DiscountTarget.PRODUCT
                ?
                [<CategoryDropdown
                    selected={this.state.category}
                    categories={this.state.categories}
                    onChange={newCategory => this.getProductsForCategory(newCategory)}
                />,
                <div></div>]
                :
                ""
                }
            </div>
        );
    }
}
