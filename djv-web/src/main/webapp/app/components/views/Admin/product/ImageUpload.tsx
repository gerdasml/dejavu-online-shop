import * as React from "react";

import { Button, Icon, message, Modal, Upload } from "antd";
import { RcFile, UploadChangeParam, UploadFile } from "antd/lib/upload/interface";
import * as api from "../../../../api";

import { dataUriToFile } from "../../../../utils/file";

import { ImageInfo } from "../../../../model/ImageInfo";
import { ImageCropper } from "../../Admin/product/ImageCropper";

interface ImageUploadProps {
    onUpdate: (files: ImageInfo[]) => void;
}

const cloneFile = (old: RcFile, newData: string) => {
    const f = dataUriToFile(newData, old) as RcFile;
    f.uid = old.uid;
    return f;
};

export class ImageUpload extends React.Component<ImageUploadProps, never> {
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
        const isGood = (f: UploadFile) => f.response as ImageInfo;
        const goodFiles = info.fileList.filter(isGood);
        this.props.onUpdate(goodFiles.map(x => x.response));
    }

    handleBefore = async (file: RcFile): Promise<RcFile> =>
        new Promise<RcFile>((resolve, reject) => {
            let newImg: string = file.toString();
            const onChange = (f: string) => newImg = f;
            Modal.confirm({
                content: <ImageCropper onChange={onChange} image={file} />,
                iconType: "none",
                maskClosable: true,
                style: { top: 0 },
                width: "80%",
                onOk () {
                    resolve(cloneFile(file, newImg));
                },
                onCancel () {
                    reject(file);
                }
            });
        })

    render () {
        return (
            <Upload onChange={this.handleChange.bind(this)}
                    beforeUpload={this.handleBefore.bind(this)}
                    customRequest={this.upload.bind(this)}
                    listType="picture-card">
                <Icon type="plus" />
                <div className="ant-upload-text">Upload</div>
            </Upload>
        );
    }
}
