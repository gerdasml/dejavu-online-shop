import * as React from "react";
import * as Moment from "moment";

import { Dropdown, Button, Icon, Menu, DatePicker, InputNumber, notification, message } from "antd";
import * as api from "../../../../api";

import { RangePickerValue } from "antd/lib/date-picker/interface";
import { CategoryDropdown } from "../product/CategoryDropdown";
import { DiscountProductsTable } from "./DiscountProductsTable";
import { CategoryTree } from "../../../../model/CategoryTree";
import { Product } from "../../../../model/Product";
import { DiscountTarget, DiscountType, Discount } from "../../../../model/Discount";

import { fromString } from "../../../../utils/enum";

import "../../../../../style/admin/discounts.css";
import { Category } from "../../../../model/Category";

interface DiscountEditorProps {
    discount?: Discount;
    onSubmit?: () => void;
}

interface DiscountEditorState {
    discountTarget?: DiscountTarget;
    discountType?: DiscountType;
    dateStart?: Date;
    dateEnd?: Date;
    discountValue: number;
    categories: CategoryTree[];
    category?: number;
    products: Product[];
    selectedProductIds?: number[];
}

export class DiscountEditor extends React.Component <DiscountEditorProps, DiscountEditorState> {
    state: DiscountEditorState = {
        categories: [],
        discountTarget: undefined,
        discountType: undefined,
        discountValue: 0,
        products: [],
        selectedProductIds: [],
    };

    discountTargetMenu = (
        <Menu onClick={e =>this.setState({
            ...this.state,
            discountTarget: fromString(DiscountTarget, e.key),
            category: undefined
        })}>
            <Menu.Item key={DiscountTarget.EVERYTHING}>{DiscountTarget.EVERYTHING}</Menu.Item>
            <Menu.Item key={DiscountTarget.CATEGORY}>{DiscountTarget.CATEGORY}</Menu.Item>
            <Menu.Item key={DiscountTarget.PRODUCT}>{DiscountTarget.PRODUCT}</Menu.Item>
        </Menu>
    );

    discountTypeMenu = (
        <Menu onClick={e =>this.setState({ ...this.state,
                discountType: fromString(DiscountType, e.key),
                discountValue: 0,
        })}>
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
        this.setState({...this.state,categories});
    }

    async componentWillReceiveProps (props: DiscountEditorProps) {
        const newState = this.state;
        if(props.discount !== undefined) {
            newState.discountTarget = props.discount.targetType;
            newState.discountType = props.discount.type;
            newState.dateStart = new Date (Date.parse(props.discount.activeFrom.toString()));
            newState.dateEnd = new Date (Date.parse(props.discount.activeTo.toString()));
            newState.discountValue = props.discount.value;
            if (props.discount.targetType === DiscountTarget.PRODUCT) {
                newState.category = (props.discount.target as Product).categoryId;
                newState.selectedProductIds = [props.discount.target.id];
                newState.products = await this.getProductsForCategory(newState.category);
            } else if (props.discount.targetType === DiscountTarget.CATEGORY) {
                newState.category = (props.discount.target as Category).id;
            }
            this.setState(newState);
        } else {
            this.setState({
                categories: [],
                discountTarget: undefined,
                discountType: undefined,
                discountValue: 0,
                products: [],
            });
        }
    }

    getProductsForCategory = async (category: number): Promise<Product[]> => {
        const categoryProducts = await api.product.getProductsByCategory(category);
        if(api.isError(categoryProducts)) {
            notification.error({message: "Failed to fetch products data", description: categoryProducts.message});
            return [];
        }
        return categoryProducts;
    }

    updateDate (date: RangePickerValue, dateString: [string, string]) {
        this.setState({
            ...this.state,
            dateStart: dateString[0].length > 0 ? new Date(Date.parse(dateString[0])) : undefined,
            dateEnd: dateString[1].length > 0 ? new Date(Date.parse(dateString[1])) : undefined,
        });
    }

    async handleSave () {
        const newDiscountTargetType = this.state.discountTarget;
        const newDiscountType = this.state.discountType;
        const newDiscountValue = this.state.discountValue;
        const newDiscountDateStart = this.state.dateStart;
        const newDiscountDateEnd = this.state.dateEnd;

        let anyErrors: boolean = false;
        if( newDiscountTargetType === undefined) {
            this.notifyError("Discount target type was not selected.");
            anyErrors = true;
        }
        if (newDiscountType === undefined) {
            this.notifyError("Discount type was not selected.");
            anyErrors = true;
        }
        if (newDiscountValue === 0) {
            this.notifyError("Discount value must be a positive number.");
            anyErrors = true;
        }
        if (newDiscountDateStart === undefined || newDiscountDateEnd === undefined) {
            this.notifyError("Discount date period was not selected.");
            anyErrors = true;
        }
        if(anyErrors) {
            return;
        }

        let newDiscount: Discount = {
            targetType: newDiscountTargetType,
            type: newDiscountType,
            value: newDiscountValue,
            activeFrom: newDiscountDateStart,
            activeTo: newDiscountDateEnd,
        };

        if (newDiscountTargetType === DiscountTarget.CATEGORY) {
            const newDiscountTargetId = this.state.category;
            newDiscount = {...newDiscount, targetId: newDiscountTargetId};
        }

        if(newDiscountTargetType !== DiscountTarget.PRODUCT) {
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
        } else if (newDiscountTargetType === DiscountTarget.PRODUCT) {
            const productToDiscountIds = this.state.selectedProductIds;
            if(productToDiscountIds === undefined || productToDiscountIds.length === 0) {
                this.notifyError("At least one product must be selected.");
                return;
            }

            if(this.props.discount !== undefined) {
                const firstProductId = productToDiscountIds.pop();
                const response = await api.discount.updateDiscount(this.props.discount.id,
                    {
                        ...newDiscount,
                        targetId: firstProductId
                    }
                );
                if(api.isError(response)) {
                    notification.error({message: "Failed to edit this discount.", description: response.message});
                    return;
                }
            }

            if(productToDiscountIds.length > 0) {
                const response = await api.discount.addDiscounts(productToDiscountIds.map(pid =>
                    ({
                        ...newDiscount,
                        targetId: pid,
                    })
                ));
                if(api.isError(response)) {
                    notification.error({message: "Failed to create new discount(s).", description: response.message});
                    return;
                }
            }
        }

        message.success("Discount procedure was successful.");
    }

    notifyError= (error: string)=> {notification.error({message: error,description: "All fields must be filled in."});};

    extractProductIds = (products: Product[]) => {
        const productIds = products.map(p => p.id);
        this.setState({...this.state, selectedProductIds: productIds});
    }

    parsePercentage = (val: string): number => {
        const inputNum = +(val.replace(/[^0-9]/g, ""));
        let result = inputNum;
        result = Math.min(100, result);
        result = Math.max(0, result);
        return result;
    }

    render () {
        return (
            <div id="discountEditor">
                <Dropdown
                    trigger={["click"]}
                    overlay={this.discountTargetMenu}
                    disabled={this.props.discount !== undefined}
                >
                    <Button className="discountEditorButton">
                        { this.state.discountTarget === undefined
                        ? "Discount target"
                        : this.state.discountTarget
                        }
                        <Icon type="down"/>
                    </Button>
                </Dropdown>
                { this.state.dateStart === undefined || this.state.dateEnd === undefined
                ?
                <DatePicker.RangePicker className="discountEditorButton"
                    onChange={this.updateDate.bind(this)}
                    format={"YYYY-MM-DD"}
                />
                :
                <DatePicker.RangePicker className="discountEditorButton"
                    onChange={this.updateDate.bind(this)}
                    value={[ Moment(this.state.dateStart),
                                    Moment(this.state.dateEnd)]}
                    format={"YYYY-MM-DD"}
                />
                }
                <Dropdown className="discountEditorButton"
                    trigger={["click"]}
                    overlay={this.discountTypeMenu}
                >
                    <Button style={{ marginLeft: 0 }} className="discountEditorButton">
                        { this.state.discountType === undefined
                        ? "Discount type"
                        : this.state.discountType
                        }
                        <Icon type="down" />
                    </Button>
                </Dropdown>
                { this.state.discountType === undefined ? ""
                : this.state.discountType === DiscountType.ABSOLUTE
                ?
                <InputNumber className="discountEditorButton"
                    defaultValue={this.state.discountValue}
                    value={this.state.discountValue}
                    min={0}
                    formatter={value => `€ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
                    parser={value => +(value.replace(/[^0-9]/g, ""))}
                    onChange={(e: number) => this.setState({...this.state, discountValue: e})}
                />
                :
                <InputNumber className="discountEditorButton"
                    defaultValue={this.state.discountValue}
                    value={this.state.discountValue}
                    min={0}
                    max={100}
                    formatter={value => `${value}%`}
                    parser={this.parsePercentage.bind(this)}
                    onChange={(e: number) => this.setState({...this.state, discountValue: e})}
                />
                }
                <br/>
                { this.state.discountTarget === undefined ? ""
                : this.state.discountTarget === DiscountTarget.CATEGORY
                ?
                <CategoryDropdown
                    selected={this.state.category}
                    categories={this.state.categories}
                    onChange={newCategory => this.setState({
                        ...this.state,
                        category: newCategory
                        })
                    }
                    allowParentSelection={true}
                    disabled={this.props.discount !== undefined}
                />
                : this.state.discountTarget === DiscountTarget.PRODUCT
                ?
                [<CategoryDropdown
                    selected={this.state.category}
                    categories={this.state.categories}
                    onChange={async newCategory => this.setState({
                        ...this.state,
                        category: newCategory,
                        products: await this.getProductsForCategory(newCategory)
                    })}
                    disabled={this.props.discount !== undefined}
                />,
                <DiscountProductsTable
                    products={this.state.products}
                    onSelect={selectedProducts => this.extractProductIds(selectedProducts)}
                    selectionDisabled={this.props.discount !== undefined}
                    selectedProductIds={this.state.selectedProductIds}
                />
                ]
                : ""
                }
                <br/>
                <Button onClick={this.handleSave.bind(this)} id="saveButton">Save</Button>
            </div>
        );
    }
}
