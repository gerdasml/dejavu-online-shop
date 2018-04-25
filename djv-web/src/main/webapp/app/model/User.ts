// api/user.ts yra user type ir a user. reik situos issikelt
import { Address } from "./Address";

export enum UserType {
    REGULAR,
    ADMIN
}

export interface User {
    firstName?: string;
    lastName?: string;
    email: string;
    type: UserType;
    banned: boolean;
    address: Address;
    phone: string;
}
