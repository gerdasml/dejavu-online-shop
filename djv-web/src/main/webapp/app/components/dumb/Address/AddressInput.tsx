import * as React from "react";

import { Form } from "semantic-ui-react";
import { countries } from "../../../data/countries";
import { Address } from "../../../model/Address";

import "../../../../style/address.css";

interface AddressProps {
  address: Address;
  formSize: string;
  onAddressChange: (newAddress: Address) => void;
}

export class AddressInput extends React.Component<AddressProps, {}> {
  handleStreetInput = (event: React.FormEvent<HTMLInputElement>) => {
    const value = event.currentTarget.value;
    const newAddress = { ...this.props.address };
    newAddress.street = value;
    this.props.onAddressChange(newAddress);
  }
  handleCityInput = (event: React.FormEvent<HTMLInputElement>) => {
    const value = event.currentTarget.value;
    const newAddress = { ...this.props.address };
    newAddress.city = value;
    this.props.onAddressChange(newAddress);
  }
  handleZipCodeInput = (event: React.FormEvent<HTMLInputElement>) => {
    const value = event.currentTarget.value;
    const newAddress = { ...this.props.address };
    newAddress.zipCode = value;
    this.props.onAddressChange(newAddress);
  }
  handleCountryInput = (event: React.FormEvent<HTMLInputElement>) => {
    const value = event.currentTarget.value;
    const newAddress = { ...this.props.address };
    newAddress.country = value;
    this.props.onAddressChange(newAddress);
  }

  // tslint:disable-next-line:prefer-function-over-method
  render () {
    return (
      <div>
        <Form id="addressForm" size={this.props.formSize}>
          <Form.Field inline>
            <label>Street</label>
            <input type="text"
              placeholder="Gatvės g., 24-16"
              defaultValue={this.props.address.street}
              onChange={this.handleStreetInput.bind(this)} />
          </Form.Field>
          <Form.Field inline>
            <label>Zip Code</label>
            <input type="text"
              placeholder="LT-03011"
              defaultValue={this.props.address.zipCode}
              onChange={this.handleZipCodeInput.bind(this)} />
          </Form.Field>
          <Form.Field inline>
            <label>Country</label>
            <select
              defaultValue={this.props.address.country}
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
              defaultValue={this.props.address.city}
              onChange={this.handleCityInput.bind(this)} />
          </Form.Field>
        </Form>
      </div>
    );
  }
}
