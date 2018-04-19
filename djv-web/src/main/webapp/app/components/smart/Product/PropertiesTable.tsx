import * as React from "react";
import { Icon, Table } from "semantic-ui-react";

import { products } from "../../../data/products";
import { Product } from "../../../model/Product";

interface PropertiesTableProps { product: Product; }

export class PropertiesTable extends React.Component<PropertiesTableProps, {}> {
    constructor (props: PropertiesTableProps) {
        super(props);
    }
    render () {
        return(
            <Table celled fixed singleLine>
                <Table.Body>
                    {this.props.product.properties.map((x, i) =>
                    <Table.Row key={i}>
                        <Table.Cell>{x.name}</Table.Cell>
                        <Table.Cell>{x.value}</Table.Cell>
                    </Table.Row>)}
                </Table.Body>
            </Table>
        );
    }
}
