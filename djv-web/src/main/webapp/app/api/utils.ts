import { ApiResponse } from "./ApiResponse";

import { ApiError } from ".";
import {buildAuthHeader, clearToken} from "../utils/token";
import store from "../redux/store";
import {logout} from "../redux/actions/auth";
interface Headers {
    [header: string]: string;
}

interface Request {
    url: string;
    params: RequestInit;
}

const buildUnknownError = (code: number, msg: string): ApiError => ({
    message: msg + " (" + code.toString() + "): Please contact the administrators of the site",
    timestamp: new Date(),
    type: "Unknown",
    status: code
});

// =========> Exported members <==========
export enum HttpMethod {
    GET    = "GET",
    POST   = "POST",
    PUT    = "PUT",
    DELETE = "DELETE"
}

export const buildRequest = <T>(url: string, method: HttpMethod, payload?: T): Request => {
    const token = buildAuthHeader();
    const headers: Headers = {};
    headers.accept = "application/json";
    if (token !== undefined) headers.authorization = token;

    let params: RequestInit;
    if (payload instanceof FormData) {
        params = {
            body: payload,
            headers,
            method: method.toString()
        };
    } else {
        params = {
            body: JSON.stringify(payload),
            headers,
            method: method.toString()
        };
        if (method !== HttpMethod.GET) {
            headers["content-type"] = "application/json";
        }
    }

    return {
        params,
        url,
    };
};

export const fetchData = <T, K>(url: string, method: HttpMethod, payload?: T): Promise<ApiResponse<K>> => {
    const req = buildRequest(url, method, payload);
    return fetch(req.url, req.params)
            .then(r => {
                if(r.status === 401) store.dispatch(logout());
                if(!r.ok) throw r;
                if(r.headers.get("content-length") === "0") return "";
                return r.json();
            })
            .catch(r => {
                if (r.headers === undefined) return buildUnknownError(500, r);
                if(r.headers.get("content-length") === "0") return buildUnknownError(r.status, r.statusText);
                return r.json()
                        .then((j: ApiError) => ({
                            ...j,
                            status: r.status,
                            timestamp: new Date(Date.parse(j.timestamp.toString()))
                        }));
            });
};
