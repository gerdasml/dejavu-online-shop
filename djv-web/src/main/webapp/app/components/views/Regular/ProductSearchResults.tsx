import * as React from "react";


import { Header } from "semantic-ui-react";



import { config } from "../../../config";

import { ProductContainer } from "../../smart/Product/ProductContainer";
import { RouteComponentProps } from "react-router";

import { parse } from "query-string";
import { NotFound } from "../NotFound";

export class ProductSearchResults extends React.Component< RouteComponentProps<{}>, never> {
    render () {
        const query = parse(this.props.location.search);
        const searchPhrase = query[config.searchQueryParamName];
        if (searchPhrase === undefined) {
            return <NotFound />;
        }
        return (
            <span>
                <Header size="huge">Search results for <span className="search-query">{searchPhrase}</span></Header>
                <ProductContainer
                    query={{name: searchPhrase}}
                    noResultsMessage={<Header size="large">No results</Header>}
                />
            </span>
        );
    }
}
