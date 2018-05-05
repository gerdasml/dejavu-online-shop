import { OrderItem } from "./Order";
import { User } from "./User";

export interface Cart {
    items: OrderItem[];
    total: number;
    user: User;
}
