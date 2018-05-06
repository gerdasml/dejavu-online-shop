import { Address } from "./Address";

export enum UserType {
    REGULAR = "REGULAR",
    ADMIN = "ADMIN"
}

export interface User {
    id: number;
    firstName?: string;
    lastName?: string;
    email: string;
    type: UserType;
    banned: boolean;
    address: Address;
    phone: string;
}
