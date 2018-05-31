import * as React from "react";
import { Dropdown, List } from "semantic-ui-react";

import "../../../../style/style.css";
import { SortDirection, SortBy } from "../../../model/Product";
import { fromString } from "../../../utils/enum";

interface SortProps {
    onChange: (sortBy: SortBy, sortDir: SortDirection) => void;
    sortBy: SortBy;
    sortDir: SortDirection;
}

const sortOptions = [
    {
        text: "Price (lowest to highest)",
        value: SortBy.PRICE.toString() + "|" + SortDirection.ASC.toString()
    },
    {
        text: "Price (highest to lowest)",
        value: SortBy.PRICE.toString() + "|" + SortDirection.DESC.toString()
    },
    {
        text: "Creation date (oldest to newest)",
        value: SortBy.CREATION_DATE.toString() + "|" + SortDirection.ASC.toString()
    },
    {
        text: "Creation date (newest to oldest)",
        value: SortBy.CREATION_DATE.toString() + "|" + SortDirection.DESC.toString()
    },
    // TODO: DISCOUNTS
];

const extractEnums = (val: string, fn: (sb: SortBy, sd: SortDirection) => void) => {
    const parts = val.split("|");
    const sortBy = fromString(SortBy, parts[0]);
    const sortDir = fromString(SortDirection, parts[1]);
    fn(sortBy, sortDir);
};

export const ProductSort = (props: SortProps) => (
    <Dropdown
        className="product-sort"
        placeholder="Sort by"
        fluid
        selection
        value={props.sortBy.toString()+"|"+props.sortDir.toString()}
        onChange={(e, data) => extractEnums(data.value.toString(), props.onChange)}
        options={sortOptions} />
);
