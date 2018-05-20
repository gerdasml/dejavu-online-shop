import { Product } from "./Product";
import { Category } from "./Category";

export interface Discount {
    id: number;
    targetType: DiscountTarget;
    type: DiscountType;
    dateStart: string;
    dateEnd: string;
    amount: number;
    target?: Product | Category;
}

export enum DiscountTarget {
    EVERYTHING  = "Everything",
    CATEGORY    = "Category",
    PRODUCT     = "Product"
}

export enum DiscountType {
    ABSOLUTE = "Absolute",
    PERCENTAGE = "Percentage"
}
