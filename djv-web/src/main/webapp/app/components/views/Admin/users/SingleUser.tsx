import * as React from "react";
import { RouteComponentProps } from "react-router-dom";

import { notification, Spin, Badge, Tag } from "antd";
import { Order } from "../../../../model/Order";
import { User, UserType } from "../../../../model/User";

import { OrdersTable } from "./Table/OrdersTable";
import { OrderTable } from "./Table/OrderTable";

import * as api from "../../../../api";
import { stringifyAddress } from "../../../../utils/common";

interface SingleUserProps {
    id: number;
}

interface SingleUserState {
    isLoading: boolean;
    user?: User;
    orders: Order[];
}

export class SingleUser extends React.Component<RouteComponentProps<SingleUserProps>, SingleUserState> {
    state: SingleUserState = {
        isLoading: true,
        orders: []
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

    showString = (s: string) => s ? s : "N/A";

    render () {
        const { isLoading, user, orders } = this.state;
        return (
            <Spin spinning={isLoading} size="large">
                { user
                ?
                <span>
                    <h3>
                        <b>Traits: </b>
                        { user.type === UserType.REGULAR
                        ? <Tag color="green">Regular user</Tag>
                        : <Tag color="geekblue">Admin</Tag>
                        }
                        { user.banned
                        ? <Tag color="red">Banned</Tag>
                        : ""
                        }
                    </h3>
                    <h3><b>Email: </b>{this.showString(user.email)}</h3>
                    <h3><b>Name: </b>{this.showString(user.firstName)} {this.showString(user.lastName)}</h3>
                    <h3><b>Phone: </b>{this.showString(user.phone)}</h3>
                    <h3><b>Address: </b>{this.showString(stringifyAddress(user.address))}</h3>
                    <h3><b>Orders:</b></h3>
                </span>
                : ""
                }
                <OrdersTable orders={orders} />
            </Spin>
        );
    }
}
