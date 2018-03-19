import {buildRequest, fetchData, HttpMethod} from "./utils";

const PATH_PREFIX = "/api/user";

enum UserType {
    REGULAR,
    ADMIN
}

interface IUser {
    firstName?: string;
    lastName?: string;
    email: string;
    type: UserType;
    banned: boolean;
}

export const getUsers = (): Promise<IUser[]> => fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const getUser = (id: number): Promise<IUser> =>
    fetchData(PATH_PREFIX + "/" + id, HttpMethod.GET);

export const banUser = (id: number): Promise<{}> =>
    fetchData(PATH_PREFIX + "/" + id + "/ban", HttpMethod.POST);
