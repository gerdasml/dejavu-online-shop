import { ReducersMapObject, Reducer, Action } from "redux";
import { getToken } from "../../utils/token";

export interface AuthReducerState {
    loggedIn: boolean;
}

export class AuthAction implements Action {
    type: AuthActionType;
}

export enum AuthActionType {
    LOGIN,
    LOGOUT,
}

const initialState: AuthReducerState = {
    loggedIn: getToken() !== null,
};

export default  (state: AuthReducerState = initialState, action: AuthAction): AuthReducerState => {
    switch (action.type) {
        case AuthActionType.LOGIN:
            return {
               ...state,
                loggedIn: true,
            };
        case AuthActionType.LOGOUT:
            return {
                ...state,
                loggedIn: false,
            };
        default:
            return state;
    }
};
