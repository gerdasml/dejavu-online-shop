import { IProduct } from "./Product";

export interface IPurchase {
    item: IProduct;
    unitPrice: number;
    amount: number;
    total: number;
}
