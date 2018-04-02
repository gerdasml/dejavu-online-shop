import * as React from "react";
import { Button, Grid, Icon, Modal, Segment, Form } from "semantic-ui-react";
import {Register} from "./Register";

import "../../../../style/login.css";

export class Login extends React.Component {
    render () {
        return (
            <Modal trigger={<Button icon
                                    size="medium"
            >
                LOG&nbsp;IN
                <br/>
                <Icon name="user circle outline" size="big"/>
            </Button>}>
                <Modal.Content id="loginModal">
                    <Grid columns={2} divided>
                        <Grid.Column>
                            <Segment basic>
                                <h3> Already registered user?<br/>Log in:</h3>
                                <Form>
                                    <Form.Field>
                                        <label>Email address</label>
                                        <input type="email"></input>
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Password</label>
                                        <input type="password"></input>
                                    </Form.Field>
                                    <Button>Log in</Button>
                                </Form>
                            </Segment>
                        </Grid.Column>
                        <Grid.Column>
                            <Segment basic>
                                <h3> Haven't used before?<br/>Sign up:</h3>
                                <div className="register">
                                    <Register/>
                                </div>
                            </Segment>
                        </Grid.Column>
                    </Grid>
                </Modal.Content>
            </Modal>
        );
    }
}
