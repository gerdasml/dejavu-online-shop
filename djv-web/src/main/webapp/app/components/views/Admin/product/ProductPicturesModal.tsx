import * as React from "react";
import Cropper from "react-cropper";

import "cropperjs/dist/cropper.css";

import { Button, Modal } from "semantic-ui-react";

interface ImageState {
    open: boolean;
}

export class ProductPicturesModal extends React.Component<{}, ImageState> {
    state = {
        open: false
    };
    cropper: any;
    crop () {
        // image in dataUrl
        console.log(this.cropper.getCroppedCanvas().toDataURL());
    }
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
                    <Cropper
                        ref={(elem: any) => {this.cropper = elem;}}
                        src="https://vignette.wikia.nocookie.net/austinally/images/1/14/Random_picture_of_shark.png/revision/latest?cb=20150911004230"
                        style={{height: 400, width: "100%"}}
                        aspectRatio={16 / 9}
                        guides={false}
                        crop={this.crop.bind(this)} />
                </Modal.Content>
            </Modal>
        );
    }
}
