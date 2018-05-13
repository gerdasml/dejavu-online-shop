import { ApiResponse } from "./ApiResponse";

import { ImportStatus, Product } from "../model/Product";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/product";

// export const getProducts = (): Promise<ApiResponse<Product[]>> =>
//     fetchData(PATH_PREFIX + "/", HttpMethod.GET);

// export const getProductByCategory = (id: number): Promise<ApiResponse<Product>> =>
//     fetchData(PATH_PREFIX + "/" + id.toString() + "/info", HttpMethod.GET);

export const getAllProducts = (): Promise<ApiResponse<Product[]>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const createProduct = (product: Product): Promise<ApiResponse<number>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.POST, product);

export const getProduct = (id: number): Promise<ApiResponse<Product>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.GET);

export const updateProduct = (id: number, product: Product): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.PUT, product);

export const deleteProduct = (id: number): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.DELETE);

export const getImportStatus = (jobId: string): Promise<ApiResponse<ImportStatus>> =>
    fetchData(PATH_PREFIX + "/import/status/" + jobId, HttpMethod.GET);

export const importProducts = (excel: File): Promise<ApiResponse<string>> => {
    const data = new FormData();
    data.append("file", excel);
    return fetchData(PATH_PREFIX + "/import", HttpMethod.POST, data);
};

export const getImportStatuses = (): Promise<ApiResponse<ImportStatus[]>> =>
    fetchData(PATH_PREFIX + "/import/status/", HttpMethod.GET);
