import * as React from "react";
import { Dropdown, List } from "semantic-ui-react";

import "../../../../style/style.css";

interface SortProps {
    onChange: (s: string) => void;
}

const sortOptions = [
  {
    text: "Price",
    value: "Price"
  },
  {
    text: "Discount",
    value: "Discount"
  },
  {
    text: "Date",
    value: "Date"
  }
];

export const ProductSort = (props: SortProps) => (
    <Dropdown
        className="product-sort"
        placeholder="Sort by"
        fluid
        selection
        onChange={(e, data) => props.onChange(data.value.toString())}
        options={sortOptions} />
);
