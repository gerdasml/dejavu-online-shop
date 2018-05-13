import * as React from "react";

import { Alert, Icon, notification, Progress, Spin, Tooltip } from "antd";

import * as api from "../../../../../api";
import { Status } from "../../../../../model/Product";
import { Link } from "react-router-dom";

interface ImportProgressProps {
    jobId: string;
    navigateToJob: () => void;
}

interface ImportProgressState {
    successCount: number;
    failureCount: number;
    error?: string;
    isFinished: boolean;
}

export class ImportProgress extends React.Component<ImportProgressProps, ImportProgressState> {
    state: ImportProgressState = {
        failureCount: 0,
        isFinished: false,
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
                this.setState({...this.state, isFinished: true});
            }
            this.setState({...this.state, successCount: response.successCount, failureCount: response.failureCount});
        }, 100);
    }

    render () {
        const {successCount, failureCount, error, isFinished} = this.state;
        if (error !== undefined) {
            return error;
        }
        if (isFinished) {
            return (
                <span>
                    The import operation is finished.
                    { failureCount > 0
                    ? <a onClick={this.props.navigateToJob}> Review import errors.</a>
                    : "No errors have occurred."
                    }
                </span>
            );
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
}
