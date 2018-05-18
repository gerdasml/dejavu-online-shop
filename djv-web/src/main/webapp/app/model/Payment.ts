export interface Expiration {
    year: number;
    month: number;
}

export interface Card {
    cvv: string;
    holder: string;
    number: string;
    expiration: Expiration;
}

export interface ValidationError {
    location: string;
    message: string;
}
