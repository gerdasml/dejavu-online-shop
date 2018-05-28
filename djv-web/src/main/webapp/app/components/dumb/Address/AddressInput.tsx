import * as React from "react";

import { Form } from "semantic-ui-react";
import { countries } from "../../../data/countries";
import { Address } from "../../../model/Address";

import "../../../../style/address.css";

interface AddressProps {
  address: Address;
  formSize: string;
  onAddressChange: (newAddress: Address) => void;
  fieldClassName?: string;
  labelsOnly?: boolean;
  labelClassName?: string;
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
    return [
          <Form.Field inline className={this.props.fieldClassName}>
            <label>Street</label>
            { this.props.labelsOnly
            ?
            <label className={this.props.labelClassName}>
                {this.props.address.street}
            </label>
            :
            <input type="text"
              placeholder="Gatvės g., 24-16"
              value={this.props.address.street}
              onChange={this.handleStreetInput.bind(this)} />
            }
          </Form.Field>,
          <Form.Field inline className={this.props.fieldClassName}>
            <label>City</label>
            { this.props.labelsOnly
            ?
            <label className={this.props.labelClassName}>
                {this.props.address.city}
            </label>
            :
            <input type="text"
              placeholder="Vilnius"
              value={this.props.address.city}
              onChange={this.handleCityInput.bind(this)} />
            }
          </Form.Field>,
          <Form.Field inline className={this.props.fieldClassName}>
            <label>Zip Code</label>
            { this.props.labelsOnly
            ?
            <label className={this.props.labelClassName}>
                {this.props.address.zipCode}
            </label>
            :
            <input type="text"
              placeholder="LT-03011"
              value={this.props.address.zipCode}
              onChange={this.handleZipCodeInput.bind(this)} />
            }
          </Form.Field>,
          <Form.Field inline className={this.props.fieldClassName}>
            <label>Country</label>
            { this.props.labelsOnly
            ?
            <label className={this.props.labelClassName}>
                {this.props.address.country}
            </label>
            :
            <select
              value={this.props.address.country}
              onChange={this.handleCountryInput.bind(this)}>
              <option value="" hidden></option>
              {countries.map(country => (
                <option value={country.name}>{country.name}</option>))}
            </select>
            }
          </Form.Field>,
        ];
  }
}
