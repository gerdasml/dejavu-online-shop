// reikia padaryti userio orderio gavima is orderAPI ir gautus orderius atvaizduoti.
import * as React from "react";

import {Header, Icon, Loader, Menu, Pagination, Rating, Table} from "semantic-ui-react";

import "../../../../style/profile.css";

import * as api from "../../../api";
import * as order from "../../../api/order";

import {Order} from "../../../model/Order";

interface OrderHistoryState {
    activePage: number;
    error?: string;
    isLoading: boolean;
    orders: Order[];
    shownFirstPage: number;
}

export class OrderHistory extends React.Component<{},OrderHistoryState> {
    state: OrderHistoryState = {
        activePage: 1,
        isLoading: true,
        orders: [],
        shownFirstPage: 1,
    };

    async componentDidMount () {
        const orderHistory = await order.getOrderHistory();
        // console.log(orderHistory);
        if(api.isError(orderHistory)) {
            console.log(orderHistory.message);
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
        // console.log(this.state.orders);
        const orderHistory = this.state.orders;
        const start = (this.state.activePage-1)*5;
        const end = start+5;
        return orderHistory.slice(start,end).map( (x, i) =>
            <Table.Row>
                {/*console.log(x)*/}
                <Table.Cell>
                    <Header as="h2" >{start+i+1}</Header>
                </Table.Cell>
                <Table.Cell singleLine>{
                    this.parseDate(x.createdDate.toString()).toLocaleDateString()
                    // new Date(Date.parse(x.createdDate.toString()))
                }</Table.Cell>
                <Table.Cell>
                    <Rating icon="star" defaultRating={0} maxRating={5} disabled/>
                </Table.Cell>
                <Table.Cell>{x.status}</Table.Cell>
                <Table.Cell>{x.total} €</Table.Cell>
            </Table.Row>
        );
    }

    render () {
        return(
            <div>
                {
                    this.state.isLoading
                    ? <Loader active inline="centered" />
                    :
                    <div>
                        <Table id="orderHistoryTable" celled padded selectable>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell singleLine>Order Nr.</Table.HeaderCell>
                                    <Table.HeaderCell singleLine>Order date</Table.HeaderCell>
                                    <Table.HeaderCell>Rating</Table.HeaderCell>
                                    <Table.HeaderCell>Status</Table.HeaderCell>
                                    <Table.HeaderCell>Price</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>
                            <Table.Body>
                                {/*<Table.Row>
                                    <Table.Cell>
                                        <Header as="h2" >0001</Header>
                                    </Table.Cell>
                                    <Table.Cell singleLine>2018-04-28</Table.Cell>
                                    <Table.Cell>
                                        <Rating icon="star" defaultRating={5} maxRating={5} disabled/>
                                    </Table.Cell>
                                    <Table.Cell>Delivering...</Table.Cell>
                                    <Table.Cell>68,99 €</Table.Cell>
                                </Table.Row>
                                <Table.Row>
                                    <Table.Cell>
                                        <Header as="h2" >0002</Header>
                                    </Table.Cell>
                                    <Table.Cell singleLine>2018-04-28</Table.Cell>
                                    <Table.Cell>
                                        <Rating icon="star" defaultRating={2} maxRating={5} disabled/>
                                    </Table.Cell>
                                    <Table.Cell>Delivering...</Table.Cell>
                                    <Table.Cell>68,99 €</Table.Cell>
                                </Table.Row>
                                <Table.Row>
                                    <Table.Cell>
                                        <Header as="h2" >0003</Header>
                                    </Table.Cell>
                                    <Table.Cell singleLine>2018-04-28</Table.Cell>
                                    <Table.Cell>
                                        <Rating icon="star" defaultRating={4} maxRating={5} disabled/>
                                    </Table.Cell>
                                    <Table.Cell>Delivering...</Table.Cell>
                                    <Table.Cell>68,99 €</Table.Cell>
                                </Table.Row>*/}
                                {this.mapOrders()}
                            </Table.Body>
                        </Table>
                        {/*<Menu floated="right" pagination>
                            {
                                this.state.shownFirstPage === 1
                                ?   <Menu.Item as="a" icon disabled>
                                        <Icon name="chevron left" />
                                    </Menu.Item>
                                :   <Menu.Item as="a" icon>
                                        <Icon name="chevron left" />
                                    </Menu.Item>
                            }
                            {this.showPages()}
                            <Menu.Item as="a">1</Menu.Item>
                            <Menu.Item as="a">2</Menu.Item>
                            <Menu.Item as="a">3</Menu.Item>
                            <Menu.Item as="a">4</Menu.Item>
                            <Menu.Item as="a">5</Menu.Item>
                            {
                                this.state.orders.length > (this.state.shownFirstPage+5)*5
                                ?   <Menu.Item as="a" icon>
                                        <Icon name="chevron right" />
                                    </Menu.Item>
                                :   <Menu.Item as="a" icon disabled>
                                        <Icon name="chevron right" />
                                    </Menu.Item>
                            }
                        </Menu>*/}
                        <Pagination
                            // defaultActivePage={1}
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
                    </div>
                }
            </div>
        );
    }

    handlePaginationChange = (e: any, x: any) => this.setState({ ...this.state, activePage: x.activePage });

    showPages () {
        const historyCount = this.state.orders.length;
        const pagesNeeded = Math.ceil(historyCount/5);

    }
}
