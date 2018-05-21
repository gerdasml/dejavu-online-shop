import * as React from "react";

import { Modal } from "semantic-ui-react";

import { Order } from "../../../model/Order";

import { Review } from "../../../model/Review";

import { OrderReview } from "./OrderReview";

interface ReviewProps {
    onClose: () => void;
    open: boolean;
    order: Order;
    onReviewSubmit: (orderId: number, review: Review) => void;
}

export class ReviewModal extends React.Component<ReviewProps, never> {
    close = () => this.props.onClose();

    render () {
        return (
            <Modal open={this.props.open}>
                <Modal.Header>Please review your last Order</Modal.Header>
                <Modal.Content>
                    <OrderReview
                        order={this.props.order}
                        onReview={r => this.props.onReviewSubmit(this.props.order.id, r)}
                        onClose={this.close}
                    />
                </Modal.Content>
            </Modal>
        );
    }
}
