import * as React from "react";

import { Button, Icon, Input, Table } from "antd";
import { ProductProperties } from "../../../../model/ProductProperties";

import { addKey, WithKey } from "../../../../utils/table";
import { EditableCell } from "./EditableCell";

type Property = ProductProperties & WithKey;

interface PropertiesTableProps {
    properties: ProductProperties[];
}

interface PropertiesTableState {
    properties: Property[];
}

class PropertiesTable extends Table<Property> {}

// tslint:disable-next-line:max-classes-per-file
class PropertyColumn extends Table.Column<Property> {}

// tslint:disable-next-line:max-classes-per-file
export class ProductPropertiesTable extends React.Component<PropertiesTableProps, PropertiesTableState> {
    state: PropertiesTableState = {
        properties: this.props.properties.map(addKey)
    };
    render () {
        return (
            <PropertiesTable pagination={false} dataSource={this.state.properties}>
                <PropertyColumn
                    key="name"
                    title="Name"
                    render={(text, record, index) => <EditableCell value={record.name}/>} />
                <PropertyColumn
                    key="value"
                    title="Value"
                    render={(text, record, index) => <EditableCell value={record.value}/>} />
                <PropertyColumn
                    key="remove"
                    render={(_, record) => <Button type="danger"><Icon type="delete" /></Button>}/>
            </PropertiesTable>
        );
    }
}
