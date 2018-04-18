import { ApiError } from "./ApiError";

export type ApiResponse<T> = T | ApiError;

export const isError = <T>(response: ApiResponse<T>): response is ApiError =>
    (response as ApiError).type !== undefined;
