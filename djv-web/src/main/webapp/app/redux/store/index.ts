import { createStore, compose  } from "redux";

import rootReducer from "../reducers";

const enhancers = compose(
    (window as any).devToolsExtension ? (window as any).devToolsExtension() : (f: any) => f // tslint:disable-line
);

const store = createStore(rootReducer, enhancers);

export default store;
