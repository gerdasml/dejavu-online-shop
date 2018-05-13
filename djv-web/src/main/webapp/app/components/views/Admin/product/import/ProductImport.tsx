import * as React from "react";

import { Icon, Modal, notification, Upload, Button } from "antd";
import { RcFile, UploadChangeParam, UploadFile } from "antd/lib/upload/interface";
import * as api from "../../../../../api";
import { ImportProgress } from "./ImportProgress";

interface ProductImportProps {
    navigateToJob: (jobId: string) => void;
}

export class ProductImport extends React.Component<ProductImportProps, never> {
    upload = async (info: any) => {
        const response = await api.product.importProducts(info.file);
        if (api.isError(response)) {
            info.onError(undefined, response.message);
            return;
        }
        info.onSuccess(response);

        notification.open({
            description: <ImportProgress jobId={response} navigateToJob={() => this.props.navigateToJob(response)} />,
            duration: 0,
            message: "Import status",
        });
    }

    render () {
        return (
            <Upload fileList={[]}
                    customRequest={this.upload.bind(this)}>
                <Button>
                    <Icon type="upload" /> Import
                </Button>
            </Upload>
        );
    }
}
