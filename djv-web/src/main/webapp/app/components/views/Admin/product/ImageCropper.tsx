import * as React from "react";
import Cropper from "react-cropper";

import { Col, Row } from "antd";

import "cropperjs/dist/cropper.css";

export interface ImageCropperState {
    open: boolean;
    src?: string;
}

interface ImageCropperProps {
    image: File;
    onChange: (dataUrlProvider: () => string) => void;
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

        this.props.onChange(() => this.cropper.getCroppedCanvas().toDataURL());
    }

    render () {
        return (
            <Row type="flex" align="middle" justify="space-between">
                <Col span={12}>
                    <Cropper
                        style={{ height: 400, width: '100%' }}
                        ref={(elem: HTMLImageElement & Cropper) => {this.cropper = elem;}}
                        src={this.state.src}
                        aspectRatio={16 / 9}
                        guides={false}
                        crop={this.cropImage}
                        preview=".img-preview"
                        />
                </Col>
                <Col span={12} className="box">
                    <div className="img-preview" style={{height: "400px", width: "100%", float: "left", overflow: "hidden"}} />
                </Col>
            </Row>
        );
    }
}
