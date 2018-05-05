import * as React from "react";
import Cropper from "react-cropper";

import { Button, Modal } from "semantic-ui-react";

import { ImageCropper } from "./ImageCropper";

interface ImageState {
    open: boolean;
}

export class ProductPicturesModal extends React.Component<{}, ImageState> {
    state = {
        open: false
    };
    handleOpen = () => this.setState({...this.state, open: true});
    handleClose = () => this.setState({...this.state, open: false});
    render () {
        return (
            <Modal
                trigger={
                    <Button
                        icon="plus"
                        size="medium"
                        onClick={this.handleOpen.bind(this)}
                    />
                }
                open={this.state.open}
                onClose={this.handleClose.bind(this)}
            >
                <Modal.Content>
                    <ImageCropper/>
                </Modal.Content>
            </Modal>
        );
    }
}
