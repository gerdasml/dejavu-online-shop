import * as React from "react";
import { Dropdown, List } from "semantic-ui-react";

import "../../../../style/style.css";

const friendOptions = [
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

export const ProductSort = () => (
    <Dropdown
        className="product-sort"
        placeholder="Sort by"
        fluid
        selection
        options={friendOptions} />
);
