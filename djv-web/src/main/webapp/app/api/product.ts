import { ApiResponse } from "./ApiResponse";

import { Product } from "../model/Product";
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

export const getProductsByCategory = (id: number): Promise<ApiResponse<Product[]>> =>
    fetchData(PATH_PREFIX + "/category/" + id.toString(), HttpMethod.GET);
