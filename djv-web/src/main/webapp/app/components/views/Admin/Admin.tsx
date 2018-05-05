import * as React from "react";

import { Button, Icon, message, Modal, Upload } from "antd";
import { RcFile } from "antd/lib/upload/interface";
import {Header} from "semantic-ui-react";
import * as api from "../../../api";

import { dataUriToFile } from "../../../utils/file";

import { ImageCropper } from "../Admin/product/ImageCropper";

const upload = async (info: any) => {
    const img = info.file;
    const response = await api.image.uploadImage(img);
    if (api.isError(response)) {
        info.onError(undefined, response.message);
        return;
    }
    info.onSuccess(response);
};

const cloneFile = (old: RcFile, newData: string) => {
    const f = dataUriToFile(newData, old) as RcFile;
    f.uid = old.uid;
    return f;
};

const handleBefore = async (file: RcFile): Promise<RcFile> =>
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
    });

export const Admin = () => (
    <div>
        <Header size="large">Admin</Header>
        <Upload onChange={info => console.log("CHANGED", info)}
                beforeUpload={handleBefore}
                customRequest={upload}
                listType="picture-card">
            <Icon type="plus" />
            <div className="ant-upload-text">Upload</div>
        </Upload>
    </div>
);
