import * as React from "react";

import { Button, Icon, notification, Upload } from "antd";
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
            placement: "bottomRight"
        });
    }

    render () {
        return (
            <Upload fileList={[]}
                    customRequest={this.upload.bind(this)}>
                <Button className="productsButton">
                    <Icon type="upload" /> Import
                </Button>
            </Upload>
        );
    }
}
