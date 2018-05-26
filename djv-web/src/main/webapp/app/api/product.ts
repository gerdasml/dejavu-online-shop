import { ApiResponse } from "./ApiResponse";

import { ImportStatus, Product } from "../model/Product";
import { fetchData, HttpMethod } from "./utils";
import { ProductProperties } from "../model/ProductProperties";
import { SearchResult } from "../model/SearchResult";

const PATH_PREFIX = "/api/product";

interface FilterRequest {
    minPrice: number;
    maxPrice: number;
    properties?: ProductProperties[];
}

export interface ProductSearchRequest {
    categoryIdentifier?: string;
    minPrice?: number;
    maxPrice?: number;
    name?: string;
}

const buildPaginationPath = (relPath: string, offset?: number, limit?: number) => {
    let path = PATH_PREFIX + relPath;
    if (offset !== undefined && limit !== undefined) {
        path += `?offset=${offset}&limit=${limit}`;
    } else if (offset !== undefined) {
        path += `?offset=${offset}`;
    } else if(limit !== undefined) {
        path += `?limit=${limit}`;
    }
    return path;
};

// export const getProducts = (): Promise<ApiResponse<Product[]>> =>
//     fetchData(PATH_PREFIX + "/", HttpMethod.GET);

// export const getProductByCategory = (id: number): Promise<ApiResponse<Product>> =>
//     fetchData(PATH_PREFIX + "/" + id.toString() + "/info", HttpMethod.GET);

export const getProductByIdentifier = (identifier: string): Promise<ApiResponse<Product>> =>
    fetchData(PATH_PREFIX + "/byIdentifier?identifier=" + identifier, HttpMethod.GET);

export const searchForProducts =
    (req: ProductSearchRequest, offset?: number, limit?: number): Promise<ApiResponse<SearchResult<Product>>> =>
        fetchData(buildPaginationPath("/search", offset, limit), HttpMethod.POST, req);

export const getAllProducts = (offset?: number, limit?: number): Promise<ApiResponse<Product[]>> =>
    fetchData(buildPaginationPath("/", offset, limit), HttpMethod.GET);

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

export const getProductsByCategory = (id: number, offset?: number, limit?: number): Promise<ApiResponse<Product[]>> =>
    fetchData(buildPaginationPath("/category/" + id.toString()), HttpMethod.GET);

export const updateImportStatus = (jobId: string, newStatus: ImportStatus): Promise<ApiResponse<ImportStatus>> =>
    fetchData(PATH_PREFIX + "/import/status/" + jobId, HttpMethod.PUT, newStatus);

export const getImportStatistics = (jobId: string): Promise<ApiResponse<ImportStatus>> =>
    fetchData(PATH_PREFIX + "/import/statistics/" + jobId, HttpMethod.GET);

export const getTotalProductCount = (): Promise<ApiResponse<number>> =>
    fetchData(PATH_PREFIX + "/count", HttpMethod.GET);
