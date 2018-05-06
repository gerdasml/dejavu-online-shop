import * as React from "react";
import { RouteComponentProps } from "react-router-dom";

import { notification, Spin } from "antd";
import { User } from "../../../../model/User";

import * as api from "../../../../api";
import { Order } from "../../../../model/Order";

interface SingleUserProps {
    id: number;
}

interface SingleUserState {
    isLoading: boolean;
    user?: User;
    orders?: Order[];
}

export class SingleUser extends React.Component<RouteComponentProps<SingleUserProps>, SingleUserState> {
    state: SingleUserState = {
        isLoading: true
    };

    async componentDidMount () {
        const userResponse = await api.user.getUser(this.props.match.params.id);
        if (api.isError(userResponse)) {
            notification.error({message: "Failed to fetch user data", description: userResponse.message});
            return;
        }
        const ordersResponse = await api.order.getUserOrderHistory(this.props.match.params.id);
        if (api.isError(ordersResponse)) {
            notification.error({message: "Failed to fetch orders data", description: ordersResponse.message});
            return;
        }

        this.setState({user: userResponse, orders: ordersResponse, isLoading: false});
    }

    render () {
        const { isLoading, user, orders } = this.state;
        return (
            <Spin spinning={isLoading} size="large">
                <p>{JSON.stringify(user)}</p>
                <p>{JSON.stringify(orders)}</p>
            </Spin>
        );
    }
}
