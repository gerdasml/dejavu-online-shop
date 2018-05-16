import * as React from "react";

const InlineSvg = require("svg-inline-react");
const largeLogo = require("../../../assets/djv-logo.svg");
const smallLogo = require("../../../assets/djv-logo-small.svg");

interface LogoProps {
    size: "large" | "small";
}

export const Logo = (props: LogoProps) => (
    <InlineSvg.default
        src={props.size === "large" ? largeLogo : smallLogo}
    />
);
