<<<<<<< HEAD
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
=======
import {Product} from "./Product";
import {Category} from "./Category";

enum DiscountTarget {
    EVERYTHING = "EVERYTHING",
    CATEGORY   = "CATEGORY",
    PRODUCT    = "PRODUCT"
}

enum DiscountType {
    ABSOLUTE   = "ABSOLUTE",
    PERCENTAGE = "PERCENTAGE"
}

export interface Discount {
    targetType: DiscountTarget;
    target?: Product | Category;
    type: DiscountType;
    value: number;
    activeFrom: Date;
    activeTo: Date;
>>>>>>> master
}
