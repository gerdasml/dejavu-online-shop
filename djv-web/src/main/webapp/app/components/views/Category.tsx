import * as React from "react";

import {Header} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";

interface ICategoryRouteProps {
    name: string;
}
export const Category = (props: RouteComponentProps<ICategoryRouteProps>) => (
    <Header size="large">Category {props.match.params.name}</Header>
);
