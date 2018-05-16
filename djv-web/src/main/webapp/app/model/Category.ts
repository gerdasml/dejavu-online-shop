import { CategoryProperties } from "./CategoryProperties";

export interface Category {
    id?: number;
    parentCategoryId?: number;
    identifier: string;
    icon: string;
    name: string;
    properties?: CategoryProperties[];
}
