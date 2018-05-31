import * as React from "react";

import { Card, Icon, Pagination } from "semantic-ui-react";

import { Cart } from "../../../model/Cart";
import { Product, SortBy, SortDirection } from "../../../model/Product";
import { ProductCard } from "../Home/ProductCard";

import "../../../../style/filter.css";

import { config } from "../../../config";
import { ProductSort } from "./ProductSort";

interface ProductListProps {
    totalProductCount: number;
    products: Product[];
    onPageChange: (pageNumber: number) => void;
    activePage: number;
    cart: Cart;
    onAddToCart: (product: Product) => void;
    onSortChange: (sortBy: SortBy, sortDir: SortDirection) => void;
    sortBy: SortBy;
    sortDir: SortDirection;
}

export const ProductList = (props: ProductListProps) => (
    <div>
        <ProductSort onChange={props.onSortChange} sortBy={props.sortBy} sortDir={props.sortDir} />
        <Card.Group itemsPerRow={5} doubling>
            {props.products.map((x, i) =>
                <ProductCard
                key={i}
                product={x}
                onProductAddToCart={props.onAddToCart}
                />
            )}
        </Card.Group>
        { Math.ceil(props.totalProductCount / config.productsPerPage) > 1
        ?
        <div className="pagination-father">
            <Pagination
                id="products-pagination"
                floated="left"
                activePage={props.activePage}
                onPageChange={((e, x) => props.onPageChange(x.activePage as number))}
                ellipsisItem={{ content: <Icon name="ellipsis horizontal" />, icon: true }}
                firstItem={{ content: <Icon name="angle double left" />, icon: true }}
                lastItem={{ content: <Icon name="angle double right" />, icon: true }}
                prevItem={{ content: <Icon name="angle left" />, icon: true }}
                nextItem={{ content: <Icon name="angle right" />, icon: true }}
                totalPages={Math.ceil(props.totalProductCount / config.productsPerPage)}
            />
        </div>
        : ""
        }
    </div>
);
