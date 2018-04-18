import { ApiResponse } from "../model/ApiResponse";
import {fetchData, HttpMethod} from "./utils";

const PATH_PREFIX = "/api/auth";

interface ILoginSuccessResponse {
    token: string;
}

interface ILoginBannedResponse {
    banned: boolean;
}

type LoginResponse = ILoginSuccessResponse | ILoginBannedResponse;

export const isBanned = (response: LoginResponse): response is ILoginBannedResponse =>
    (response as ILoginBannedResponse).banned !== undefined;

export const login = (email: string, password: string): Promise<ApiResponse<LoginResponse>> => {
    const body = {email, password};
    return fetchData(PATH_PREFIX + "/login", HttpMethod.POST, body);
};
