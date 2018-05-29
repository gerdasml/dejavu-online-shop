import { combineReducers } from "redux";
import authReducer, {AuthReducerState} from "./authReducer";

export interface StoreState {
    auth: AuthReducerState;
}

const rootReducer = combineReducers({
    auth: authReducer
});

export default rootReducer;
