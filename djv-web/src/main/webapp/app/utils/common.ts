import { Address } from "../model/Address";

export const stringifyAddress = (addr: Address) =>
    addr
    ? `${addr.street}, ${addr.zipCode} ${addr.city}, ${addr.country}`
    : undefined;

export const toUrlFriendlyString = (s: string) =>
    s
    ? s.replace(/[^a-z0-9_]+/gi, "-")
        .replace(/^-|-$/g, "")
        .toLowerCase()
    : "";
