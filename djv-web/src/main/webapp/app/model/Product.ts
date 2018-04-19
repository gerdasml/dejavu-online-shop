import { IProductProperties } from "./ProductProperties";

export interface IProduct {
    name: string;
    imageUrl: string;
    description: string;
    price: number;
    properties: IProductProperties[];
    // TODO: update when product functionality is finished in back end
}
