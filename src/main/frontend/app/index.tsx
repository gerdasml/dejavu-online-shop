import * as React from "react";
import * as ReactDOM from "react-dom";

import { Hello } from "./Hello";

import "../style/style.css";

ReactDOM.render(
    <Hello compiler="TypeScript" framework="React" />,
    document.getElementById("react")
);
