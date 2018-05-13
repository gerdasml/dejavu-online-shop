import * as React from "react";

import { Icon, Modal, notification, Upload, Button } from "antd";
import { RcFile, UploadChangeParam, UploadFile } from "antd/lib/upload/interface";
import * as api from "../../../../../api";

export const ProductExport = () => (
    <Button href="/api/product/export">
        <Icon type="download" /> Export
    </Button>
);
