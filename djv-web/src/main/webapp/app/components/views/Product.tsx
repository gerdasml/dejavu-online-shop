import * as React from "react";

import {Header} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";

interface IProductRouteProps { id: number; }
export const Product = (props: RouteComponentProps<IProductRouteProps>) => (
    <Header size="large">Product {props.match.params.id}</Header>
);
