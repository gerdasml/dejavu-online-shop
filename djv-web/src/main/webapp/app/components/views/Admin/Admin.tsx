import * as React from "react";

import { Button, Icon, message, Upload, Modal } from "antd";
import { RcFile } from "antd/lib/upload/interface";
import {Header} from "semantic-ui-react";
import * as api from "../../../api";

import { ImageCropper } from "../Admin/product/ImageCropper";

const upload = async (info: any) => {
    const img = info.file;
    console.log("UPLOADING", img);
    const response = await api.image.uploadImage(img);
    if (api.isError(response)) {
        info.onError(undefined, response.message);
        return;
    }
    console.log("RESPONSE", response);
    info.onSuccess(response);
};

const dataURItoBlob = (dataURI: string, old: RcFile) => {
    // convert base64/URLEncoded data component to raw binary data held in a string
    let byteString: string;
    if (dataURI.split(',')[0].indexOf('base64') >= 0) {
        byteString = atob(dataURI.split(',')[1]);
    }
    else{
        byteString = unescape(dataURI.split(',')[1]);
    }

    // separate out the mime component
    const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

    // write the bytes of the string to a typed array
    const ia = new Uint8Array(byteString.length);
    for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    return new File([ia], old.name, {type:mimeString, lastModified: old.lastModified});
};

const cloneFile = (old: any, newData: any) => {
    /*const f: any = new File([newData], old.name, {type: old.type});
    f.uid = old.uid;
    return f;*/
    const f = dataURItoBlob(newData, old) as RcFile;
    f.uid = old.uid;
    return f;
};

const handleBefore = async (file: any): Promise<File> => {
    console.log("!@!@!@", file);
    return new Promise<File>((resolve, reject) => {
        let newImg: File = file;
        const onChange = (f: File) => {
            newImg = f;
            console.log("!!!", f);
        };
        Modal.confirm({
            content: <ImageCropper onChange={onChange} image={file} />,
            iconType: "none",
            maskClosable: true,
            style: { top: 0 },
            title: "Crop dat sheet",
            width: "80%",
            onOk () {
                console.log("???", file, cloneFile(file, newImg));
                resolve(cloneFile(file, newImg));
            },
            onCancel () {
                reject(file);
            }
        });
    });
};

export const Admin = () => (
    <div>
        <Header size="large">Admin</Header>
        <Upload onChange={(info: any) => console.log("CHANGED", info)}
                beforeUpload={handleBefore}
                customRequest={upload}
                listType="picture-card">
            <Icon type="plus" />
            <div className="ant-upload-text">Upload</div>
        </Upload>
    </div>
);
