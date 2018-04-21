import * as React from "react";

import {Header} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";

interface CategoryRouteProps {
    name: string;
}
export const Category = (props: RouteComponentProps<CategoryRouteProps>) => (
    <Header size="large">Category {props.match.params.name}</Header>
);
