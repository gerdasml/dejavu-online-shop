import * as React from "react";
import { Input, Icon } from "semantic-ui-react";
import { withRouter, RouteComponentProps } from "react-router-dom";
import { Key } from "ts-keycode-enum";
import { stringify } from "query-string";
import { config } from "../../../config";

interface ProductSearchProps {
    fluid?: boolean;
}

interface ProductSearchState {
    query: string;
}

class ProductSearchInput extends React.Component<RouteComponentProps<{}> & ProductSearchProps, ProductSearchState> {
    state: ProductSearchState = {
        query: ""
    };

    handleSearch () {
        this.props.history.push(`/product/search?${stringify({[config.searchQueryParamName]: this.state.query})}`);
        this.setState({query: ""});
    }

    render () {
        return (
            <Input
                icon
                className="search-header"
                placeholder="Search for products..."
                fluid={this.props.fluid}
            >
                <input
                    value={this.state.query}
                    onChange={e => this.setState({query: e.currentTarget.value})}
                    onKeyDown={e => e.keyCode === Key.Enter ? this.handleSearch() : {}}
                />
                <Icon name="search" />
            </Input>
        );
    }
}

export const ProductSearch = withRouter(ProductSearchInput);
