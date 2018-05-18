import * as React from "react";
import { Dropdown } from "semantic-ui-react";
import { ProductFilter } from "../../../utils/product/productFilter";

export interface FilterProps {
    properties: ProductFilter;
}

// const options = [
//   { key: "angular", text: "Angular", value: "angular" },
//   { key: "css", text: "CSS", value: "css" },
//   { key: "design", text: "Graphic Design", value: "design" },
//   { key: "ember", text: "Ember", value: "ember" },
//   { key: "html", text: "HTML", value: "html" },
//   { key: "ia", text: "Information Architecture", value: "ia" },
//   { key: "javascript", text: "Javascript", value: "javascript" },
//   { key: "mech", text: "Mechanical Engineering", value: "mech" },
//   { key: "meteor", text: "Meteor", value: "meteor" },
//   { key: "node", text: "NodeJS", value: "node" },
//   { key: "plumbing", text: "Plumbing", value: "plumbing" },
//   { key: "python", text: "Python", value: "python" },
//   { key: "rails", text: "Rails", value: "rails" },
//   { key: "react", text: "React", value: "react" },
//   { key: "repair", text: "Kitchen Repair", value: "repair" },
//   { key: "ruby", text: "Ruby", value: "ruby" },
//   { key: "ui", text: "UI Design", value: "ui" },
//   { key: "ux", text: "User Experience", value: "ux" },
// ];

export class Filter extends React.Component<FilterProps, {}> {
    render () {
        const options = this.props.properties.values.map(x => ({key: x, text: x, value: x}));
        return (
            <Dropdown placeholder={this.props.properties.property} fluid multiple selection options={options} />
        );
    }
}
