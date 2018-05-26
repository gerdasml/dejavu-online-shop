import { ApiResponse } from "./ApiResponse";

import { fetchData, HttpMethod } from "./utils";
import { Discount } from "../model/Discount";

const PATH_PREFIX = "/api/discount";

export const getAllDiscounts = (): Promise<ApiResponse<Discount[]>> =>
fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const getDiscount = (id: number): Promise<ApiResponse<Discount>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.GET);

export const addDiscount = (discount: Discount): Promise<ApiResponse<number>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.POST, discount);

export const addDiscounts = (discounts: Discount[]): Promise<ApiResponse<number[]>> =>
    fetchData(PATH_PREFIX + "/batch", HttpMethod.POST, discounts);

export const updateDiscount = (id: number, discount: Discount): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.PUT, discount);

export const deleteDiscount = (id: number): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.DELETE);
