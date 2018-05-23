import * as React from "react";
import { Input, Icon } from "semantic-ui-react";

interface ProductSearchProps {
    fluid?: boolean;
}
export const ProductSearch = (props: ProductSearchProps) => (
    <Input
        icon
        className="search-header"
        placeholder="Search for products..."
        fluid={props.fluid}
    >
        <input/>
        <Icon name="search" />
    </Input>
);
