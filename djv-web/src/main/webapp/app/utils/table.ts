export interface WithKey {
    key: number;
}

export interface WithStringKey {
    key: string;
}

// tslint:disable-next-line:prefer-object-spread
export const addKey = <T>(x: T, idx: number): T & WithKey => Object.assign({key: idx}, x);

// tslint:disable-next-line:prefer-object-spread
export const addKeyString = <T>(x: T, keyString: string ): T & WithStringKey => Object.assign({key: keyString}, x);
