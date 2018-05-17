import * as React from "react";
import Cropper from "react-cropper";

import { Col, Row } from "antd";

import { lookup } from "mime-types";

import "cropperjs/dist/cropper.css";

export interface ImageCropperState {
    open: boolean;
    src?: string;
}

interface ImageCropperProps {
    image: File;
    onChange: (dataUrlProvider: () => Blob) => void;
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

        const type: string = lookup(this.props.image.name).toString();
        this.cropper
            .getCroppedCanvas({
                fillColor: type === "image/png" ? undefined : "white"
            })
            .toBlob((result: Blob) => this.props.onChange(() => result), type, 0.5);
            // TODO: investigate this compression ratio
    }

    render () {
        return (
            <Row type="flex" align="middle" justify="space-between">
                <Col span={12}>
                    <Cropper
                        style={{ height: 400, width: "100%" }}
                        ref={(elem: HTMLImageElement & Cropper) => {this.cropper = elem;}}
                        src={this.state.src}
                        aspectRatio={16 / 9}
                        guides={false}
                        crop={this.cropImage}
                        preview=".img-preview"
                        />
                </Col>
                <Col span={12} className="box">
                    <div
                        className="img-preview"
                        style={{
                            float: "left",
                            height: "400px",
                            overflow: "hidden",
                            width: "100%",
                        }}
                    />
                </Col>
            </Row>
        );
    }
}
