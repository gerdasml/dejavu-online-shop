export interface WithKey {
    key: number;
}

// tslint:disable-next-line:prefer-object-spread
export const addKey = <T>(x: T, idx: number): T & WithKey => Object.assign({key: idx}, x);