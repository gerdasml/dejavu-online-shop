import * as React from "react";

import { Button, Comment, Form, Grid, Header, Modal, Rating, Table } from "semantic-ui-react";

import { Order } from "../../../model/Order";

import { Review } from "../../../model/Review";

import { OrderReview } from "./OrderReview";

interface ReviewProps {
    onClose: () => void;
    open: boolean;
    orders: Order[];
    onReviewSubmit: (orderId: number, review: Review) => void;
}

interface ReviewState {
    orders: Order[];
}

export class ReviewModal extends React.Component<ReviewProps, ReviewState> {

    state = {
        orders: this.props.orders
    };

    handleReview = (review: Review) => {
        this.props.onReviewSubmit(this.state.orders[0].id, review);
        this.setState({ ... this.state, orders: this.state.orders.slice(1)});
    }

    close = () => this.props.onClose();

    // tslint:disable-next-line:prefer-function-over-method
    render () {
        return (
            this.state.orders.length
            ?
            <Modal open={this.props.open}>
                <Modal.Header>Please review your last Order</Modal.Header>
                <Modal.Content>
                            <OrderReview order={this.state.orders[0]}
                            onReview={this.handleReview.bind(this)} onClose={this.close} />
                </Modal.Content>
            </Modal>
            :
            this.close
        );
    }
}
