import * as React from "react";

import {Header} from "semantic-ui-react";

import { ImageUpload } from "./product/ImageUpload";

export const Admin = () => (
    <div>
        <Header size="large">Admin</Header>
        <ImageUpload onUpdate={imgs => console.log(imgs)} />
    </div>
);
