import * as React from "react";

import { Button, Comment, Form, Grid, Header, Modal, Rating, Table} from "semantic-ui-react";

import { Order } from "../../../model/Order";

import { Review } from "../../../model/Review";

interface OrderReviewProps {
    order: Order;
    onReview: (review: Review) => void;
    onClose: () => void;
}

interface OrderReviewState {
    review: Review;
}

export class OrderReview extends React.Component<OrderReviewProps, OrderReviewState> {

    state = {
        review: { rating: 5, comment: "" }
    };

    handleRatingInput = (e: React.MouseEvent<HTMLDivElement>, rating) => {
        // tslint:disable-next-line:radixs
        const value = Number(rating.rating);
        this.setState({
            ...this.state, review: {rating: value, comment: this.state.review.comment}
        });
    }

    handleCommentInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, review: {rating: this.state.review.rating, comment: value}
        });
    }

    render () {
        return (
            <div>
            <Grid columns={2} divided>
                <Grid.Column>
                    <Header as="h3" dividing>Rating</Header>
                    <Rating icon="star" maxRating={5} defaultRating={5} size="huge"
                        onRate={this.handleRatingInput}/>
                    <Comment.Group>
                        <Header as="h3" dividing>Comment</Header>
                        <Form>
                            <Form.TextArea onChange={this.handleCommentInput.bind(this)}/>
                        </Form>
                    </Comment.Group>
                </Grid.Column>
                <Grid.Column>
                    <Header as="h3" dividing>Products</Header>
                    <Table celled striped>
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
            <Button content="Save" positive onClick={() =>
                this.props.onReview(this.state.review)} />
            <Button content="Cancel" negative onClick={this.props.onClose} />
            </div>
        );
    }
}
