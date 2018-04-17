export interface ICard {
    cvv: string;
    holder: string;
    number: string;
}

export interface IExpiration {
    year: number;
    month: number;
}

export interface IPayment {
    amount: number;
    card: ICard;
    expiration: IExpiration;
}

export interface IValidationError {
    location: string;
    message: string;
}
