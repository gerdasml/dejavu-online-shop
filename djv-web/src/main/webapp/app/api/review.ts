import { ApiResponse } from "./ApiResponse";

import { fetchData, HttpMethod } from "./utils";

import { Order } from "../model/Order";
import { Review } from "../model/Review";

const PATH_PREFIX = "/api/order/review";

export const getOrdersToReview = (): Promise<ApiResponse<Order[]>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const submitReview = (orderId: number, review: Review): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + orderId, HttpMethod.POST, review);

export const rejectReview = (orderId: number): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + orderId + "/reject", HttpMethod.POST);
