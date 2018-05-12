import { ApiResponse } from "./ApiResponse";

import { User } from "../model/User";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/user";

export const getUsers = (): Promise<ApiResponse<User[]>> => fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const getUser = (id: number): Promise<ApiResponse<User>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.GET);

export const banUser = (id: number, banned: boolean): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString() + "/ban?banned=" + banned.toString(), HttpMethod.POST);

export const getProfile = (): Promise<ApiResponse<User>> =>
fetchData(PATH_PREFIX + "/profile", HttpMethod.GET);

export const updateUser = (user: User): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.POST, user);
