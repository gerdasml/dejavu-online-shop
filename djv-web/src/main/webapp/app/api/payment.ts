import { ApiResponse } from "./ApiResponse";

import { Payment, ValidationError } from "../model/Payment";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/payment";

export const validate = (payment: Payment): Promise<ApiResponse<ValidationError[]>> =>
    fetchData(PATH_PREFIX + "/validate", HttpMethod.POST, payment);
