import * as React from "react";

import { Button, Collapse } from "antd";
import { Icon, SemanticICONS } from "semantic-ui-react";

import { icons, IconSet } from "../../../../data/icons";

interface IconSelectProps {
    onSelect: (iconName: string) => void;
}

const mapIcon = (icon: string, key: number, props: IconSelectProps) => (
    <Button size="small" key={key} onClick={() => props.onSelect(icon)}><Icon name={icon as SemanticICONS} /></Button>
);

const mapIconSet = (set: IconSet, key: number, props: IconSelectProps) => (
    <Collapse.Panel header={set.name} key={key.toString()}>
        {set.values.map((i, idx) => mapIcon(i, idx, props))}
    </Collapse.Panel>
);

export const IconSelect = (props: IconSelectProps) => (
    <Collapse>
        {icons.map((i, idx) => mapIconSet(i, idx, props))}
    </Collapse>
);
