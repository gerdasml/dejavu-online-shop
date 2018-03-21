import * as React from "react";

import { Header } from "./Header/Header";
import { DrawerMenu } from "./Menu/Drawer";

export const Layout = () =>
    <div>
        <Header />
        <DrawerMenu />
    </div>;
