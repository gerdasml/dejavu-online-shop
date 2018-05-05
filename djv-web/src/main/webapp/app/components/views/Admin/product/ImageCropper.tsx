import * as React from "react";
import Cropper from "react-cropper";
import "cropperjs/dist/cropper.css";

export interface ImageCropperState {
    cropResult: any;
    open: boolean;
    src: any;
}

export class ImageCropper extends React.Component<any, ImageCropperState> {
    cropper: any;
    constructor (props: any) {
        super(props);
        this.state = {
            cropResult: undefined,
            open: false,
            src: undefined
        };
        this.readImage(props.image);
        this.cropImage = this.cropImage.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    readImage = (image: File) => {
        const reader = new FileReader();
        reader.onload = () => {
            this.setState({ ...this.state, src: reader.result });
        };
        reader.readAsDataURL(image);
    }

    onChange (e: any) {
        e.preventDefault();
        let files;
        if (e.dataTransfer) {
            files = e.dataTransfer.files;
        } else if (e.target) {
            files = e.target.files;
        }
        const reader = new FileReader();
        reader.onload = () => {
            this.setState({ ...this.state, src: reader.result });
        };
        reader.readAsDataURL(files[0]);
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
        <div>
            <div style={{ width: "100%" }}>
            <input type="file" onChange={this.onChange} />
            <br />
            <br />
            <Cropper
                ref={(elem: any) => {this.cropper = elem;}}
                src={this.state.src}
                style={{height: 400, width: "100%"}}
                aspectRatio={16 / 9}
                guides={false}
                crop={this.cropImage} />
            </div>
            <div>
            <div className="box" style={{ width: "50%", float: "right" }}>
                <h1>Preview</h1>
                <div className="img-preview" style={{ width: "100%", float: "left", height: 300 }} />
            </div>
            <div className="box" style={{ width: "50%", float: "right" }}>
                <h1>
                <span>Crop</span>
                <button onClick={this.cropImage} style={{ float: "right" }}>
                    Crop Image
                </button>
                </h1>
                <img style={{ width: "100%" }} src={this.state.cropResult}/>
            </div>
            </div>
            <br style={{ clear: "both" }} />
        </div>
        );
    }
}
