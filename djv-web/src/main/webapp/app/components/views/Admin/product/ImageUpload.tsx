import * as React from "react";

import { Icon, Modal, notification, Upload } from "antd";
import { RcFile, UploadChangeParam, UploadFile } from "antd/lib/upload/interface";
import * as api from "../../../../api";

import { blobToFile } from "../../../../utils/file";

import { ImageInfo } from "../../../../model/ImageInfo";
import { ImageCropper } from "../../Admin/product/ImageCropper";

interface ImageUploadProps {
    onUpdate: (files: ImageInfo[]) => void;
    images: string[];
}

interface ImageUploadState {
    imageList: UploadFile[];
}

const cloneFile = (old: RcFile, newData: Blob) => {
    const f = blobToFile(newData, old) as RcFile;
    f.uid = old.uid;
    return f;
};

const urlToUploadFile = (url: string, idx: number): UploadFile => ({
    name: "",
    response: {url},
    size: 0,
    status: "done",
    type: "",
    uid: -idx,
    url,
});

export class ImageUpload extends React.Component<ImageUploadProps, ImageUploadState> {
    state: ImageUploadState = {
        imageList: this.props.images.map(urlToUploadFile)
    };

    componentWillReceiveProps (nextProps: ImageUploadProps) {
        if(nextProps.images.length === 0 || this.state.imageList.length === 0) {
            this.setState({imageList: nextProps.images.map(urlToUploadFile)});
        }
    }

    upload = async (info: any) => {
        const img = info.file;
        const response = await api.image.uploadImage(img);
        if (api.isError(response)) {
            info.onError(undefined, response.message);
            return;
        }
        info.onSuccess(response);
    }

    handleChange = (info: UploadChangeParam) => {
        if (info.file.status === "error") {
            notification.error({message: "Failed to upload image", description: info.file.response});
            this.setState({imageList: info.fileList.filter(x => x.uid !== info.file.uid)});
            return;
        }
        const isGood = (f: UploadFile) => f.response as ImageInfo;
        const goodFiles = info.fileList.filter(isGood);
        this.props.onUpdate(goodFiles.map(x => x.response));
        this.setState({imageList: info.fileList});
    }

    handleBefore = async (file: RcFile): Promise<RcFile> =>
        new Promise<RcFile>((resolve, reject) => {
            if (!file.name.endsWith(".jpg") && !file.name.endsWith("jpeg") && !file.name.endsWith("png")) {
                reject(file);
                return;
            }
            let newImgProvider: () => Blob = () => file;
            const onChange = (f: () => Blob) => newImgProvider = f;
            Modal.confirm({
                content: <ImageCropper onChange={onChange} image={file} />,
                iconType: "none",
                maskClosable: true,
                style: { top: 0 },
                width: "80%",
                onOk () {
                    resolve(cloneFile(file, newImgProvider()));
                },
                onCancel () {
                    reject(file);
                }
            });
        })

    render () {
        return (
            <Upload fileList={this.state.imageList}
                    onChange={this.handleChange.bind(this)}
                    beforeUpload={this.handleBefore.bind(this)}
                    customRequest={this.upload.bind(this)}
                    listType="picture-card"
                    accept=".jpg,.jpeg,.png"
            >
                <Icon type="plus" />
                <div className="ant-upload-text">Upload</div>
            </Upload>
        );
    }
}
