import * as React from "react";
import { RouteComponentProps } from "react-router-dom";

import { Grid } from "semantic-ui-react";

import { notification, Spin, Tag } from "antd";
import { Order } from "../../../../model/Order";
import { User, UserType } from "../../../../model/User";

import { OrdersTable } from "../common/Table/OrdersTable";

import * as api from "../../../../api";
import { stringifyAddress } from "../../../../utils/common";
import { BanButton } from "./BanButton";

import "../../../../../style/admin/users.css";

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

    // required to load data on initial render
    async componentDidMount () {
        await this.loadData(this.props);
    }

    // required to load data on each url change
    async componentWillReceiveProps (nextProps: RouteComponentProps<SingleUserProps>) {
        await this.loadData(nextProps);
    }

    async loadData (props: RouteComponentProps<SingleUserProps>) {
        const userResponse = await api.user.getUser(props.match.params.id);
        if (api.isError(userResponse)) {
            notification.error({message: "Failed to fetch user data", description: userResponse.message});
            return;
        }
        const ordersResponse = await api.order.getUserOrderHistory(props.match.params.id);
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
                <Grid id="singleUserGrid" stackable>
                    <Grid.Row>
                        <Grid.Column>
                            <h3><b>Traits:</b></h3>
                        </Grid.Column>
                        <Grid.Column width="eight">
                        <h3>
                        { user.type === UserType.REGULAR
                        ? <Tag color="green">Regular user</Tag>
                        : <Tag color="geekblue">Admin</Tag>
                        }
                        { user.banned
                        ? <Tag color="red">Banned</Tag>
                        : ""
                        }
                    </h3>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column>
                            <h3><b>Email:</b></h3>
                        </Grid.Column>
                        <Grid.Column>
                        <h3>{this.showString(user.email)}</h3>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column>
                            <h3><b>Name:</b></h3>
                        </Grid.Column>
                        <Grid.Column>
                        <h3>{this.showString(user.firstName)} {this.showString(user.lastName)}</h3>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column>
                            <h3><b>Phone:</b></h3>
                        </Grid.Column>
                        <Grid.Column>
                        <h3>{this.showString(user.phone)}</h3>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column>
                            <h3><b>Address:</b></h3>
                        </Grid.Column>
                        <Grid.Column>
                        <h3>{this.showString(stringifyAddress(user.address))}</h3>
                        </Grid.Column>
                    </Grid.Row>
                    { user.type !== UserType.ADMIN
                    ?
                    <Grid.Row>
                        <Grid.Column><h3><b>Actions: </b></h3></Grid.Column>
                        <Grid.Column><h3>
                        <BanButton
                            user={user}
                            onSwitch={() => this.setState({...this.state, user: {...user, banned: !user.banned}})}
                        />
                        </h3></Grid.Column>
                    </Grid.Row>
                    : ""
                    }
                    <h3><b>Orders:</b></h3>
                </Grid>
                : ""
                }
                <OrdersTable orders={orders} />
            </Spin>
        );
    }
}
