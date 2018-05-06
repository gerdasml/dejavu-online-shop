export interface WithKey {
    key: number;
}

export const addKey = <T extends {}>(x: T, idx: number): T & WithKey => Object.assign({key: idx}, x);
