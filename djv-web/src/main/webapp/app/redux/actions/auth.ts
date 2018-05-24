import { AuthAction, AuthActionType } from "../reducers/authReducer";

export const login = (): AuthAction =>
    ({type: AuthActionType.LOGIN});

export const logout = (): AuthAction =>
    ({type: AuthActionType.LOGOUT});
