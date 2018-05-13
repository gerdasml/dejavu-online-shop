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

export const descriptionShortener = (text: string) => {
    const SHORT_TEXT_LENGTH = 100;
    if(text.length <= SHORT_TEXT_LENGTH) {
        return text;
    }
    const shortText = text.substring(0,SHORT_TEXT_LENGTH);
    const lastSpaceIdx = shortText.lastIndexOf(" ");
    const fixedText = shortText.substring(0, lastSpaceIdx);
    const completedText = fixedText + "...";
    return completedText;
};
