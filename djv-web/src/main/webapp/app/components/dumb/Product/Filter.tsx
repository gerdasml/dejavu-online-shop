import * as React from "react";
import { Dropdown } from "semantic-ui-react";

import "../../../../style/filter.css";
import { PropertySummary } from "../../../model/Category";

export interface FilterProps {
    properties: PropertySummary;
    onSelectChange: (select: string[]) => void;
}

export class Filter extends React.Component<FilterProps, {}> {
    render () {
        const options = this.props.properties.values.map(x => ({key: x, text: x, value: x}));
        return (
            <Dropdown
                className="filter-dropdown"
                placeholder={this.props.properties.propertyName}
                fluid
                multiple
                selection
                options={options}
                onChange={(e, data) => this.props.onSelectChange(data.value as string[])}/>
        );
    }
}
