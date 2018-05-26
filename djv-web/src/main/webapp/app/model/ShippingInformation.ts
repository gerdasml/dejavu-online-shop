import { Address } from "./Address";

export interface ShippingInformation {
    recipientFirstName: string;
    recipientLastName: string;
    shippingAddress: Address;
}
