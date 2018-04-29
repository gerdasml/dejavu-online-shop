import { ProductProperties } from "./ProductProperties";

export interface Product {
    id?: number;
    name: string;
    mainImageUrl?: string;
    additionalImagesUrls?: string[];
    description: string;
    price: number;
    properties?: ProductProperties[];
    creationDate?: string;
    categoryId?: number;
}
