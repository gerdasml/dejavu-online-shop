import { ProductProperties } from "./ProductProperties";

export interface Product {
    name: string;
    imageUrl: string;
    description: string;
    price: number;
    properties: ProductProperties[];
    // TODO: update when product functionality is finished in back end
}
