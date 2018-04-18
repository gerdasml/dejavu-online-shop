import { IImageInfo } from "../model/ImageInfo";
import { fetchData, HttpMethod } from "./utils";
import { ApiResponse } from "../model/ApiResponse";

const PATH_PREFIX = "/api/image";

export const getImages = (): Promise<ApiResponse<IImageInfo[]>> =>
    fetchData(PATH_PREFIX + "/", HttpMethod.GET);

export const getImage = (id: number): Promise<ApiResponse<IImageInfo>> =>
    fetchData(PATH_PREFIX + "/" + id.toString() + "/info", HttpMethod.GET);

export const uploadImage = (image: File): Promise<ApiResponse<IImageInfo>> =>
    fetchData(PATH_PREFIX + "/upload", HttpMethod.POST, image);
