import { ApiResponse } from "./ApiResponse";

import { ImportStatus, Product } from "../model/Product";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/product";

interface ProductSearchRequest {
    categoryIdentifier: string;
}

// export const getProducts = (): Promise<ApiResponse<Product[]>> =>
//     fetchData(PATH_PREFIX + "/", HttpMethod.GET);

// export const getProductByCategory = (id: number): Promise<ApiResponse<Product>> =>
//     fetchData(PATH_PREFIX + "/" + id.toString() + "/info", HttpMethod.GET);

export const getProductByIdentifier = (identifier: string): Promise<ApiResponse<Product>> =>
    fetchData(PATH_PREFIX + "/byIdentifier?identifier=" + identifier, HttpMethod.GET);

export const searchForProducts = (req: ProductSearchRequest): Promise<ApiResponse<Product[]>> =>
    fetchData(PATH_PREFIX + "/category", HttpMethod.POST, req);

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

export const getProductsByCategory = (id: number): Promise<ApiResponse<Product[]>> =>
    fetchData(PATH_PREFIX + "/category/" + id.toString(), HttpMethod.GET);

export const updateImportStatus = (jobId: string, newStatus: ImportStatus): Promise<ApiResponse<ImportStatus>> =>
    fetchData(PATH_PREFIX + "/import/status/" + jobId, HttpMethod.PUT, newStatus);
