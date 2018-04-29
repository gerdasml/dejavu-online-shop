import { ApiResponse } from "./ApiResponse";

import { CategoryTree } from "../model/CategoryTree";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/category";

export const getCategoryTree = (): Promise<ApiResponse<CategoryTree[]>> =>
    fetchData(PATH_PREFIX + "/categoryTree", HttpMethod.GET);
