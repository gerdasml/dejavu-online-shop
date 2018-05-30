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
        this.setState({properties: this.addPropertyIdAsKey(nextProps.properties)});
    }

    addPropertyIdAsKey = (props: ProductProperties[] ) => props.map(x => ({...x, key: x.propertyId}));

    handleRemoveRow (keyToDelete: number) {
        const newProp = this.state.properties.filter(x => x.key !== keyToDelete);
        this.setState({properties: newProp});
        this.updateParent(newProp);
    }
    updatePropertyValue (key: number, value: string) {
        const newProps = this.state.properties.map(x => x.key === key ? {...x, value }: x);
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
                    dataSource={this.state.properties}
                    bordered={true}>
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
                                onChange={s=>this.updatePropertyValue(record.key, s)}
                            />}
                        />
                    <PropertyColumn
                        key="remove"
                        render={(_, record) => <Button className="editButton"
                                                    type="danger"
                                                    onClick={() => this.handleRemoveRow(record.key)}>
                                                    <Icon type="delete" />
                                                </Button>}/>
                </PropertiesTable>
            </div>
        );
    }
}
