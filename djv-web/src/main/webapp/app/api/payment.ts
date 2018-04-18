import { ApiResponse } from "../model/ApiResponse";
import { Payment, ValidationError } from "../model/Payment";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/payment";

export const pay = (payment: Payment): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/pay", HttpMethod.POST, payment);

export const validate = (payment: Payment): Promise<ApiResponse<ValidationError[]>> =>
    fetchData(PATH_PREFIX + "/validate", HttpMethod.POST, payment);
