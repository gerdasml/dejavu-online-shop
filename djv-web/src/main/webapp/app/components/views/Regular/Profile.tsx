import * as React from "react";

import {Grid, Header} from "semantic-ui-react";

import "../../../../style/profile.css";

import {OrderHistory} from "../../dumb/Profile/OrderHistory";
import {ProfileInfo} from "../../dumb/Profile/ProfileInfo";

export class Profile extends React.Component<{},{}> {
    render () {
        return(
            <Grid stackable>
                <Grid.Column width={6}>
                    <Header size="large" textAlign="center">User profile information</Header>
                    <ProfileInfo/>
                </Grid.Column>
                <Grid.Column width={10}>
                <Header size="large" textAlign="center">Order History</Header>
                <OrderHistory/>
                </Grid.Column>
            </Grid>
        );
    }
}
