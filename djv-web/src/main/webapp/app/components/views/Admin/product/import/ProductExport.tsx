import * as React from "react";

import { Button, Icon } from "antd";

export const ProductExport = () => (
    <Button href="/api/product/export">
        <Icon type="download" /> Export
    </Button>
);
