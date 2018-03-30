// api/user.ts yra user type ir a user. reik situos issikelt
enum UserType {
    REGULAR,
    ADMIN
}

export interface IUser {
    firstName?: string;
    lastName?: string;
    email: string;
    type: UserType;
    banned: boolean;
}
