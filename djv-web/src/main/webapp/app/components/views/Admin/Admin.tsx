import * as React from "react";

import { withRouter, RouteComponentProps } from "react-router-dom";

import {Header} from "semantic-ui-react";

export const Admin = withRouter(class extends React.Component<RouteComponentProps<{}>, never> {
    componentWillMount () {
        this.props.history.push("/admin/orders");
    }
    render () {
        return <Header>Admin panel</Header>;
    }
});
