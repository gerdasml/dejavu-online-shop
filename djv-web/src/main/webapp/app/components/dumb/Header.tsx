import * as React from "react";

import {Button, Grid, Icon, Image, Search} from "semantic-ui-react";
import "../../../style/header.css";
// import logo from "../../assets/dejavu-logo-transperant.png";

/*export const Header = () =>
    <div className="header"></div>
;*/

export default class Header extends React.Component {
    /*constructor () {
        super(props: {});
        this.state = {
            test: "test"
        };
    }*/

    render () {
        return (
            <Grid className="header">
                <Grid.Row>
                    <Grid.Column width={3}>
                        <Image id="logo"
                            // TODO: change for local image when loaders are fixed
                            src="http://www.part.lt/img/1db15aa8a0de669f04c364eae9b6edb9383.png"
                            alt="Neveikia"
                        />
                    </Grid.Column>
                    <Grid.Column width={13}>
                        <Grid>

                            <Grid.Row>
                                <Grid.Column width={6}>
                                    <Icon name="mail outline" />
                                    dejavu.psk@gmail.com
                                </Grid.Column>
                                <Grid.Column width={6}>
                                    <Icon name="phone" />
                                    +3706NETIKRAS
                                </Grid.Column>
                                <Grid.Column width={4}>
                                    {/* TODO: make as a button */}
                                    <Icon name="info" />
                                    INFO
                                </Grid.Column>
                        </Grid.Row>
                        <Grid.Row>
                                <Grid.Column width={12}>
                                    {/* TODO: implement search */}
                                    <Search id="searchBar"
                                            value="Search..."
                                            noResultsMessage="Not implemented exception. xD"
                                            size="mini"
                                            fluid
                                    >
                                    </Search>
                                </Grid.Column>
                                <Grid.Column width={2}>
                                    {/* TODO: make as a button */}
                                    <Icon name ="cart"/>
                                </Grid.Column>
                                <Grid.Column width={2}>
                                    {/* TODO: make as a button */}
                                    <Icon name ="user circle outline"/>
                                </Grid.Column>
                            </Grid.Row>
                        </Grid>
                    </Grid.Column>
                </Grid.Row>
            </Grid>
        );
    }
}
