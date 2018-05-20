import * as React from "react";

import { Dropdown, Button, Icon, Menu, DatePicker, InputNumber, notification, Spin } from "antd";

import * as api from "../../../../api";
import { Discount, DiscountTarget } from "../../../../model/Discount";
import ButtonGroup from "antd/lib/button/button-group";
import { NavLink } from "react-router-dom";
import { DiscountsTable } from "./DiscountsTable";

interface DiscountsState {
    isLoading: boolean;
    discounts: Discount[];
}

export class Discounts extends React.Component <{}, DiscountsState> {
    state: DiscountsState = {
        isLoading: true,
        discounts: [],
    };

    async componentWillMount () {
        // const discounts = await api.discount.getAllDiscounts();
        // if(api.isError(discounts)) {
        //     notification.error({message: "Failed to fetch category data", description: discounts.message});
        //     return;
        // }
        const discounts: Discount[] = []; // Temporary solution until backend starts working
        this.setState({
            ...this.state,
            discounts,
        });
    }

    async handleDelete (id: number, targetType: DiscountTarget) {
        // const deleteResponse = await api.discount.deleteDiscount(id, targetType);
        // if(api.isError(deleteResponse)) {
        //     notification.error({message: "Failed to fetch category data", description: deleteResponse.message});
        //     return;
        // }
        this.setState({
            ...this.state,
            discounts: this.state.discounts.filter(d => (d.id !== id && d.targetType !== targetType)),
        });
    }

    render () {
        return (
            <Spin spinning={this.state.isLoading} size="large">
                <ButtonGroup>
                    <NavLink to={`/admin/discount/create`}>
                        <Button>Add new discount</Button>
                    </NavLink>
                </ButtonGroup>
                <DiscountsTable
                    discounts={this.state.discounts}
                    onDelete={(id: number, targetType: DiscountTarget) => this.handleDelete(id, targetType)}
                />
            </Spin>
        );
    }

}