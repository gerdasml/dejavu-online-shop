import { AuthAction, AuthActionType } from "../reducers/authReducer";

export const login = () =>
    ({type: AuthActionType.LOGIN});

export const logout = (): AuthAction =>
    ({type: AuthActionType.LOGOUT});
