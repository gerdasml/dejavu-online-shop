import * as React from "react";

import { Button, notification, Popconfirm } from "antd";
import { User } from "../../../../model/User";

import * as api from "../../../../api";

interface BanButtonProps {
    user: User;
    onSwitch: () => void;
}

interface BanButtonState {
    isBanned: boolean;
    isLoading: boolean;
}

export class BanButton extends React.Component<BanButtonProps, BanButtonState> {
    state: BanButtonState = {
        isBanned: false,
        isLoading: false
    };

    componentDidMount () {
        this.setState({...this.state, isBanned: this.props.user.banned});
    }

    applyBan = async (ban: boolean) => {
        const response = await api.user.banUser(this.props.user.id, ban);
        if(api.isError(response)) {
            notification.error({ message: "Failed to update user ban state", description: response.message });
            return;
        }
        this.setState({...this.state, isBanned: ban});
        this.props.onSwitch();
    }

    handleBan = async (ban: boolean) => {
        this.setState({...this.state, isLoading: true});
        await this.applyBan(ban);
        this.setState({...this.state, isLoading: false});
    }

    render () {
        let confirmText: string;
        let buttonText: string;
        let buttonAction: () => void;

        const { isBanned, isLoading } = this.state;

        if(isBanned) {
            confirmText = "Are you sure you want to unban this user?";
            buttonText = "Unban";
            buttonAction = () => this.handleBan(false);
        } else {
            confirmText = "Are you sure you want to ban this user?";
            buttonText = "Ban";
            buttonAction = () => this.handleBan(true);
        }

        return (
            <Popconfirm title={confirmText} cancelText="No" okText="Yes" onConfirm={buttonAction}>
                <Button loading={isLoading} ghost={isBanned} type="danger">{buttonText}</Button>
            </Popconfirm>
        );
    }
}
