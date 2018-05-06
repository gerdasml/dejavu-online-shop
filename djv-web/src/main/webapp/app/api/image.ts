import { ApiResponse } from "./ApiResponse";

import { ImageInfo } from "../model/ImageInfo";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/image";

export const getImages = (): Promise<ApiResponse<ImageInfo[]>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const getImage = (id: number): Promise<ApiResponse<ImageInfo>> =>
    fetchData(PATH_PREFIX + "/" + id.toString() + "/info", HttpMethod.GET);

export const uploadImage = (image: File): Promise<ApiResponse<ImageInfo>> => {
    const data = new FormData();
    data.append("file", image);
    return fetchData(PATH_PREFIX + "/upload", HttpMethod.POST, data);
};
