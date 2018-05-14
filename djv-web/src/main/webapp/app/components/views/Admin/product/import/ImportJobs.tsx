import * as React from "react";

import { RouteComponentProps, NavLink } from "react-router-dom";

import { Button, Icon, notification, Spin } from "antd";
import { ImportStatus } from "../../../../../model/Product";

import * as api from "../../../../../api";

interface ImportJobsState {
    importJobs: ImportStatus[];
}

export class ImportJobs extends React.Component<{}, ImportJobsState> {
    state: ImportJobsState = {
        importJobs: []
    };

    async componentWillMount () {
        const response = await api.product.getImportStatuses();
        if (api.isError(response)) {
            notification.error({message: "Failed to fetch import data", description: response.message});
            return;
        }
        this.setState({importJobs: response});
    }

    render () {
        return (
            <Spin spinning={this.state.importJobs === undefined}>
                {this.state.importJobs.map((j, i) => <NavLink to={`/admin/imports/${j.id}`} key={i}>{j.id} {j.startTime}</NavLink>)}
            </Spin>
        );
    }
}
