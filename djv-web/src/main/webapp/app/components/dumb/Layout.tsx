import * as React from "react";

import { Header } from "./Header/Header";
import { DrawerMenu } from "./Menu/Drawer";
import { Main } from "./Main";

export const Layout = () =>
    <div>
        <Header />
        <Main />
    </div>;
