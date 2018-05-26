import * as React from "react";

import { Button, Comment, Form, Grid, Header, Rating, Table} from "semantic-ui-react";

import { Order } from "../../../model/Order";

import { Review } from "../../../model/Review";

import "../../../../style/style.css";

interface OrderReviewProps {
    order: Order;
    onReview: (review: Review) => void;
    onClose: () => void;
}

interface OrderReviewState {
    review: Review;
}

const INITIAL_STATE = {
    review: { rating: 5, comment: "" }
};

export class OrderReview extends React.Component<OrderReviewProps, OrderReviewState> {

    state = INITIAL_STATE;

    handleSubmit () {
        this.props.onReview(this.state.review);
        this.setState(INITIAL_STATE); // Reset the state after submit
    }

    render () {
        return (
            <div>
            <Grid columns={2} divided stackable className="review-modal-content">
                <Grid.Column>
                    <Header as="h3" dividing>Rating</Header>
                    <Rating
                        icon="star"
                        maxRating={5}
                        rating={this.state.review.rating}
                        size="huge"
                        onRate={(e, r) => this.setState({
                            ...this.state,
                            review: {
                                ...this.state.review,
                                rating: +r.rating
                            }
                        })}
                    />
                    <Comment.Group>
                        <Header as="h3" dividing>Comment</Header>
                        <Form>
                            <Form.TextArea
                                value={this.state.review.comment}
                                onChange={e => this.setState({
                                    ...this.state,
                                    review: {
                                        ...this.state.review,
                                        comment: e.currentTarget.value
                                    }
                                })}
                            />
                        </Form>
                    </Comment.Group>
                </Grid.Column>
                <Grid.Column>
                    <Header as="h3" dividing>Products</Header>
                    <Table celled striped className="review-table">
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell textAlign="center">Product</Table.HeaderCell>
                                <Table.HeaderCell textAlign="center">Amount</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                            <Table.Body>
                                {this.props.order.items.map(item => (
                                    <Table.Row>
                                        <Table.Cell>
                                            {item.product.name}
                                        </Table.Cell>
                                        <Table.Cell>
                                            {item.amount}
                                        </Table.Cell>
                                    </Table.Row>
                                ))}
                            </Table.Body>
                    </Table>
                </Grid.Column>
            </Grid>
            <Button
                className="order-review-cancel-button"
                content="Cancel"
                onClick={this.props.onClose} />
            <Button
                className="order-review-save-button"
                content="Save"
                onClick={this.handleSubmit.bind(this)} />
            </div>
        );
    }
}
