import * as React from "react";

import { Button, Icon, Input, Table } from "antd";
import { ColumnProps } from "antd/lib/table";

interface EditableCellProps {
    value: string;
}

interface EditableCellState {
    value: string;
    isBeingEdited: boolean;
}

export class EditableCell extends React.Component<EditableCellProps, EditableCellState> {
    state: EditableCellState = {
        isBeingEdited: false,
        value: this.props.value,
    };

    handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const val = e.target.value;
        this.setState({...this.state, value: val});
    }

    finishEdit = () => {
        this.setState({...this.state, isBeingEdited: false});
        // TODO:  call this.props.onChange in the future
    }

    startEdit = () => {
        this.setState({... this.state, isBeingEdited: true });
    }

    render () {
        const {value, isBeingEdited} = this.state;
        return (
            isBeingEdited ?
            <div className="editable-cell-input-wrapper">
              <Input
                value={value}
                onChange={this.handleChange}
                onPressEnter={this.finishEdit}
              />
              <Icon
                type="check"
                className="editable-cell-icon-check"
                onClick={this.finishEdit}
              />
            </div>
            :
            <div className="editable-cell-text-wrapper">
              {value || ""}
              <Icon
                type="edit"
                className="editable-cell-icon"
                onClick={this.startEdit}
              />
            </div>
        );
    }
}
