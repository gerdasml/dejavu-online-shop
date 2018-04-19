import * as React from "react";
import { Icon, Table } from "semantic-ui-react";

import { products } from "../../../data/products";
import { IProduct } from "../../../model/Product";

interface IPropertiesTableProps { product: IProduct; }

export class PropertiesTable extends React.Component<IPropertiesTableProps, {}> {
    constructor (props: IPropertiesTableProps) {
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
