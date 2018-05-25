import * as React from "react";

import "../../../../style/profile.css";

import {Button, Form, Icon} from "semantic-ui-react";

import * as api from "../../../api";
import {User} from "../../../model/User";
import {ChangePassword} from "../../dumb/Profile/ChangePassword";

interface ProfileState {
    beingEdited: boolean;
    editIcon: EditIcons;
    isLoading: boolean;
    error?: string;
    user?: User;
    changedUser?: User;
}

enum EditIcons {
    write="write",
    checkmark="checkmark"
}

export class ProfileInfo extends React.Component<{}, ProfileState> {
    state: ProfileState = {
        beingEdited: false,
        editIcon: EditIcons.write,
        isLoading: true,

    };

    async componentDidMount () {
        const userInfo = await api.user.getProfile();
        if(api.isError(userInfo)) {
            this.setState({
                ...this.state,
                error: userInfo.message,
                isLoading: false,
            });
        } else {
            this.setState({
                ...this.state,
                changedUser: userInfo,
                isLoading: false,
                user: userInfo,
            });
        }
    }

    enableEditing = () => {
        this.setState({
            ...this.state,
            beingEdited: !this.state.beingEdited,
        });
    }

    async saveChanges () {
        const userInfo = this.state.changedUser;
        await api.user.updateUser(userInfo);
        this.setState({
            ...this.state,
            beingEdited: !this.state.beingEdited,
            user: userInfo,
        });
    }

    cancelChanges = () => {
        const userInfo = this.state.user;
        this.setState({
            ...this.state,
            beingEdited: !this.state.beingEdited,
            changedUser: userInfo,
        });
    }

    handleFirstNameInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, changedUser: {...this.state.changedUser, firstName: value}
        });
    }

    handleLastNameInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, changedUser: {...this.state.changedUser, lastName: value}
        });
    }

    handleEmailInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, changedUser: {...this.state.changedUser, email: value}
        });
    }

    handlePhoneInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state, changedUser: {...this.state.changedUser, phone: value}
        });
    }

    handleStreetInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state,
            changedUser: {...this.state.changedUser, address: {...this.state.changedUser.address, street: value}}
        });
    }

    handleCityInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state,
            changedUser: {...this.state.changedUser, address: {...this.state.changedUser.address, city: value}}
        });
    }

    handleZipCodeInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state,
            changedUser: {...this.state.changedUser, address: {...this.state.changedUser.address, zipCode: value}}
        });
    }

    handleCountryInput = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({
            ...this.state,
            changedUser: {...this.state.changedUser, address: {...this.state.changedUser.address, country: value}}
        });
    }

    render () {
        return(
            <Form size="mini" id="changeProfile" loading={this.state.isLoading}>
                <div className="profileEditing header">
                    { this.state.beingEdited
                    ?
                    <Button.Group id="profileSave">
                        <Button negative onClick= {() => this.cancelChanges()}>Cancel</Button>
                        <Button positive onClick= {() => this.saveChanges()}>Save</Button>
                    </Button.Group>
                    :
                    <Button
                        id="profileEdit"
                        icon labelPosition="left"
                        onClick= {() => this.enableEditing()}
                    >
                        <Icon name="write" />
                        Edit
                    </Button>
                    }
                </div>
                <Form.Field inline className="profileFormField">
                    <label>First name:</label>
                    { this.state.beingEdited
                    ?
                    <input className="editableFields" type="text" placeholder="First Name"
                        onChange = {this.handleFirstNameInput.bind(this)}
                        defaultValue={this.state.isLoading ? "" : this.state.user.firstName}
                    />
                    :
                    <label className="readOnlyFields" >
                        {this.state.isLoading ? "Cia turi but vardas..." : this.state.user.firstName}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Last name:</label>
                    { this.state.beingEdited
                    ?
                    <input className="editableFields" type="text" placeholder="Last Name"
                        onChange = {this.handleLastNameInput.bind(this)}
                        defaultValue={this.state.isLoading ? "" : this.state.user.lastName}
                    />
                    :
                    <label className="readOnlyFields" >
                        {this.state.isLoading ? "Cia turi but pavarde..." : this.state.user.lastName}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Email:</label>
                    { this.state.beingEdited
                    ?
                    <input className="editableFields" type="email" placeholder="email"
                        onChange = {this.handleEmailInput.bind(this)}
                        defaultValue={this.state.isLoading ? "" : this.state.user.email}
                    />
                    :
                    <label className="readOnlyFields" >
                        {this.state.isLoading ? "Cia turi but pastas..." : this.state.user.email}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Phone number:</label>
                    { this.state.beingEdited
                    ?
                    <input className="editableFields" type="text" placeholder="+370********"
                        onChange = {this.handlePhoneInput.bind(this)}
                        defaultValue={this.state.isLoading ? "" : this.state.user.phone}
                    />
                    :
                    <label className="readOnlyFields" >
                        {this.state.isLoading ? "Cia turi but numeris..." : this.state.user.phone}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Street:</label>
                    { this.state.beingEdited
                    ?
                    <input className="editableFields" type="text" placeholder="street"
                        onChange = {this.handleStreetInput.bind(this)}
                        defaultValue={this.state.isLoading ? "" : this.state.user.address.street}
                    />
                    :
                    <label className="readOnlyFields" >
                        {this.state.isLoading ? "Cia turi but gatve..." : this.state.user.address.street}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>City:</label>
                    {
                        this.state.beingEdited
                        ? <input className="editableFields" type="text" placeholder="city"
                            onChange = {this.handleCityInput.bind(this)}
                            defaultValue={this.state.isLoading ? "" : this.state.user.address.city}
                        />
                        : <label className="readOnlyFields" >
                            {this.state.isLoading ? "Cia turi but miestas..." : this.state.user.address.city}
                        </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
{/*Reikia visų galimų šalių sąrašo pasirinkimams*/}
                    <label>Country:</label>
                    {
                        this.state.beingEdited
                        ? <select className="editableFields"
                            onChange = {this.handleCountryInput.bind(this)}>
                            { this.state.isLoading
                            ? undefined
                            : <option value={this.state.user.address.country}>
                                {this.state.user.address.country}
                            </option>
                            }
                             <option value="Lithuania">Lithuania</option>
                             <option value="United Kingdom">United Kingdom</option>
                         </select>
                        : <label className="readOnlyFields" >
                            {this.state.isLoading ? "Cia turi but salis..." : this.state.user.address.country}
                        </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Zip Code:</label>
                    { this.state.beingEdited
                    ? <input className="editableFields" type="text" placeholder="zip code"
                        onChange = {this.handleZipCodeInput.bind(this)}
                        defaultValue={this.state.isLoading ? "" : this.state.user.address.zipCode}/>
                    : <label className="readOnlyFields" >
                        {this.state.isLoading ? "Cia turi but pasto kodas..." : this.state.user.address.zipCode}
                    </label>
                    }
                </Form.Field>
                <ChangePassword />
            </Form>
        );
    }

}
