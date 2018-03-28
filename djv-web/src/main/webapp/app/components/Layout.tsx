import * as React from "react";

import { Footer } from "./dumb/Footer/Footer";
import { Header } from "./dumb/Header/Header";
import { Main } from "./Main";

export const Layout = () =>
    <div>
        <Header />
        <Main />
        <Footer/>
    </div>;
