import * as React from "react";

import {Button, Header, Icon, Image, Table} from "semantic-ui-react";

import { IProduct } from "../../model/Product";

import { IPurchase } from "../../model/Purchase";

import {Amount} from "../dumb/Cart/Amount";

import {CartStep} from "../dumb/Cart/CartStep";

import "../../../style/cart.css";

interface ICartPurchase { purchases: IPurchase[]; }

export const Cart = (props: ICartPurchase) => (
  <div>
  <CartStep/>
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
      <Table.Row>
        {/*<Table.Cell>{props.purchases[0].item}</Table.Cell>
        <Table.Cell>{props.purchases[0].unitPrice}€</Table.Cell>
        <Table.Cell>{props.purchases[0].amount}</Table.Cell>
<Table.Cell>{props.purchases[0].total}€</Table.Cell>*/}
        <Table.Cell><Image src={"src"} size="tiny" verticalAlign="middle" /> <span>Shopping item</span></Table.Cell>
        <Table.Cell class="unitPrice">55.46€</Table.Cell>
        <Table.Cell class="amount"><Amount/></Table.Cell>
        <Table.Cell>55.46€ <Icon className="trashIcon" name="trash outline" size="big"/></Table.Cell>
      </Table.Row>
      <Table.Row>
        <Table.Cell><Image src={"src"} size="tiny" verticalAlign="middle" /> <span>Shopping item</span></Table.Cell>
        <Table.Cell class="unitPrice">55.46€</Table.Cell>
        <Table.Cell class="amount"><Amount/></Table.Cell>
        <Table.Cell>55.46€ <Icon className="trashIcon" name="trash outline" size="big"/></Table.Cell>
      </Table.Row>
    </Table.Body>
  </Table>
  <Button icon labelPosition="right" floated="right">
      Buy
      <Icon name="chevron right" />
    </Button>
  </div>

);
