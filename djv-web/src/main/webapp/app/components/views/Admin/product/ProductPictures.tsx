import * as React from "react";

import { ImageUpload } from "./ImageUpload";

export interface ProductPicturesProps {
    pictures: string[];
    onChange: (s: string[]) => void;
}

export const ProductPictures = (props: ProductPicturesProps) => (
    <ImageUpload
        images={props.pictures}
        onUpdate={imgs => props.onChange(imgs.map(x => x.url))}
    />
);
