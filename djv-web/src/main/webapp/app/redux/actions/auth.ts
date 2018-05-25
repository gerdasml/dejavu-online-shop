import { AuthAction, AuthActionType } from "../reducers/authReducer";
import { Action } from "redux";
import { clearToken, storeToken} from "../../utils/token";

export const login = (token?: string): AuthAction => {
    if (token) {
        storeToken(token);
    }
    return ({type: AuthActionType.LOGIN});
};

export const  logout = (): AuthAction => {
    clearToken();
    return ({type: AuthActionType.LOGOUT});
};
