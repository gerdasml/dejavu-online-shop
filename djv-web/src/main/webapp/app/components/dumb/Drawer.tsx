import * as React from 'react';

import { Sidebar, Segment, Button, Menu, Image, Icon, Header } from 'semantic-ui-react';

import "../../../style/drawer.css";

export interface IMyState { visible: boolean; }

export class Drawer extends React.Component<{}, IMyState> {
    constructor(props: any){
        super(props);
        this.state = {
            visible: true
        }
    }
    render () {
        return (
            <div>
                <Sidebar.Pushable as={Segment}>
                <Sidebar className="drawer" as={Menu} animation='push' width='thin' visible={this.state.visible} icon='labeled' vertical inverted/>
                <Sidebar.Pusher>
                    <Segment basic className="content"/>
                </Sidebar.Pusher>
                </Sidebar.Pushable>
            </div>
        );
    }
}