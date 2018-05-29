import * as React from "react";

import { Button, Icon, notification, Upload } from "antd";
import * as api from "../../../../../api";
import { ImportProgress } from "./ImportProgress";

interface ProductImportProps {
    navigateToJob: (jobId: string) => void;
}

export class ProductImport extends React.Component<ProductImportProps, never> {
    upload = async (info: any) => {
        if (!info.file.name.endsWith(".xlsx")) {
            info.onError(undefined, "Unsupported file type");
            return;
        }
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
                    customRequest={this.upload.bind(this)}
                    accept=".xlsx"
            >
                <Button>
                    <Icon type="upload" /> Import
                </Button>
            </Upload>
        );
    }
}
