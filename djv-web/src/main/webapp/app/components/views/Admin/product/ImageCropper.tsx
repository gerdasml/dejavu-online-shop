import * as React from "react";
import Cropper from "react-cropper";

import { Col, Row } from "antd";

import "cropperjs/dist/cropper.css";

export interface ImageCropperState {
    cropResult?: string;
    open: boolean;
    src?: string;
}

interface ImageCropperProps {
    image: File;
    onChange: (dataUrl: string) => void;
}

export class ImageCropper extends React.Component<ImageCropperProps, ImageCropperState> {
    cropper: Cropper;
    constructor (props: ImageCropperProps) {
        super(props);
        this.state = {
            open: false
        };
        this.readImage(props.image);
        this.cropImage = this.cropImage.bind(this);
    }

    readImage = (image: File) => {
        const reader = new FileReader();
        reader.onload = () => {
            this.setState({ ...this.state, src: reader.result });
        };
        reader.readAsDataURL(image);
    }

    cropImage () {
        if (typeof this.cropper.getCroppedCanvas() === "undefined") {
            return;
        }
        const newImg = this.cropper
                           .getCroppedCanvas()
                           .toDataURL();
        this.setState({
            cropResult: newImg,
        });

        this.props.onChange(newImg);
    }

    render () {
        return (
            <Row type="flex" align="middle" justify="space-between">
                <Col span={12}>
                    <Cropper
                        ref={(elem: HTMLImageElement & Cropper) => {this.cropper = elem;}}
                        src={this.state.src}
                        aspectRatio={16 / 9}
                        guides={false}
                        crop={this.cropImage} />
                </Col>
                <Col span={12}>
                    <img style={{ width: "100%" }} src={this.state.cropResult}/>
                </Col>
            </Row>
        );
    }
}
