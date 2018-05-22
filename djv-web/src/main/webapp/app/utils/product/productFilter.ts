import { Category } from "../../model/Category";
import { Product } from "../../model/Product";
import { ProductProperties } from "../../model/ProductProperties";

export interface ProductFilter {
    propertyId: number;
    property: string;
    values: string[];
}

export const getProperties = (category: Category, products: Product[]): ProductFilter[] => {
    if(category === undefined) return [];
    return category.properties.map(prop => ({
        propertyId: prop.propertyId,
        property: prop.name,
        values: products.map(
            prod => prod.properties
                        .filter(p => p.propertyId === prop.propertyId)[0]
                    )
                    .filter(x => x !== undefined)
                    .map (x => x.value)
                    .filter((x, i, arr) => arr.indexOf(x) === i)
    }));
};

export const transform = (filteredData: ProductFilter[]): ProductProperties[] => {
    const result: ProductProperties[] = [];
    filteredData.forEach(
        data => data.values.forEach(
            value => result.push({
                propertyId: data.propertyId,
                name: data.property,
                value
            })
        )
    );
    return result;
};

export const getMax = (products: Product[]): number => {
    if (products.length === 0) return 0;
    const result = products.reduce((prev, curr) => (prev.price > curr.price) ? prev : curr);
    return result.price;
};

export const getMin = (products: Product[]): number => {
    if (products.length === 0) return 0;
    const result = products.reduce((prev, curr) => (prev.price < curr.price) ? prev : curr);
    return result.price;
};
