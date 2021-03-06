import { CategoryProperty } from "./CategoryProperties";

export interface Category {
    id?: number;
    parentCategoryId?: number;
    identifier: string;
    icon: string;
    name: string;
    properties?: CategoryProperty[];
}

export interface CategoryInfo {
    productCount: number;
    availableProperties: PropertySummary[];
    minPrice: number;
    maxPrice: number;
}

export interface PropertySummary {
    propertyId: number;
    propertyName: string;
    values: string[];
}
