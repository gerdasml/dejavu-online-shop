import { Address } from "../model/Address";
import { ApiResponse } from "./ApiResponse";

import { User } from "../model/User";
import {fetchData, HttpMethod} from "./utils";

const PATH_PREFIX = "/api/auth";

interface LoginSuccessResponse {
    token: string;
}

interface LoginBannedResponse {
    banned: boolean;
}

type LoginResponse = LoginSuccessResponse | LoginBannedResponse;

export interface RegistrationRequest {
    email: string;
    password: string;
    firstName?: string;
    lastName?: string;
    address?: Address;
    phone?: string;
}

export const isBanned = (response: LoginResponse): response is LoginBannedResponse =>
    (response as LoginBannedResponse).banned !== undefined;

export const login = (email: string, password: string): Promise<ApiResponse<LoginResponse>> => {
    const body = {email, password};
    return fetchData(PATH_PREFIX + "/login", HttpMethod.POST, body);
};

export const register = (user: RegistrationRequest): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/register", HttpMethod.POST, user);
