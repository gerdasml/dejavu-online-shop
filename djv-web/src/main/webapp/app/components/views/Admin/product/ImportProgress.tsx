import * as React from "react";

import { Alert, Icon, notification, Progress, Spin, Tooltip } from "antd";

import * as api from "../../../../api";
import { Status } from "../../../../model/Product";

interface ImportProgressProps {
    jobId: string;
}

interface ImportProgressState {
    successCount: number;
    failureCount: number;
    error?: string;
}

export class ImportProgress extends React.Component<ImportProgressProps, ImportProgressState> {
    state: ImportProgressState = {
        failureCount: 0,
        successCount: 0,
    };

    componentWillMount () {
        const intervalId = setInterval(async () => {
            const response = await api.product.getImportStatus(this.props.jobId);
            if (api.isError(response)) {
                this.setState({...this.state, error: response.message});
                clearInterval(intervalId);
                return;
            }
            if (response.status === Status.FINISHED) {
                clearInterval(intervalId);
            }
            this.setState({...this.state, successCount: response.successCount, failureCount: response.failureCount});
        }, 100);
    }

    render () {
        const {successCount, failureCount, error} = this.state;
        if (error !== undefined) {
            return error;
        }
        if (successCount + failureCount === 0) {
            return (
                <Spin spinning={true}>
                    Starting import...
                </Spin>
            );
        }
        const successPercent = successCount / (successCount + failureCount) * 100;
        return (
            <Tooltip title={`${successCount} OK / ${failureCount} Failed`}>
                <div className="import-status-text">
                    <span className="success-count">{successCount}</span>
                    /
                    <span className="failure-count">{failureCount}</span></div>
                <Progress percent={100} successPercent={successPercent} showInfo={false} status="exception" />
            </Tooltip>
        );
    }
};
