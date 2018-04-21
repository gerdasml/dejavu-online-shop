import * as React from "react";
import { Icon, Table } from "semantic-ui-react";

import { products } from "../../../data/products";
import { Product } from "../../../model/Product";
import { ProductProperties } from "../../../model/ProductProperties";

interface PropertiesTableProps { product: Product; }

export class PropertiesTable extends React.Component<PropertiesTableProps, {}> {
    constructor (props: PropertiesTableProps) {
        super(props);
    }
    buildCell = (x?: ProductProperties) =>
        x === undefined
        ? <Table.Cell/>
        : <Table.Cell title={x.name + ": " + x.value}><span style={{color:"blue", marginRight:"10px"}}>{x.name}:</span>
        {x.value}</Table.Cell>

    render () {
        const pairs = this.props.product.properties.reduce((res, val, idx, a) => {
            if(idx % 2 === 0) {
                res.push(a.slice(idx, idx+2));
            }
            return res;
        }, []);
        return(
            <Table celled fixed singleLine>
                <Table.Body>
                    {pairs.map((x, i) =>
                    <Table.Row key={i}>
                        {this.buildCell(x[0])}
                        {this.buildCell(x[1])}
                    </Table.Row>)}
                </Table.Body>
            </Table>
        );
    }
}
