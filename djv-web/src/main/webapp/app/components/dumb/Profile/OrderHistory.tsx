import * as React from "react";

import {Button, Icon, Loader, Modal, Pagination, Rating, Table} from "semantic-ui-react";

import "../../../../style/profile.css";

import * as api from "../../../api";

import {Order} from "../../../model/Order";
import {OrderTable} from "../Order/OrderTable";

interface OrderHistoryState {
    activePage: number;
    error?: string;
    isLoading: boolean;
    orders: Order[];
    activeOrder?: Order;
}

export class OrderHistory extends React.Component<{},OrderHistoryState> {
    state: OrderHistoryState = {
        activePage: 1,
        isLoading: true,
        orders: [],
    };

    async componentDidMount () {
        const orderHistory = await api.order.getOrderHistory();
        if(api.isError(orderHistory)) {
            this.setState({
                ...this.state,
                error: orderHistory.message,
                isLoading: false,
            });
        } else {
            this.setState({
                ...this.state,
                isLoading: false,
                orders: orderHistory,
            });
        }
    }

    parseDate = (date: string) => {
        const parsedDate = Date.parse(date);
        return new Date(parsedDate);
    }

    mapOrders () {
        const orderHistory = this.state.orders;
        const start = (this.state.activePage-1)*5;
        const end = start+5;
        return orderHistory.slice(start,end).map( (x, i) =>
            <Table.Row key={i}>
                <Table.Cell singleLine>{
                    this.parseDate(x.createdDate.toString()).toLocaleDateString()
                }</Table.Cell>
                <Table.Cell>{x.status}</Table.Cell>
                <Table.Cell>{x.total}€</Table.Cell>
                <Table.Cell>
                    <Button icon circular onClick={() => this.showOrderTable(x)}>
                        <Icon name="info"/>
                    </Button>
                </Table.Cell>
            </Table.Row>
        );
    }

    showOrderTable = (order: Order) => {
        this.setState({
            ...this.state,
            activeOrder: order,
        });
    }

    close = () => this.setState({ ...this.state, activeOrder: undefined });

    handlePaginationChange = (e: any, x: any) => this.setState({ ...this.state, activePage: x.activePage });

    render () {
        return(
            <div>
                { this.state.isLoading
                ? <Loader active inline="centered" />
                :
                <div>
                    <Table id="orderHistoryTable" celled padded>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell singleLine>Order date</Table.HeaderCell>
                                <Table.HeaderCell>Status</Table.HeaderCell>
                                <Table.HeaderCell>Price</Table.HeaderCell>
                                <Table.HeaderCell singleLine>Order info</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {this.mapOrders()}
                        </Table.Body>
                    </Table>
                    <Pagination
                        floated="right"
                        activePage={this.state.activePage}
                        onPageChange={this.handlePaginationChange}
                        ellipsisItem={{ content: <Icon name="ellipsis horizontal" />, icon: true }}
                        firstItem={{ content: <Icon name="angle double left" />, icon: true }}
                        lastItem={{ content: <Icon name="angle double right" />, icon: true }}
                        prevItem={{ content: <Icon name="angle left" />, icon: true }}
                        nextItem={{ content: <Icon name="angle right" />, icon: true }}
                        totalPages={Math.ceil(this.state.orders.length/5)}
                    />
                    <Modal size={"large"} open={this.state.activeOrder!==undefined} onClose={this.close}>
                        <Modal.Header>
                            Information about your order
                        </Modal.Header>
                        <Modal.Content>
                            <OrderTable data={this.state.activeOrder}/>
                        </Modal.Content>
                    </Modal>
                </div>
                }
            </div>
        );
    }
}
