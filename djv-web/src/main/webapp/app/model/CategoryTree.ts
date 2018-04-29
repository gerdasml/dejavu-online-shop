import { Category } from "./Category";

export interface CategoryTree {
    category: Category;
    children?: CategoryTree[];
}
