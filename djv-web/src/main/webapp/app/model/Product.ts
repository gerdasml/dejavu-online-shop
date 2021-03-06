import { ProductProperties } from "./ProductProperties";
import { Discount } from "./Discount";

export interface Product {
    id?: number;
    identifier?: string;
    name: string;
    skuCode: string;
    mainImageUrl?: string;
    additionalImagesUrls?: string[];
    description: string;
    price: number;
    minimalPrice?: number;
    properties?: ProductProperties[];
    creationDate?: Date;
    categoryId?: number;
    discount?: ProductDiscount;
}

export enum Status {
    ANALYZING = "ANALYZING",
    IMPORTING = "IMPORTING",
    FINISHED  = "FINISHED",
    FAILED    = "FAILED"
}

export interface ImportStatus {
    id: string;
    status: Status;
    successCount: number;
    failureCount: number;
    total: number;
    failedProducts: Product[];
    startTime: Date;
}

interface ProductDiscount extends Discount {
    finalPrice: number;
}

export enum SortBy {
    PRICE = "PRICE",
    CREATION_DATE = "CREATION_DATE"
}

export enum SortDirection {
    ASC = "ASC",
    DESC = "DESC"
}
