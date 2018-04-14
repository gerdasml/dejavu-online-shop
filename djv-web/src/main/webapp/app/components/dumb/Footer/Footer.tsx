import * as React from "react";
import {Grid, Icon} from "semantic-ui-react";

import "../../../../style/footer.css";

export class Footer extends React.Component <{},{}> {

    render () {
        return(
            <Grid id="footer">
                <Grid.Column width={7}>
                    © dejavu™, Inc.
                </Grid.Column>
                <Grid.Column width={2}>
                    <a href="https://github.com/gerdasml/dejavu-online-shop">
                        <Icon name="github" size="big" link color="black"/>
                    </a>
                </Grid.Column>
                <Grid.Column id="origin" width={7}>
                    Vilnius University, Lithuania, 2018.
                </Grid.Column>
            </Grid>
        );
    }
}
