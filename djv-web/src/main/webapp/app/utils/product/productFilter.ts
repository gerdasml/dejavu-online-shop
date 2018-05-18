import * as api from "../../api";
import { Category } from "../../model/Category";
import { Product } from "../../model/Product";

export interface ProductFilter {
    property: string;
    values: string[];
}

export const getProperties = (category: Category, products: Product[]): ProductFilter[] =>
    category.properties.map(prop => ({
        property: prop.name,
        values: products.map(
            prod => prod.properties
                        .filter(p => p.propertyId === prop.propertyId)[0]
                    )
                    .filter(x => x !== undefined)
                    .map (x => x.value)
                    .filter((x, i, arr) => arr.indexOf(x) === i)
    }));
