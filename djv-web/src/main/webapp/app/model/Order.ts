import { Address } from "./Address";
import { Product } from "./Product";
import { User } from "./User";

export enum OrderStatus {
    CREATED    = "CREATED",
    PROCESSING = "PROCESSING",
    SENT       = "SENT",
    DELIVERED  = "DELIVERED"
}

export interface OrderItem {
    amount: number;
    total: number;
    product: Product;
}

export interface Order {
    id: number;
    createdDate: Date;
    shippingAddress: Address;
    status: OrderStatus;
    total: number;
    user: User;
    items: OrderItem[];
}

export interface OrderSummary {
    user: User;
    orderCount: number;
    totalSpending: number;
    averageSpending: number;
}
