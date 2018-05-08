import { Address } from "../model/Address";

export const stringifyAddress = (addr: Address) =>
    addr
    ? `${addr.street}, ${addr.zipCode} ${addr.city}, ${addr.country}`
    : undefined;
