import * as React from "react";


import { Header } from "semantic-ui-react";




import { ProductContainer } from "../../smart/Product/ProductContainer";

export class Home extends React.Component< {}, never> {
    render () {
        return (
            <ProductContainer
                query={{}}
                noResultsMessage={<Header size="huge">No products were found</Header>}
            />
        );
    }
}
