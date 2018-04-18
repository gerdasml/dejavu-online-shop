import { Error } from "./Error";

export type ApiResponse<T> = T | Error;

export const isError = <T>(response: ApiResponse<T>): response is Error =>
    (response as Error).type !== undefined;
