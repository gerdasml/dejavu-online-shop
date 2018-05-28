import * as React from "react";

import "../../../../style/profile.css";

import { withRouter, RouteComponentProps } from "react-router-dom";

import {Button, Form, Icon} from "semantic-ui-react";

import * as api from "../../../api";
import {User} from "../../../model/User";
import {ChangePassword} from "../../dumb/Profile/ChangePassword";
import { notification } from "antd";

interface ProfileState {
    beingEdited: boolean;
    editIcon: EditIcons;
    isLoading: boolean;
    user?: User;
    changedUser?: User;
}

enum EditIcons {
    write="write",
    checkmark="checkmark"
}

export const ProfileInfo = withRouter(class extends React.Component<RouteComponentProps<{}>, ProfileState> {
    state: ProfileState = {
        beingEdited: false,
        editIcon: EditIcons.write,
        isLoading: true,

    };

    async componentDidMount () {
        const userInfo = await api.user.getProfile();
        if(api.isError(userInfo)) {
            notification.error({message: "Failed to open profile page", description: userInfo.message});
            this.setState({...this.state, isLoading: false});
            this.props.history.push("/");
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
        const value = event.currentTarget.value.replace(/[^0-9]/g, "");
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
        const isLoading = this.state.changedUser === undefined;
        return(
            <Form
                size="mini"
                id="changeProfile"
                loading={isLoading}
                onSubmit={e => {e.preventDefault(); this.saveChanges();}}
            >
                <div className="profileEditing header">
                    { this.state.beingEdited
                    ?
                    <Button.Group id="profileSave">
                        <Button positive type="submit">Save</Button>
                        <Button negative type="cancel" onClick={this.cancelChanges.bind(this)}>Cancel</Button>
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
                        value={isLoading ? "" : this.state.changedUser.firstName}
                    />
                    :
                    <label className="readOnlyFields" >
                        {isLoading ? "" : this.state.changedUser.firstName}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Last name:</label>
                    { this.state.beingEdited
                    ?
                    <input className="editableFields" type="text" placeholder="Last Name"
                        onChange = {this.handleLastNameInput.bind(this)}
                        value={isLoading ? "" : this.state.changedUser.lastName}
                    />
                    :
                    <label className="readOnlyFields" >
                        {isLoading ? "" : this.state.changedUser.lastName}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Email:</label>
                    { this.state.beingEdited
                    ?
                    <input required className="editableFields" type="email" placeholder="email"
                        onChange = {this.handleEmailInput.bind(this)}
                        value={isLoading ? "" : this.state.changedUser.email}
                    />
                    :
                    <label className="readOnlyFields" >
                        {isLoading ? "" : this.state.changedUser.email}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Phone number:</label>
                    { this.state.beingEdited
                    ?
                    <input className="editableFields" type="tel" placeholder="370********"
                        onChange = {this.handlePhoneInput.bind(this)}
                        value={isLoading ? "" : this.state.changedUser.phone}
                    />
                    :
                    <label className="readOnlyFields" >
                        {isLoading ? "" : this.state.changedUser.phone}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Street:</label>
                    { this.state.beingEdited
                    ?
                    <input className="editableFields" type="text" placeholder="street"
                        onChange = {this.handleStreetInput.bind(this)}
                        value={isLoading ? "" : this.state.changedUser.address.street}
                    />
                    :
                    <label className="readOnlyFields" >
                        {isLoading ? "" : this.state.changedUser.address.street}
                    </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>City:</label>
                    {
                        this.state.beingEdited
                        ? <input className="editableFields" type="text" placeholder="city"
                            onChange = {this.handleCityInput.bind(this)}
                            value={isLoading ? "" : this.state.changedUser.address.city}
                        />
                        : <label className="readOnlyFields" >
                            {isLoading ? "" : this.state.changedUser.address.city}
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
                            { isLoading
                            ? undefined
                            : <option value={this.state.changedUser.address.country}>
                                {this.state.changedUser.address.country}
                            </option>
                            }
                             <option value="Lithuania">Lithuania</option>
                             <option value="United Kingdom">United Kingdom</option>
                         </select>
                        : <label className="readOnlyFields" >
                            {isLoading ? "" : this.state.changedUser.address.country}
                        </label>
                    }
                </Form.Field>
                <Form.Field inline className="profileFormField">
                    <label>Zip Code:</label>
                    { this.state.beingEdited
                    ? <input className="editableFields" type="text" placeholder="zip code"
                        onChange = {this.handleZipCodeInput.bind(this)}
                        value={isLoading ? "" : this.state.changedUser.address.zipCode}/>
                    : <label className="readOnlyFields" >
                        {isLoading ? "" : this.state.changedUser.address.zipCode}
                    </label>
                    }
                </Form.Field>
                <ChangePassword />
            </Form>
        );
    }

});
