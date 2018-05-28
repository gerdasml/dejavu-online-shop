import { ApiResponse } from "./ApiResponse";

import { Order, OrderStatus, OrderSummary } from "../model/Order";

import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/order";

export const getAllOrders = (): Promise<ApiResponse<Order[]>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const getOrder = (id: number): Promise<ApiResponse<Order>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.GET);

export const getUserOrderHistory = (userId: number): Promise<ApiResponse<Order[]>> =>
    fetchData(PATH_PREFIX + "/" + userId.toString() + "/history", HttpMethod.GET);

export const getOrderHistory = (): Promise<ApiResponse<Order[]>> =>
    fetchData(PATH_PREFIX + "/history", HttpMethod.GET);

export const updateOrderStatus =
    (orderId: number, status: OrderStatus, lastModified: Date): Promise<ApiResponse<Order>> =>
        fetchData(PATH_PREFIX + "/" + orderId.toString(), HttpMethod.PUT, {status, lastModified});

export const getOrderSummary = (): Promise<ApiResponse<OrderSummary[]>> =>
    fetchData(PATH_PREFIX + "/summary", HttpMethod.GET);
