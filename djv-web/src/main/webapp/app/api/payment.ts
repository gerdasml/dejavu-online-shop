import { ApiResponse } from "../model/ApiResponse";
import { IPayment, IValidationError } from "../model/Payment";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/payment";

export const pay = (payment: IPayment): Promise<ApiResponse<void>> =>
    fetchData(PATH_PREFIX + "/pay", HttpMethod.POST, payment);

export const validate = (payment: IPayment): Promise<ApiResponse<IValidationError[]>> =>
    fetchData(PATH_PREFIX + "/validate", HttpMethod.POST, payment);
