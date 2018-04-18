import { Product } from "./Product";

export interface Purchase {
    amount: number;
    item: Product;
    total: number;
    unitPrice: number;
}
