import * as React from 'react';

import "../../../style/header.css";
import * as api from "../../api";

export const Header = () =>
    <div className="header"></div>
;

const getUser = async () => {
    const tkn = await api.login("admin@email.com", "password");
    if(api.isBanned(tkn)) {
        console.log("You banned bro");
        return undefined;
    }
    localStorage.setItem("accessToken", tkn.token);
    const users = await api.getUsers();
    console.log(users);
    return users;
};

getUser();
