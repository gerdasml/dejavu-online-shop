// tslint:disable:max-classes-per-file
import * as React from "react";

import { Button, Icon, Table } from "antd";
import { CategoryProperty } from "../../../../model/CategoryProperties";

import { addKey, WithKey } from "../../../../utils/table";
import { EditableCell } from "../common/EditableCell";

type Property = CategoryProperty & WithKey;

interface PropertiesTableProps {
    properties: CategoryProperty[];
    onChange: (cp: CategoryProperty[]) => void;
}

interface PropertiesTableState {
    properties: Property[];
}

class PropertiesTable extends Table<Property> {}
class PropertyColumn extends Table.Column<Property> {}

export class CategoryPropertiesTable extends React.Component<PropertiesTableProps, PropertiesTableState> {
    state: PropertiesTableState = {
        properties: this.props.properties.map(addKey)
    };

    componentWillReceiveProps (nextProps: PropertiesTableProps) {
        if(nextProps.properties.length !== 0 || this.state.properties.length !== 0) {
            this.setState({properties: nextProps.properties.map(addKey)});
        }
    }

    handleAddRow () {
        const p = this.state.properties;
        const lastKey = p.length === 0 ? -1 : p[p.length-1].key;
        const newProp: Property = {name: "", propertyId: undefined, key: lastKey+1};
        this.setState({properties: [...p, newProp]});
        this.updateParent([...p, newProp]);
    }
    handleRemoveRow (keyToDelete: number) {
        const newProp = this.state.properties.filter(x => x.key !== keyToDelete);
        this.setState({properties: newProp});
        this.updateParent(newProp);
    }
    updateParent (newProps: Property[]) {
        this.props.onChange(newProps.map(x => ({name: x.name, propertyId: x.propertyId})));
    }
    updatePropertyName (index: number, name: string) {
        const newProps = [...this.state.properties];
        newProps[index].name = name;
        this.setState({properties: newProps});
        this.updateParent(newProps);
    }
    render () {
        return (
            <div>
                <h3>Properties:</h3>
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
