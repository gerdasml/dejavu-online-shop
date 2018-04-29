import * as React from "react";

import {Header, Input} from "semantic-ui-react";

export const ProductName = () => (
    <Input
        label={{ basic: true, content: "Product name:" }}
        labelPosition="left"
        placeholder="Enter product name..."
  />
);
