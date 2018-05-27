// tslint:disable:max-classes-per-file
import * as React from "react";

import { Button, Icon, Table } from "antd";
import { ProductProperties } from "../../../../model/ProductProperties";

import { addKey, WithKey } from "../../../../utils/table";
import { EditableCell } from "../common/EditableCell";
import { config } from "../../../../config";

import "../../../../../style/admin/products.css";

type Property = ProductProperties & WithKey;

interface PropertiesTableProps {
    properties: ProductProperties[];
    onChange: (pp: ProductProperties[]) => void;
}

interface PropertiesTableState {
    properties: Property[];
}

class PropertiesTable extends Table<Property> {}
class PropertyColumn extends Table.Column<Property> {}

export class ProductPropertiesTable extends React.Component<PropertiesTableProps, PropertiesTableState> {
    state: PropertiesTableState = {
        properties: this.props.properties.map(addKey)
    };

    componentWillReceiveProps (nextProps: PropertiesTableProps) {
        if(nextProps.properties.length === 0 || this.state.properties.length === 0) {
            this.setState({properties: nextProps.properties.map(addKey)});
        }
    }
    handleRemoveRow (keyToDelete: number) {
        const newProp = this.state.properties.filter(x => x.key !== keyToDelete);
        this.setState({properties: newProp});
        this.updateParent(newProp);
    }
    updatePropertyValue (index: number, value: string) {
        const newProps = [...this.state.properties];
        newProps[index].value = value;
        this.setState({properties: newProps});
        this.updateParent(newProps);
    }
    updateParent (newProps: Property[]) {
        this.props.onChange(newProps.map(x => ({propertyId: x.propertyId, name: x.name, value: x.value})));
    }
    render () {
        return (
            <div>
                <PropertiesTable className="propertiesTable"
                    scroll={{x: config.adminTableScrollWidth.properties}}
                    pagination={false}
                    dataSource={this.state.properties}>
                    <PropertyColumn
                        key="name"
                        title="Name"
                        dataIndex="name"
                        />
                    <PropertyColumn
                        key="value"
                        title="Value"
                        render={(text, record, index) =>
                            <EditableCell
                                value={record.value}
                                onChange={s=>this.updatePropertyValue(index, s)}
                            />}
                        />
                    <PropertyColumn
                        key="remove"
                        render={(_, record) => <Button
                                                    type="danger"
                                                    onClick={() => this.handleRemoveRow(record.key)}>
                                                    <Icon type="delete" />
                                                </Button>}/>
                </PropertiesTable>
            </div>
        );
    }
}
