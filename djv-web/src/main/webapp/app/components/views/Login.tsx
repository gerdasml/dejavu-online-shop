import * as React from "react";
import { Button, Grid, Icon, Modal, Segment, Form } from "semantic-ui-react";
import {Register} from "./Register";

import "../../../style/login.css";

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
                                Email address:<br/><br/>
                                <input type="email"></input><br/><br/>
                                Password:<br/><br/>
                                <input type="password"></input><br/><br/>
                                <input type="submit" value="Log in"></input>
                                </Form>
                            </Segment>
                        </Grid.Column>
                        <Grid.Column>
                            <Segment basic>
                                <h3> Haven't used before?<br/>Sign up:</h3>
                                <br/><br/><br/>
                                <Register/>
                            </Segment>
                        </Grid.Column>
                    </Grid>
                </Modal.Content>
            </Modal>
        );
    }
}
