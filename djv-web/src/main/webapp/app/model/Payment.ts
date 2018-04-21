export interface Card {
    cvv: string;
    holder: string;
    number: string;
}

export interface Expiration {
    year: number;
    month: number;
}

export interface Payment {
    amount: number;
    card: Card;
    expiration: Expiration;
}

export interface ValidationError {
    location: string;
    message: string;
}
