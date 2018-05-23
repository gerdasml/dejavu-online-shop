import { createStore, applyMiddleware, compose  } from "redux";

import rootReducer from "../reducers";
import createHistory from "history/createBrowserHistory";

const enhancers = compose(
    window.devToolsExtension ? window.devToolsExtension() : f => f
);

const store = createStore(rootReducer, enhancers);

export default store;
