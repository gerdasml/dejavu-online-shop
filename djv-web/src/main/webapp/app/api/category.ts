import { ApiResponse } from "./ApiResponse";

import { Category } from "../model/Category";
import { CategoryTree } from "../model/CategoryTree";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/category";

export const getCategoryByIdentifier = (identifier: string): Promise<ApiResponse<Category>> =>
    fetchData(PATH_PREFIX + "/?identifier=" + identifier, HttpMethod.GET);

export const getCategoryTree = (): Promise<ApiResponse<CategoryTree[]>> =>
    fetchData(PATH_PREFIX + "/categoryTree", HttpMethod.GET);

export const updateCategory = (id: number, category: Category): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.PUT, category);

export const deleteCategory = (id: number): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.DELETE);

export const createCategory = (category: Category): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/create", HttpMethod.POST, category);

export const getCategory = (id: number): Promise<ApiResponse<Category>> =>
    fetchData(PATH_PREFIX + "/" + id.toString(), HttpMethod.GET);
