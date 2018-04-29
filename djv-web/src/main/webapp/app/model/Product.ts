import { ProductProperties } from "./ProductProperties";

export interface Product {
    id?: number;
    name: string;
    imageUrl?: string;
    description: string;
    price: number;
    properties?: ProductProperties[];
    creationDate?: string;
    categoryID?: number;
}
