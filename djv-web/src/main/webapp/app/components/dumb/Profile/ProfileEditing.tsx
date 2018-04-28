import * as React from "react";

import "../../../../style/profile.css";

import {Button, Form, Grid, Header, Icon, Modal, SemanticICONS} from "semantic-ui-react";

import ChangePassword from "../../dumb/Profile/ChangePassword";

interface IProfileState {
    beingEdited: boolean;
    editIcon: EditIcons;
}

enum EditIcons {
    write="write",
    checkmark="checkmark"
}

export default class ProfileEditing extends React.Component<{}, IProfileState> {
    constructor () {
        super({});
        this.state = {
            beingEdited: false,
            editIcon: EditIcons.write,
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
            <Form id="changeProfile">
                    <fieldset>
                        <div className="profileEditing">
                            {
                                this.state.beingEdited
                                ? <Button.Group id="profileSave">
                                      <Button negative onClick= {() => this.enableEditing()}>Cancel</Button>
                                      <Button.Or />
                                      <Button positive onClick= {() => this.enableEditing()}>Save</Button>
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
                        <Form.Field inline id="profileFormField">
                            <label>First name:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="text" placeholder="First Name"/>
                                : <label className="readOnlyFields" >Cia turi but vardas</label>
                            }
                        </Form.Field>
                        <Form.Field inline id="profileFormField">
                            <label>Last name:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="text" placeholder="Last Name"/>
                                : <label className="readOnlyFields" >Cia turi but pavarde</label>
                            }
                        </Form.Field>
                        <Form.Field inline id="profileFormField">
                            <label>Email:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="email" placeholder="email"/>
                                : <label className="readOnlyFields" >Cia turi but pastas</label>
                            }
                        </Form.Field>
                        <Form.Field inline id="profileFormField">
                            <label>Phone number:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="text" placeholder="+370********"/>
                                : <label className="readOnlyFields" >Cia turi but numeris</label>
                            }
                        </Form.Field>
                        <Form.Field inline id="profileFormField">
                            <label>Address:</label>
                            {
                                this.state.beingEdited
                                ? <input className="editableFields" type="text" placeholder="address"/>
                                : <label className="readOnlyFields" >Cia turi but adresas</label>
                            }
                        </Form.Field>
                        <Form.Field inline id="profileFormField">
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
                        <ChangePassword/>
                    </fieldset>
                    </Form>
        );
    }

}
