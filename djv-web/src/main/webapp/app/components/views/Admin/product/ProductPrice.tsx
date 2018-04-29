import * as React from "react";

import {Header, Input} from "semantic-ui-react";

export const ProductPrice = () => (
    <Input
        icon="tags"
        iconPosition="left"
        label={{ tag: true, content: "Add price", active: true }}
        labelPosition="right"
        placeholder="Enter product price..."
    />
);
