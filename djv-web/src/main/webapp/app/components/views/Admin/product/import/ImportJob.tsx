import * as React from "react";

import { RouteComponentProps } from "react-router-dom";

import { Divider, notification, Spin } from "antd";
import { ImportStatus, Product, Status } from "../../../../../model/Product";

import * as api from "../../../../../api";
import { ProductForm } from "../ProductForm";
import { CategoryTree } from "../../../../../model/CategoryTree";

interface ImportJobsProps {
    jobId: string;
}

interface ImportJobState {
    importJob?: ImportStatus;
    categories?: CategoryTree[];
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
        const [importStatusResponse, categoriesResponse] =
        await Promise.all([api.product.getImportStatus(props.match.params.jobId), api.category.getCategoryTree()]);
        if (api.isError(importStatusResponse)) {
            notification.error({message: "Failed to fetch data", description: importStatusResponse.message});
            return;
        }
        if(api.isError(categoriesResponse)) {
            notification.error({message: "Failed to fetch category data", description: categoriesResponse.message});
            return;
        }
        this.setState({
            importJob: importStatusResponse,
            categories: categoriesResponse
        });
    }

    async handleSubmit (submittedProduct: Product) {
        const newProducts = this.state.importJob.failedProducts.filter(x => x !== submittedProduct);
        const newStatus = {...this.state.importJob, failedProducts: newProducts};
        const response = await api.product.updateImportStatus(this.props.match.params.jobId, newStatus);
        if (api.isError(response)) {
            notification.error({message: "Failed to add product", description: response.message});
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
        if (importJob.status === Status.FAILED) return <h2>The job has failed unexpectedly...</h2>;
        if (importJob.status === Status.ANALYZING) return <h2><Spin />The file is still being analyzed...</h2>;
        if (importJob.status === Status.IMPORTING) return <h2><Spin />The products are still being imported...</h2>;
        if (importJob.failureCount === 0) return <h2>There were no failures when executing this job!</h2>;
        if (importJob.failedProducts.length === 0) return <h2>All import errors have been resolved</h2>;
        return importJob.failedProducts.map((p, i) =>
            <div>
                <ProductForm
                    key={i}
                    product={p}
                    onSubmit={() => this.handleSubmit(p)}
                    categories={this.state.categories}
                />
                <Divider />
            </div>
        );
    }
}
