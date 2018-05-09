import * as React from "react";

import {Button, Form, Icon, Modal, Message} from "semantic-ui-react";

import * as api from "../../../api";

import "../../../../style/profile.css";

interface ChangePasswordState {
    modalOpen: boolean;
    currentPassword?: string;
    newPassword?: string;
    repeatedPassword?: string;
    error?: string;
    isLoading: boolean;
}

export class ChangePassword extends React.Component<{}, ChangePasswordState> {
    state: ChangePasswordState = {
        isLoading: false,
        modalOpen: false,
    };

    handleOpen = () => this.setState({ ...this.state, modalOpen: true });

    handleClose = () => this.setState({
        ...this.state,
        currentPassword: undefined,
        error: undefined,
        isLoading: false,
        modalOpen: false,
        newPassword: undefined,
        repeatedPassword: undefined,
    })

    validate () {
        let error;
        if (!this.state.currentPassword ||
            !this.state.newPassword ||
            !this.state.repeatedPassword) {
            error = "Please fill out all fields";
        } else if (this.state.newPassword !== this.state.repeatedPassword) {
            error = "The new and repeated passwords do not match";
        }
        this.setState({...this.state, error});
        return error === undefined;
    }

    async updatePassword () {
        const response = await api.auth.changePassword(this.state.currentPassword, this.state.newPassword);
        if (api.isError(response)) {
            this.setState({...this.state, error: response.message});
            return;
        }
        this.setState({...this.state, error: undefined});
        this.handleClose();
    }

    async handleSubmit () {
        if (!this.validate()) {
            return;
        }
        this.setState({...this.state, isLoading: true});
        await this.updatePassword();
        this.setState({...this.state, isLoading: false});
    }

    render () {
        return(
            <Modal id="changePassword"
                trigger={
                <div className="profileEditing">
                    <Button onClick={this.handleOpen.bind(this)}>Change password</Button>
                </div>
                }
                size={"tiny"}
                open={this.state.modalOpen}
                onClose={this.handleClose}
                >
                <Modal.Header><h2>Change password</h2></Modal.Header>
                <Modal.Content id="registerModal">
                    <Form loading={this.state.isLoading} error={this.state.error !== undefined}>
                        <Form.Field inline>
                            <label>Current password:</label>
                            <input
                                type="password"
                                placeholder="********"
                                onChange={e => this.setState({...this.state, currentPassword: e.target.value})}
                                value={this.state.currentPassword}
                            />
                        </Form.Field>
                        <Form.Field inline>
                            <label>New password:</label>
                            <input
                                type="password"
                                placeholder="********"
                                onChange={e => this.setState({...this.state, newPassword: e.target.value})}
                                value={this.state.newPassword}
                            />
                        </Form.Field>
                        <Form.Field inline>
                            <label>Repeat password:</label>
                            <input
                                type="password"
                                placeholder="********"
                                onChange={e => this.setState({...this.state, repeatedPassword: e.target.value})}
                                value={this.state.repeatedPassword}
                            />
                        </Form.Field>
                        <Message
                            error
                            content={this.state.error}
                        />
                        <Button onClick={this.handleClose.bind(this)} color="red" inverted>
                            <Icon name="remove" />
                            Cancel
                        </Button>
                        <Button onClick={this.handleSubmit.bind(this)} color="green" inverted>
                            <Icon name="checkmark" />
                            Save
                        </Button>
                    </Form>
                </Modal.Content>
            </Modal>
        );
    }
}
