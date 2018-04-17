import { IPayment, IValidationError } from "../model/Payment";
import { fetchData, HttpMethod } from "./utils";

const PATH_PREFIX = "/api/payment";

export const pay = (payment: IPayment): Promise<void> =>
    fetchData(PATH_PREFIX + "/pay", HttpMethod.POST, payment);

export const validate = (payment: IPayment): Promise<IValidationError[]> =>
    fetchData(PATH_PREFIX + "/validate", HttpMethod.POST, payment);
