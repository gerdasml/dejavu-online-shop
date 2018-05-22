/*
    Available filters:
    * Name like s
    * Price in range [a, b]
    * Has properties [{name, value}]
*/
/*
    USAGE:
    import { FilterBuilder, nameLike, priceInRange, hasProperties } from "path/to/utils/products/filter";

    new FilterBuilder()
        .add(nameLike("abc")) // nameLike: string => (product => boolean)
        .add(priceInRange(0, 10))
        .add(hasProperties([...]))
        .apply(products);
*/
import { Product } from "../../model/Product";
import { ProductProperties } from "../../model/ProductProperties";

type Filter = (p: Product) => boolean;

export class FilterBuilder {
    filters: Filter[];
    constructor () {
        this.filters = [];
    }

    add = (filter: Filter): FilterBuilder => {
        this.filters.push(filter);
        return this;
    }

    apply = (products: Product[]): Product[] =>
        products.filter(p => this.filters.every(f => f(p)))
}

export const nameLike = (name: string): Filter =>
    (p => p.name.toLowerCase() === name.toLowerCase()); // TODO: better comparison

export const priceInRange = (low: number, high: number): Filter =>
    (p => p.price >= low && p.price <= high);

export const hasProperties = (props: ProductProperties[]): Filter =>
    (p => props.length === 0 ? true : props.some(
        elem => p.properties.filter(
            x => x.name === elem.name && x.value === elem.value
        ).length > 0
    ));
