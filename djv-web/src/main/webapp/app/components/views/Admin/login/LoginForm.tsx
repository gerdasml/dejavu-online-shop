import * as React from "react";

import { Button, Icon, Input } from "antd";

export class LoginForm extends React.Component<{},{}> {
  render () {
    return (
        <div>
            <Input
                placeholder="Enter your username"
                //TODO: move to css
                prefix={<Icon type="user" style={{ color: "rgba(0,0,0,.25)" }} />}
            />
            <Input
                placeholder="Enter your password"
                prefix={<Icon type="lock" style={{ color: "rgba(0,0,0,.25)"}}/>}
            />
            <Button>Log in</Button>
        </div>
    );
  }
}
