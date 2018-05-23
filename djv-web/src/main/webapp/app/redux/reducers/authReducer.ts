import { ReducersMapObject, Reducer } from "redux";

export interface AuthReducerState {
    loggedIn?: boolean;
}

export interface AuthAction {
    type: AuthActionType;
}

export enum AuthActionType {
    LOGIN,
    LOGOUT,
}

const initialState: AuthReducerState = {
    loggedIn: false,
};

export default  (state: AuthReducerState = initialState, action: AuthAction): AuthReducerState => {
    switch (action.type) {
        case AuthActionType.LOGIN:
            console.log("loggin");
            return {
               ...state,
                loggedIn: true,
            };
        case AuthActionType.LOGOUT:
            console.log("logout");
            return {
                ...state,
                loggedIn: false,
            };
        default:
            return state;
    }
};
