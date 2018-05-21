import { Address } from "../model/Address";

export const stringifyAddress = (addr: Address) =>
    addr
    ? `${addr.street}, ${addr.zipCode} ${addr.city}, ${addr.country}`
    : undefined;

export const shortenString = (text: string, length: number = 100) => {
    if(text.length <= length) {
        return text;
    }
    const shortText = text.substring(0,length);
    const lastSpaceIdx = shortText.lastIndexOf(" ");
    const fixedText = shortText.substring(0, lastSpaceIdx);
    const completedText = fixedText + "...";
    return completedText;
};

export const formatPrice = (price: number): string =>
    price === undefined || isNaN(+price)
        ? undefined
        : (+price).toFixed(2) + " €";
