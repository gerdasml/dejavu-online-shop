import * as React from "react";

import "../../../style/profile.css";

import {Button, Form, Grid, Header, Icon, Modal, SemanticICONS} from "semantic-ui-react";

/*export const Profile = () => (
    <Header size="large">Profile asasadasdas</Header>
);*/

export interface IProfileProps {
    prop1: string;
}

interface IProfileState {
    state1: string;
    beingEdited: boolean;
    editIcon: EditIcons;
}

enum EditIcons {
    write="write",
    checkmark="checkmark"
}

export class Profile extends React.Component<IProfileProps, IProfileState> {
    constructor (props: IProfileProps) {
        super(props);
        this.state = {
            beingEdited: false,
            editIcon: EditIcons.write,
            state1: "jokia",
        };
    }

    enableEditing = () => {
        this.setState({
            ...this.state,
            beingEdited: !this.state.beingEdited,
        });
    }

    render () {
        return(
            <Grid>
                <Grid.Column width={6}>
                    <Header size="large">User information {this.state.state1}</Header>
                    <Form id="changeProfile">
                    <fieldset>
                        <div className="profileEditing">
                            {
                                this.state.beingEdited
                                ? <Button.Group id="profileSave">
                                      <Button onClick= {() => this.enableEditing()}>Cancel</Button>
                                      <Button.Or />
                                      <Button positive>Save (notImplYet)</Button>
                                  </Button.Group>
                                : <Button
                                            id="profileEdit"
                                            icon labelPosition="left"
                                            onClick= {() => this.enableEditing()}
                                  >
                                      <Icon name="write" />
                                      Edit
                                  </Button>
                            }
                        </div>
                        <Form.Field inline>
                            <label>First name:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="text" placeholder="First Name"/>
                                : <label className="readOnlyFields" >Cia turi but vardas</label>
                            }
                        </Form.Field>
                        <Form.Field inline>
                            <label>Last name:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="text" placeholder="Last Name"/>
                                : <label className="readOnlyFields" >Cia turi but pavarde</label>
                            }
                        </Form.Field>
                        <Form.Field inline>
                            <label>Email:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="email" placeholder="email"/>
                                : <label className="readOnlyFields" >Cia turi but pastas</label>
                            }
                        </Form.Field>
                        <Form.Field inline>
                            <label>Phone number:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="text" placeholder="+370********"/>
                                : <label className="readOnlyFields" >Cia turi but numeris</label>
                            }
                        </Form.Field>
                        <Form.Field inline>
                            <label>Address:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="text" placeholder="address"/>
                                : <label className="readOnlyFields" >Cia turi but adresas</label>
                            }
                        </Form.Field>
                        <Form.Field inline>
                            <label>Country:</label>
                            {
                                this.state.beingEdited
                                ? <select className="editableFields">
                                     <option value="Lithuania">Lithuania</option>
                                     <option value="United Kingdom">United Kingdom</option>
                                 </select>
                                : <label className="readOnlyFields" >Cia turi but salis</label>
                            }
                        </Form.Field>
                        <hr/>
                        <Modal trigger={
                            <div className="profileEditing">
                                <Button>Change password</Button>
                            </div>
                        } size={"tiny"}>
                            <Modal.Content id="registerModal">
                                <h2>Change password</h2>
                                <Form id="changePassword">
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
                                    <Button>Cancel (notImplYet)</Button>
                                    <Button>Register (notImplYet)</Button>
                                </Form>
                            </Modal.Content>
                        </Modal>
                    </fieldset>
                    </Form>
                </Grid.Column>
                <Grid.Column width={10}>
                <Header size="large">Order History</Header>
                {/*Semantic table here*/}
                </Grid.Column>
            </Grid>
        );
    }
}
