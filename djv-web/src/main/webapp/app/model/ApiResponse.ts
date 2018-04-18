import { IError } from "./Error";

export type ApiResponse<T> = T | IError;

export const isError = <T>(response: ApiResponse<T>): response is IError =>
    (response as IError).type !== undefined;
