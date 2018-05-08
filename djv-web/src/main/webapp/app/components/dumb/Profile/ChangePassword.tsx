// TODO: reikia padaryti veikiancia password'o keitimo sistema su irasymu i backa.
import * as React from "react";

import {Button, Form, Icon, Modal} from "semantic-ui-react";

import "../../../../style/profile.css";

interface ChangePasswordState {
    modalOpen: boolean;
}

export class ChangePassword extends React.Component<{}, ChangePasswordState> {
    state = { modalOpen: false };

    handleOpen = () => this.setState({ ...this.state, modalOpen: true });

    handleClose = () => this.setState({ ...this.state, modalOpen: false });

    render () {
        return(
            <Modal id="changePassword"
                trigger={
                <div className="profileEditing">
                    <Button onClick={this.handleOpen}>Change password</Button>
                </div>
                }
                size={"tiny"}
                open={this.state.modalOpen}
                onClose={this.handleClose}
                >
                <Modal.Header><h2>Change password</h2></Modal.Header>
                <Modal.Content id="registerModal">
                    <Form>
                        <Form.Field inline>
                            <label>Current password:</label>
                            <input type="password" placeholder="********"/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>New password:</label>
                            <input type="password" placeholder="********"/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Repeat password:</label>
                            <input type="password" placeholder="********"/>
                        </Form.Field>
                        <Button onClick={this.handleClose} color="red" inverted>
                            <Icon name="remove" />
                            Cancel
                        </Button>
                        <Button onClick={this.handleClose} color="green" inverted>
                            <Icon name="checkmark" />
                            Save
                        </Button>
                    </Form>
                </Modal.Content>
            </Modal>
        );
    }
}
