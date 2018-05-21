import { getToken } from "./token";

export const isLoggedIn = () => {
    const token = getToken();
    return token !== undefined && token !== null && token.length !== 0;
};
