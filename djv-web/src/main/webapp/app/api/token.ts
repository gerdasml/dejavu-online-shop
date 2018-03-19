const TOKEN_KEY = "accessToken";

export const storeToken = (token: string) => localStorage.setItem(TOKEN_KEY, token);

export const getToken = () => localStorage.getItem(TOKEN_KEY);

export const clearToken = () => localStorage.removeItem(TOKEN_KEY);

export const buildAuthHeader = () => {
    const tkn = getToken();
    return "Bearer " + tkn;
};
