// reikia padaryti userio orderio gavima is orderAPI ir gautus orderius atvaizduoti.
import * as React from "react";

import {Header, Icon, Menu, Rating, Table} from "semantic-ui-react";

import "../../../../style/profile.css";

export class OrderHistory extends React.Component<{},{}> {
    render () {
        return(
            <div>
                <Table id="orderHistoryTable" celled padded selectable>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell singleLine>Order ID</Table.HeaderCell>
                            <Table.HeaderCell singleLine>Order date</Table.HeaderCell>
                            <Table.HeaderCell>Rating</Table.HeaderCell>
                            <Table.HeaderCell>Status</Table.HeaderCell>
                            <Table.HeaderCell>Price</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        <Table.Row>
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
                    </Table.Row>
                    </Table.Body>
                </Table>
            <Menu floated="right" pagination>
                <Menu.Item as="a" icon>
                    <Icon name="chevron left" />
                </Menu.Item>
                <Menu.Item as="a">1</Menu.Item>
                <Menu.Item as="a">2</Menu.Item>
                <Menu.Item as="a">3</Menu.Item>
                <Menu.Item as="a">4</Menu.Item>
                <Menu.Item as="a">5</Menu.Item>
                <Menu.Item as="a" icon>
                    <Icon name="chevron right"/>
                </Menu.Item>
            </Menu>
        </div>
        );
    }
}
