import { ApiResponse } from "../model/ApiResponse";
import {fetchData, HttpMethod} from "./utils";

const PATH_PREFIX = "/api/auth";

interface LoginSuccessResponse {
    token: string;
}

interface LoginBannedResponse {
    banned: boolean;
}

type LoginResponse = LoginSuccessResponse | LoginBannedResponse;

export const isBanned = (response: LoginResponse): response is LoginBannedResponse =>
    (response as LoginBannedResponse).banned !== undefined;

export const login = (email: string, password: string): Promise<ApiResponse<LoginResponse>> => {
    const body = {email, password};
    return fetchData(PATH_PREFIX + "/login", HttpMethod.POST, body);
};
