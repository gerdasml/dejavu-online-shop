import { ProductProperties } from "./ProductProperties";

export interface Product {
    id?: number;
    identifier?: string;
    name: string;
    mainImageUrl?: string;
    additionalImagesUrls?: string[];
    description: string;
    price: number;
    properties?: ProductProperties[];
    creationDate?: Date;
    categoryId?: number;
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
