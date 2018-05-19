import * as React from "react";
import { Dropdown } from "semantic-ui-react";
import { ProductFilter } from "../../../utils/product/productFilter";

export interface FilterProps {
    properties: ProductFilter;
    onSelectChange: (select: string[]) => void;
}

export class Filter extends React.Component<FilterProps, {}> {
    render () {
        const options = this.props.properties.values.map(x => ({key: x, text: x, value: x}));
        return (
            <Dropdown
                placeholder={this.props.properties.property}
                fluid
                multiple
                selection
                options={options}
                onChange={(e, data) => this.props.onSelectChange(data.value as string[])}/>
        );
    }
}
