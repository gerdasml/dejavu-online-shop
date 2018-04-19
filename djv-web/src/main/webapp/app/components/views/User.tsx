import * as React from "react";

import {Header} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";

interface UserRouteProps { id: number; }
export const User = (props: RouteComponentProps<UserRouteProps>) => (
    <Header size="large">User {props.match.params.id}</Header>
);
