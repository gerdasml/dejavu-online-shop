export interface ICategory {
    name: string;
    icon: string;
    displayName: string;
    children: ICategory[];
}

export const categories: ICategory[] = [
    {
        children: [
            {
                children: [
                    {
                        children: [],
                        displayName: "Home11",
                        icon: "home",
                        name: "home"
                    },
                    {
                        children: [],
                        displayName: "Home12",
                        icon: "home",
                        name: "home"
                    }
                ],
                displayName: "Home1",
                icon: "home",
                name: "home",
            },
            {
                children: [
                    {
                        children: [],
                        displayName: "Home21",
                        icon: "home",
                        name: "home"
                    },
                    {
                        children: [],
                        displayName: "Home22",
                        icon: "home",
                        name: "home"
                    }
                ],
                displayName: "Home2",
                icon: "home",
                name: "home",
            }
        ],
        displayName: "Home",
        icon: "home",
        name: "home",
    },
    {
        children: [],
        displayName: "Gamepad",
        icon: "gamepad",
        name: "gamepad",
    },
    {
        children: [],
        displayName: "Camera",
        icon: "camera",
        name: "camera",
    }
];
