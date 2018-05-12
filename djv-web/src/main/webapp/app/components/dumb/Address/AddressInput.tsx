import * as React from "react";

import { Flag, Form, Select, Table } from "semantic-ui-react";
import { countries } from "../../../data/countries";
import { Address } from "../../../model/Address";

import "../../../../style/address.css";

interface AddressProps {
  address: Address;
  formSize: string;
}

interface AddressState {
  address: Address;
}

export class AddressInput extends React.Component<AddressProps, AddressState> {
  state: AddressState = {
    address: this.props.address,
  };

  handleStreetInput = (event: React.FormEvent<HTMLInputElement>) => {
    const value = event.currentTarget.value;
    const newAddress = { ...this.state.address };
    newAddress.street = value;
    this.setState({ ...this.state, address: newAddress });
  }
  handleCityInput = (event: React.FormEvent<HTMLInputElement>) => {
    const value = event.currentTarget.value;
    const newAddress = { ...this.state.address };
    newAddress.city = value;
    this.setState({ ...this.state, address: newAddress });
  }
  handleZipCodeInput = (event: React.FormEvent<HTMLInputElement>) => {
    const value = event.currentTarget.value;
    const newAddress = { ...this.state.address };
    newAddress.zipCode = value;
    this.setState({ ...this.state, address: newAddress });
  }
  handleCountryInput = (event: React.FormEvent<HTMLInputElement>) => {
    const value = event.currentTarget.value;
    const newAddress = { ...this.state.address };
    newAddress.country = value;
    this.setState({ ...this.state, address: newAddress });
  }

  // tslint:disable-next-line:prefer-function-over-method
  render () {
    return (
      <div>
        <Form id="addressForm" size={this.props.formSize}>
          <Form.Field inline>
            <label>Street</label>
            <input type="text"
              placeholder="street"
              value={this.state.address.street}
              onChange={this.handleStreetInput.bind(this)} />
          </Form.Field>
          <Form.Field inline>
            <label>Zip Code</label>
            <input type="text"
              placeholder="zip"
              value={this.state.address.zipCode}
              onChange={this.handleZipCodeInput.bind(this)} />
          </Form.Field>
          <Form.Field inline>
            <label>Country</label>
            <select
              value={this.state.address.country}
              onChange={this.handleCountryInput.bind(this)}>
              <option value="" hidden></option>
              {countries.map(country => (
                <option value={country.name}>{country.name}</option>))}
            </select>
          </Form.Field>
          <Form.Field inline>
            <label>City</label>
            <input type="text"
              placeholder="Vilnius"
              value={this.state.address.city}
              onChange={this.handleCityInput.bind(this)} />
          </Form.Field>
        </Form>
      </div>
    );
  }
}
