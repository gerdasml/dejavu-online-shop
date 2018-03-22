import * as React from "react";

import { Header } from "./dumb/Header/Header";
import { DrawerMenu } from "./dumb/Menu/Drawer";
import { Main } from "./Main";

export const Layout = () =>
    <div>
        <Header />
        <Main />
    </div>;
