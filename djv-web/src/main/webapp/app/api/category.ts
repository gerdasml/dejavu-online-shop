import { ApiResponse } from "./ApiResponse";

import { Category, CategoryInfo } from "../model/Category";
import { CategoryTree } from "../model/CategoryTree";
import { fetchData, HttpMethod } from "./utils";
import { CategoryProperty } from "../model/CategoryProperties";

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

export const getCategoryProperties = (id: number): Promise<ApiResponse<CategoryProperty[]>> =>
    fetchData(PATH_PREFIX + "/" + id.toString() + "/properties" , HttpMethod.GET);

export const getCategoryInfo = (id: number): Promise<ApiResponse<CategoryInfo>> =>
    fetchData(PATH_PREFIX + "/" + id.toString() + "/info", HttpMethod.GET);
