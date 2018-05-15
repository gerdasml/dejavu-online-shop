// tslint:disable:max-classes-per-file
import * as React from "react";

import { Button, Icon, Table } from "antd";
import { ProductProperties } from "../../../../model/ProductProperties";

import { addKey, WithKey } from "../../../../utils/table";
import { EditableCell } from "./EditableCell";

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

    handleAddRow () {
        const p = this.state.properties;
        const lastKey = p.length === 0 ? -1 : p[p.length-1].key;
        const newProp: Property = {name: "", value: "", key: lastKey+1};
        this.setState({properties: [...p, newProp]});
        this.updateParent([...p, newProp]);
    }
    handleRemoveRow (keyToDelete: number) {
        const newProp = this.state.properties.filter(x => x.key !== keyToDelete);
        this.setState({properties: newProp});
        this.updateParent(newProp);
    }
    updatePropertyName (index: number, name: string) {
        const newProps = [...this.state.properties];
        newProps[index].name = name;
        this.setState({properties: newProps});
        this.updateParent(newProps);
    }
    updatePropertyValue (index: number, value: string) {
        const newProps = [...this.state.properties];
        newProps[index].value = value;
        this.setState({properties: newProps});
        this.updateParent(newProps);
    }
    updateParent (newProps: Property[]) {
        this.props.onChange(newProps.map(x => ({name: x.name, value: x.value})));
    }
    render () {
        return (
            <div>
                <PropertiesTable pagination={false} dataSource={this.state.properties}>
                    <PropertyColumn
                        key="name"
                        title="Name"
                        render={(text, record, index) =>
                            <EditableCell
                                value={record.name}
                                onChange={s=>this.updatePropertyName(index, s)}
                            />}
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
                <Button onClick={this.handleAddRow.bind(this)}>Add new property</Button>
            </div>
        );
    }
}
