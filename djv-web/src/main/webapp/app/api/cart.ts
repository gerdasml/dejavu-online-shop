import { ApiResponse } from "./ApiResponse";

import { Cart } from "../model/Cart";
import { Card } from "../model/Payment";

import { fetchData, HttpMethod } from "./utils";
import { ShippingInformation } from "../model/ShippingInformation";

const PATH_PREFIX = "/api/cart";

export interface ModifyCartRequest {
    amount: number;
    productId: number;
}

export interface CheckoutRequest {
    card: Card;
    shippingInformation: ShippingInformation;
}

export const getCart = (): Promise<ApiResponse<Cart>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const addToCart = (request: ModifyCartRequest): Promise<ApiResponse<Cart>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.POST, request);

export const updateAmount = (request: ModifyCartRequest): Promise<ApiResponse<Cart>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.PUT, request);

export const removeFromCart = (productId: number): Promise<ApiResponse<Cart>> =>
    fetchData(PATH_PREFIX + "/" + productId.toString(), HttpMethod.DELETE);

export const checkout = (request: CheckoutRequest): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/checkout", HttpMethod.POST, request);
