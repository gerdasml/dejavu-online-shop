import * as React from "react";

import { Button, Icon } from "antd";

export const ProductExport = () => (
    <Button href="/api/product/export" className="productsButton">
        <Icon type="download" /> Export
    </Button>
);
