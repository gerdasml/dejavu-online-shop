import * as React from "react";

import { RouteComponentProps } from "react-router-dom";

import { Button, Icon, notification, Spin } from "antd";
import { ImportStatus, Status } from "../../../../../model/Product";

import * as api from "../../../../../api";

interface ImportJobsProps {
    jobId: string;
}

interface ImportJobState {
    importJob?: ImportStatus;
}

export class ImportJob extends React.Component<RouteComponentProps<ImportJobsProps>, ImportJobState> {
    state: ImportJobState = {};

    async componentWillMount () {
        await this.loadData(this.props);
    }

    async componentWillReceiveProps (nextProps: RouteComponentProps<ImportJobsProps>) {
        await this.loadData(nextProps);
    }

    async loadData (props: RouteComponentProps<ImportJobsProps>) {
        const response = await api.product.getImportStatus(props.match.params.jobId);
        if (api.isError(response)) {
            notification.error({message: "Failed to fetch data", description: response.message});
            return;
        }
        this.setState({importJob: response});
    }

    render () {
        return (
            <Spin spinning={this.state.importJob === undefined}>
                {this.renderStatus()}
            </Spin>
        );
    }

    renderStatus () {
        const { importJob } = this.state;
        if (importJob === undefined) return "";
        if (importJob.status === Status.RUNNING) return <h2>The job is still running...</h2>;
        if (importJob.failureCount === 0) return <h2>There were no failures when executing this job!</h2>;
        return importJob.failedProducts.map((p, i) => <span key={i}>{p.name}</span>);
    }
}