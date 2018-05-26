import * as React from "react";
import * as ReactDOM from "react-dom";

import { HashRouter } from "react-router-dom";
import { Layout } from "./components/Layout";
import { Provider } from "react-redux";
import store from "./redux/store";

ReactDOM.render(
    <Provider store={store}>
        <HashRouter>
            <Layout />
        </HashRouter>
    </Provider>,
    document.getElementById("react")
);
