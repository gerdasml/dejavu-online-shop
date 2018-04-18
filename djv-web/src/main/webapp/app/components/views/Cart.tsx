import * as React from "react";

import { Button, Icon, Table } from "semantic-ui-react";

import { Purchase } from "../../model/Purchase";

import { Amount } from "../dumb/Cart/Amount";

import { CartStep } from "../dumb/Cart/CartStep";

import "../../../style/cart.css";

interface CartProps { purchases: Purchase[]; }

const purchases: Purchase[] = [{
  amount: 2,
  item: {name: "something", imageUrl:"", description:"", price: 5},
  total: 100,
  unitPrice: 50
}];

export const Cart = (props: {}) => (
  <div>
    <CartStep />
    <Table striped celled>
      <Table.Header>
        <Table.Row>
          <Table.HeaderCell>Item</Table.HeaderCell>
          <Table.HeaderCell>Unit Price</Table.HeaderCell>
          <Table.HeaderCell>Amount</Table.HeaderCell>
          <Table.HeaderCell>Total</Table.HeaderCell>
        </Table.Row>
      </Table.Header>

      <Table.Body>
        {purchases.map(p =>
          <Table.Row>
            <Table.Cell>{p.item.name}</Table.Cell>
            <Table.Cell>{p.unitPrice}€</Table.Cell>
            <Table.Cell><Amount amount={p.amount}/></Table.Cell>
            <Table.Cell>{p.total}€</Table.Cell>
          </Table.Row>
        )}
    </Table.Body>
  </Table>
  <Button icon labelPosition="right" floated="right">
    Buy
      <Icon name="chevron right" />
  </Button>
  </div >

);
