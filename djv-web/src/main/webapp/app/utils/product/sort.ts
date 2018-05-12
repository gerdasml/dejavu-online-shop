/*
    Usage:
    import * as sort from "path/to/utils/product/sort";

    const newProducts1 = sort.byPrice(products); // lowest price first
    const newProducts2 = sort.byPrice(products, sort.HIGHEST_FIRST); // highest price first
*/
import { Product } from "../../model/Product";

const sort = <T>(p: T[], comparer: (a: T, b: T) => number, smallestFirst: SortOrder): T[] => {
    const pCopy = [...p];
    pCopy.sort(comparer);
    if (smallestFirst === SortOrder.HIGHEST_FIRST) {
        pCopy.reverse();
    }
    return pCopy;
};

const enum SortOrder {
    LOWEST_FIRST,
    HIGHEST_FIRST
}

export const LOWEST_FIRST = SortOrder.LOWEST_FIRST;
export const HIGHEST_FIRST = SortOrder.HIGHEST_FIRST;

export const byName = (p: Product[], smallestFirst = SortOrder.LOWEST_FIRST): Product[] =>
    sort(p, (a, b) => a.name.localeCompare(b.name), smallestFirst);

export const byPrice = (p: Product[], smallestFirst = SortOrder.LOWEST_FIRST): Product[] =>
    sort(p, (a, b) => a.price - b.price, smallestFirst);
