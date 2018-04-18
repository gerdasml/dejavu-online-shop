import { ApiResponse } from "./ApiResponse";

import { User } from "../model/User";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/user";

export const getUsers = (): Promise<ApiResponse<User[]>> => fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const getUser = (id: number): Promise<ApiResponse<User>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.GET);

export const banUser = (id: number): Promise<{}> =>
    fetchData(PATH_PREFIX + "/" + id.toString() + "/ban", HttpMethod.POST);
