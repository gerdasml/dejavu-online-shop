import * as React from "react";
// import * as Moment from "moment";

import { Dropdown, Button, Icon, Menu, DatePicker, InputNumber, notification } from "antd";

import * as api from "../../../../api";

import { RangePickerValue } from "antd/lib/date-picker/interface";
import { CategoryDropdown } from "../product/CategoryDropdown";
import { DiscountProductsTable } from "./DiscountProductsTable";
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
    product?: Product;
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
        // TODO strings still not like they should be
        newState.dateStart = (new Date (Date.parse(props.discount.activeFrom.toString())).toLocaleDateString());
        newState.dateEnd = (new Date (Date.parse(props.discount.activeTo.toString())).toLocaleDateString());
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
    }

    updateDiscountType (newDiscountType: DiscountType) {
        this.setState({
            ...this.state,
            discountType: newDiscountType,
            discountValue: 0,
        });
    }

    DiscountTargetFromString = (val: string) => DiscountTarget[val.toUpperCase() as keyof typeof DiscountTarget];

    DiscountTypeFromString = (val: string) => DiscountType[val.toUpperCase() as keyof typeof DiscountType];

    updateDate (date: RangePickerValue, dateString: [string, string]) {
        this.setState({
            ...this.state,
            dateStart: dateString[0],
            dateEnd: dateString[1],
        });
    }

    async handleSave () {
        const newDiscountTargetType = this.state.discountTarget;
        const newDiscountType = this.state.discountType;
        const newDiscountValue = this.state.discountValue;
        const newDiscountDateStart = new Date (Date.parse(this.state.dateStart));
        const newDiscountDateEnd = new Date (Date.parse(this.state.dateStart));

        let newDiscountTargetId;
        if(newDiscountTargetType === DiscountTarget.CATEGORY) {
            newDiscountTargetId = this.state.category;
        } else if (newDiscountTargetType === DiscountTarget.PRODUCT) {
            newDiscountTargetId = this.state.product.id;
        }

        let newDiscount;
        if(newDiscountTargetId === undefined) {
            newDiscount = {
                targetType: newDiscountTargetType,
                type: newDiscountType,
                value: newDiscountValue,
                activeFrom: newDiscountDateStart,
                activeTo: newDiscountDateEnd,
            };
            if( newDiscount.targetType === undefined ||
                newDiscount.type === undefined ||
                newDiscount.value < 0 ||
                newDiscount.activeFrom === undefined ||
                newDiscount.activeTo === undefined) {
                notification.error({message: "Discount properties are invalid",
                                    description: "Fill in all the missing fields."});
                return;
            }
        } else {
            newDiscount = {
                targetType: newDiscountTargetType,
                type: newDiscountType,
                value: newDiscountValue,
                targetId: newDiscountTargetId,
                activeFrom: newDiscountDateStart,
                activeTo: newDiscountDateEnd,
            };
            if( newDiscount.targetType === undefined ||
                newDiscount.type === undefined ||
                newDiscount.value < 0 ||
                newDiscount.activeFrom === undefined ||
                newDiscount.activeTo === undefined ||
                newDiscount.targetId === undefined) {
                notification.error({message: "Discount properties are invalid",
                                    description: "Fill in all the missing fields."});
                return;
            }
        }

        if(this.props.discount === undefined) {
            const response = await api.discount.addDiscount(newDiscount);
            if(api.isError(response)) {
                notification.error({message: "Failed to create new discount", description: response.message});
                return;
            }
        } else {
            const response = await api.discount.updateDiscount(this.props.discount.id,newDiscount);
            if(api.isError(response)) {
                notification.error({message: "Failed to edit this discount", description: response.message});
                return;
            }
        }
        // TODO redirect to admin discounts page
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
                <DatePicker.RangePicker
                    onChange={this.updateDate.bind(this)}
                    /* TODO: import moment and set default value to props value*/
                    /*defaultValue={[moment({this.props.activeFrom}, dateFormat), moment({this.props.activeTo}, dateFormat)]}*/
                />
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
                    min={0}
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
                <DiscountProductsTable
                    products={this.state.products}
                    onSelect={selectedProduct => this.setState({...this.state, product: selectedProduct})}
                />
                ]
                :
                ""
                }
                <br/>
                <Button onClick={this.handleSave.bind(this)}>
                    Save
                </Button>
            </div>
        );
    }
}
