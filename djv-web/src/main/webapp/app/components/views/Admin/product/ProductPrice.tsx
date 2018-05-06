import * as React from "react";

import { Icon, Input } from "antd";

export const ProductPrice = () => (
    <Input
        placeholder="Enter product price"
        prefix={<Icon type="tag" />}
    />
);
