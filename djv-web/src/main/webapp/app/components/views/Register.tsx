import * as React from "react";

import {Button, Form, Modal } from "semantic-ui-react";

import "../../../style/login.css";

export class Register extends React.Component {

    render () {
        return (
            <Modal trigger={<Button>Register</Button>} size={"tiny"}>
                <Modal.Content id="registerModal">
                    <h2>Registration</h2>
                    <Form>
                        <Form.Field inline>
                            <label>First name</label>
                            <input type="text"/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Last name</label>
                            <input type="text"/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Email</label>
                            <input type="email"/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Phone number</label>
                            <input type="text"/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Address</label>
                            <input type="text"/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Country</label>
                            <select>
                                <option value="Lithuania">Lithuania</option>
                                <option value="United Kingdom">United Kingdom</option>
                            </select>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Password</label>
                            <input type="password"/>
                        </Form.Field>
                        <Form.Field inline>
                            <label>Repeat password</label>
                            <input type="password"/>
                        </Form.Field>
                        <Button>Cancel</Button>
                        <Button>Register</Button>
                    </Form>
                </Modal.Content>
            </Modal>
        );
    }
}
