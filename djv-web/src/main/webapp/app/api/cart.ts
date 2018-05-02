import { ApiResponse } from "./ApiResponse";

import { Address } from "../model/Address";
import { Cart } from "../model/Cart";
import { Card } from "../model/Payment";

import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/cart";

export interface ModifyCartRequest {
    amount: number;
    productId: number;
}

export interface CheckoutRequest {
    card: Card;
    shippingAddress: Address;
}

export const getCart = (): Promise<ApiResponse<Cart>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const addToCart = (request: ModifyCartRequest): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.POST, request);

export const updateAmount = (request: ModifyCartRequest): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.PUT, request);

export const removeFromCart = (productId: number): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + productId.toString(), HttpMethod.DELETE);

export const checkout = (request: CheckoutRequest): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/checkout", HttpMethod.POST, request);
