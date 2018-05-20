import { ApiResponse } from "./ApiResponse";

import { Card, ValidationError } from "../model/Payment";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/payment";

export const validate = (card: Card): Promise<ApiResponse<ValidationError[]>> =>
    fetchData(PATH_PREFIX + "/validate", HttpMethod.POST, card);
