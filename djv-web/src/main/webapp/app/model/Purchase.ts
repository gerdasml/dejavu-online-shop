import { IProduct } from "./Product";

export interface IPurchase {
    amount: number;
    item: IProduct;
    total: number;
    unitPrice: number;
}
