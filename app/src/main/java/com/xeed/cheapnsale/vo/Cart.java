package com.xeed.cheapnsale.vo;

import java.util.ArrayList;

public class Cart {

    private String storeId;
    private ArrayList<CartItem> cartItems;

    public Cart() {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getTotalCount() {
        return cartItems.size();
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < cartItems.size(); ++i) {
            totalPrice += cartItems.get(i).getPrice();
        }

        return totalPrice;
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void removeCartItem(CartItem cartItem) {
        for (int i = 0; i < cartItems.size(); ++i) {
            if (cartItems.get(i).getMenuId().equals(cartItem.getMenuId())) {
                cartItems.remove(i);
                break;
            }
        }
    }

    public void removeAllCartItem(CartItem cartItem) {
        for (int i = 0; i < cartItems.size(); ++i) {
            if (cartItems.get(i).getMenuId().equals(cartItem.getMenuId())) {
                cartItems.remove(i);
            }
        }
    }

    public void clearCartItems() {
        cartItems.clear();
    }

}

