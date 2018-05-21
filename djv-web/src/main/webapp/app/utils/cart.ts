import { Cart } from "../model/Cart";
import { isLoggedIn } from "./user";

import * as api from "../api";
import { ModifyCartRequest } from "../api/cart";
import { Product } from "../model/Product";

const CART_KEY = "cart";
const EMPTY_CART: Cart = {
    items: [],
    total: 0,
    user: undefined
};

const localCartExists = () => {
    const json = localStorage.getItem(CART_KEY);
    return json !== undefined && json !== null && json.length > 0;
};

const initializeLocalCartIfNotExists = () => {
    if (!localCartExists()) saveLocalCart(EMPTY_CART);
};

const getLocalCart = (): Cart =>
    localCartExists() ? JSON.parse(localStorage.getItem(CART_KEY)) : undefined;

const saveLocalCart = (cart: Cart) => localStorage.setItem(CART_KEY, JSON.stringify(cart));

const updateProductAmount = (cart: Cart, index: number, amount: number, reset = false) => {
    const item = cart.items[index];
    const newAmount = reset ? amount : amount + item.amount;
    item.amount = newAmount;
    item.total = newAmount * item.product.price;
};

const calculateTotal = (cart: Cart) =>
    cart.items.length > 0
        ? cart.items
                .map(i => i.amount * i.product.price)
                .reduce((a, b) => a+b)
        : 0;

export const getCart = async () => {
    if (isLoggedIn()) return await api.cart.getCart();
    initializeLocalCartIfNotExists();
    return getLocalCart();
};

export const addProduct = async (product: Product, amount: number) => {
    if (isLoggedIn()) return await api.cart.addToCart({productId: product.id, amount});
    initializeLocalCartIfNotExists();

    const cart = getLocalCart();
    let index = cart.items.findIndex(i => i.product.id === product.id);
    if (index === -1) {
        index = cart.items.length;
        cart.items.push({
            amount: 0,
            product,
            total: 0
        });
    }
    updateProductAmount(cart, index, amount);
    cart.total = calculateTotal(cart);
    saveLocalCart(cart);
    return cart;
};

export const updateAmount = async (product: Product, amount: number) => {
    if (isLoggedIn()) return await api.cart.updateAmount({productId: product.id, amount});
    initializeLocalCartIfNotExists();

    const cart = getLocalCart();
    const index = cart.items.findIndex(i => i.product.id === product.id);
    if (index === -1) {
        // TODO: maybe some error
        return cart;
    }
    updateProductAmount(cart, index, amount, true);
    cart.total = calculateTotal(cart);
    saveLocalCart(cart);
    return cart;
};

export const removeProduct = async (product: Product) => {
    if (isLoggedIn()) return await api.cart.removeFromCart(product.id);
    initializeLocalCartIfNotExists();
    const cart = getLocalCart();
    cart.items = cart.items.filter(i => i.product.id !== product.id);
    cart.total = calculateTotal(cart);
    saveLocalCart(cart);
    return cart;
};

export const onLogin = async () => {
    if (!isLoggedIn()) {
        console.error("Invalid `cart.onLogin` call");
        return;
    }
    const onlineCart = await api.cart.getCart();
    const offlineCart = getLocalCart();
    saveLocalCart(EMPTY_CART);
    const response = await Promise.all(
        offlineCart.items.map(
            item => api.cart.addToCart({
                productId: item.product.id,
                amount: item.amount
            })
        )
    );
    const errors = response.filter(r => api.isError(r));
    if (errors.length > 0) {
        return errors[0];
    }
    return await api.cart.getCart();
};
