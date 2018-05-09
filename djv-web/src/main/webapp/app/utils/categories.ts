import { Category } from "../model/Category";
import { CategoryTree } from "../model/CategoryTree";

export const findCategoryFromTree = (tree: CategoryTree[], id: number): Category => {
    const cat: CategoryTree = tree.filter(t => t.category.id === id || findCategoryFromTree(t.children, id))[0];
    if(cat === undefined) {
        return undefined;
    }
    if (cat.category.id === id) {
        return cat.category;
    }
    return findCategoryFromTree(cat.children, id);
};
