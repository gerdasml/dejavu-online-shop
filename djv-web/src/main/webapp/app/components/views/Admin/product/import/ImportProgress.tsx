import * as React from "react";

import { Progress, Spin, Tooltip } from "antd";

import * as api from "../../../../../api";
import { Status } from "../../../../../model/Product";

const REFRESH_STATUS_INTERVAL = 500;

interface ImportProgressProps {
    jobId: string;
    navigateToJob: () => void;
}

interface ImportProgressState {
    successCount: number;
    failureCount: number;
    total: number;
    error?: string;
    status?: Status;
}

export class ImportProgress extends React.Component<ImportProgressProps, ImportProgressState> {
    state: ImportProgressState = {
        failureCount: 0,
        successCount: 0,
        total: 0,
    };

    componentWillMount () {
        const intervalId = setInterval(async () => {
            const response = await api.product.getImportStatus(this.props.jobId);
            if (api.isError(response)) {
                this.setState({...this.state, error: response.message});
                clearInterval(intervalId);
                return;
            }
            if (response.status === Status.FINISHED || response.status === Status.FAILED) {
                clearInterval(intervalId);
            }
            this.setState({
                ...this.state,
                failureCount: response.failureCount,
                status: response.status,
                successCount: response.successCount,
                total: response.total,
            });
        }, REFRESH_STATUS_INTERVAL);
    }

    render () {
        const {successCount, failureCount, error, status, total} = this.state;
        if (error !== undefined) {
            return error;
        }
        if (status === undefined) {
            return (
                <Spin spinning={true}>
                    Starting import...
                </Spin>
            );
        }
        if (status === Status.FAILED) {
            return "The import job failed unexpectedly...";
        }
        if (status === Status.ANALYZING) {
            return (
                <Spin spinning={true}>
                    Analyzing file: {total} products found
                </Spin>
            );
        }
        const donePercent = (successCount + failureCount) / total * 100;
        const successPercent = successCount / total * 100;
        return (
            <div>
                { status === Status.FINISHED
                ?
                <span>
                    The import operation is finished.
                    { failureCount > 0
                    ? <a onClick={this.props.navigateToJob}> Review import errors.</a>
                    : "No errors have occurred."
                    }
                </span>
                : ""
                }
                <Tooltip title={`${successCount} OK / ${failureCount} Failed`}>
                    {/* <div className="import-status-text">
                        <span className="success-count">{successCount}</span>
                        /
                        <span className="failure-count">{failureCount}</span></div> */}
                    <Progress
                        percent={donePercent}
                        successPercent={successPercent}
                        showInfo={false}
                        status="exception"
                    />
                </Tooltip>
            </div>
        );
    }
}
