import * as React from "react";

import { Button, Icon, Table } from "semantic-ui-react";

import { IPurchase } from "../../model/Purchase";

import { Amount } from "../dumb/Cart/Amount";

import { CartStepHeader } from "../dumb/Cart/CartStepHeader";

import "../../../style/cart.css";
import { ShoppingCart } from "../smart/Cart/ShoppingCart";

interface ICartProps { purchases: IPurchase[]; }

const purchases: IPurchase[] = [{
  amount: 2,
  item: {name: "something", imageUrl:"", description:"", price: 5},
  total: 100,
  unitPrice: 50
}];

export const Cart = (props: {}) => (
//   <div>
//     <CartStepHeader />
//     <Table striped celled>
//       <Table.Header>
//         <Table.Row>
//           <Table.HeaderCell>Item</Table.HeaderCell>
//           <Table.HeaderCell>Unit Price</Table.HeaderCell>
//           <Table.HeaderCell>Amount</Table.HeaderCell>
//           <Table.HeaderCell>Total</Table.HeaderCell>
//         </Table.Row>
//       </Table.Header>

//       <Table.Body>
//         {purchases.map(p =>
//           <Table.Row>
//             <Table.Cell>{p.item.name}</Table.Cell>
//             <Table.Cell>{p.unitPrice}€</Table.Cell>
//             <Table.Cell><Amount amount={p.amount}/></Table.Cell>
//             <Table.Cell>{p.total}€</Table.Cell>
//           </Table.Row>
//         )}
//     </Table.Body>
//   </Table>
//   <Button icon labelPosition="right" floated="right">
//     Buy
//       <Icon name="chevron right" />
//   </Button>
//   </div >
<ShoppingCart />

);
