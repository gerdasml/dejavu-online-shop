import { ApiResponse } from "./ApiResponse";

import { Product } from "../model/Product";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/product";

// export const getProducts = (): Promise<ApiResponse<Product[]>> =>
//     fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const getProductByCategory = (id: number): Promise<ApiResponse<Product>> =>
    fetchData(PATH_PREFIX + "/" + id.toString() + "/info", HttpMethod.GET);
