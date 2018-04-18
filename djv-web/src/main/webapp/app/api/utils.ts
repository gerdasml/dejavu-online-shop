import { ApiResponse } from "../model/ApiResponse";
import {buildAuthHeader} from "./token";

export enum HttpMethod {
    GET,
    POST
}

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
                return r.json();
            })
            .catch(r => r.json());
};
