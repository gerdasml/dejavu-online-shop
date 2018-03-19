interface ICategory {
    name: string;
    icon: string;
    displayName: string;
    children: ICategory[];
}

export const categories: ICategory[] = [
    {
        children: [],
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
