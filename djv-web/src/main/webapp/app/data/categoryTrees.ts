import { Category } from "../components/views";
import { CategoryTree } from "../model/CategoryTree";

export const categories: CategoryTree[] = [
    {
        category: {
            displayName: "Telefonai",
            icon: "home",
            name: "home",
            },
        children: [
            {
                category: {
                    displayName: "Liečiamųjų ekranų",
                    icon: "home",
                    name: "home",
                },
                children: [],
            },
            {
                category: {
                    displayName: "Knopkiniai",
                    icon: "home",
                    name: "home",
                },
                children: [],
            }
        ],
}];
