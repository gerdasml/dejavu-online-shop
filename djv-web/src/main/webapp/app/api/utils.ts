import { ApiResponse } from "./ApiResponse";

import { ApiError } from ".";
import {buildAuthHeader} from "../utils/token";

export enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE
}

const buildUnknownError = (code: number, msg: string): ApiError => ({
    message: msg + " (" + code.toString + "): Please contact the administrators of the site",
    timestamp: new Date(),
    type: "Unknown"
});

export const buildRequest = <T>(url: string, method: HttpMethod, payload?: T) => {
    const token = buildAuthHeader();
    const headers: { [header: string]: string } = {};
    headers.accept = "application/json";
    if (method === HttpMethod.POST) headers["content-type"] = "application/json";
    if (token !== null) headers.authorization = token;

    const params = {
        body: JSON.stringify(payload),
        headers,
        method: HttpMethod[method]
    };

    return {
        params,
        url,
    };
};

export const fetchData = <T, K>(url: string, method: HttpMethod, payload?: T): Promise<ApiResponse<K>> => {
    const req = buildRequest(url, method, payload);
    return fetch(req.url, req.params)
            .then(r => {
                if(!r.ok) throw r;
                if(r.headers.get("content-length") === "0") return "";
                return r.json();
            })
            .catch(r => {
                if(r.headers.get("content-length") === "0") return buildUnknownError(r.status, r.statusText);
                return r.json();
            });
};
