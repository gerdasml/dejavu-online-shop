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
}
