const PATH_PREFIX = "/api/auth";

interface ILoginSuccessResponse {
    token: string;
}

interface ILoginBannedResponse {
    banned: boolean;
}

type LoginResponse = ILoginSuccessResponse | ILoginBannedResponse;

export const login = (email: string, password: string): Promise<LoginResponse> => {
    const body = {email, password};
    const params = {
        body: JSON.stringify(body),
        headers: {
            "content-type": "application/json"
        },
        method: "POST",
    };
    const url = PATH_PREFIX + "/login";
    return fetch(url, params)
            .then(r => r.json());
};
