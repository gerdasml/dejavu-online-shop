import { ApiResponse } from "./ApiResponse";

import { Category } from "../model/Category";
import { CategoryTree } from "../model/CategoryTree";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/category";

export const getCategoryTree = (): Promise<ApiResponse<CategoryTree[]>> =>
    fetchData(PATH_PREFIX + "/categoryTree", HttpMethod.GET);

export const updateCategory = (id: number, category: Category): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.PUT, category);

export const deleteCategory = (id: number): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.DELETE);
