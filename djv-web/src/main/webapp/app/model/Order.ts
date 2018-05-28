import { Address } from "./Address";
import { Product } from "./Product";
import { User } from "./User";
import { Review } from "./Review";
import { ShippingInformation } from "./ShippingInformation";

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
    shippingInformation: ShippingInformation;
    status: OrderStatus;
    total: number;
    user: User;
    items: OrderItem[];
    reviewShown: boolean;
    review: Review;
    lastModified: Date;
}

export interface OrderSummary {
    user: User;
    orderCount: number;
    totalSpending: number;
    averageSpending: number;
}
