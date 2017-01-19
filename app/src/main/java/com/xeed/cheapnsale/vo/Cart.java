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

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getTotalCount() {
        int totalCount = 0;
        for (int i = 0; i < cartItems.size(); ++i) {
            totalCount += cartItems.get(i).getCount();
        }

        return totalCount;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < cartItems.size(); ++i) {
            totalPrice += (cartItems.get(i).getCount() * cartItems.get(i).getPrice());
        }

        return totalPrice;
    }

    // 장바구니화면에서 + 버튼을 눌러서 수량을 조정 할 경우 사용
    public void plusCartItem(String menuId) {
        for (int i = 0; i < cartItems.size(); ++i) {
            if (cartItems.get(i).getMenuId().equals(menuId)) {
                cartItems.get(i).setCount(cartItems.get(i).getCount() + 1);
                break;
            }
        }
    }

    // 장바구니화면에서 - 버튼을 눌러서 수량을 조정 할 경우 사용
    public void minusCartItem(String menuId) {
        for (int i = 0; i < cartItems.size(); ++i) {
            if (cartItems.get(i).getMenuId().equals(menuId)) {
                if (cartItems.get(i).getCount() < 2) break;
                cartItems.get(i).setCount(cartItems.get(i).getCount() - 1);
                break;
            }
        }
    }

    // 메뉴화면에서 담기버튼을 누를 때 사용
    public void addCartItem(CartItem cartItem) {
        boolean added = false;
        for (int i = 0; i < cartItems.size(); ++i) {
            if (cartItems.get(i).getMenuId().equals(cartItem.getMenuId())) {
                cartItems.get(i).setCount(cartItems.get(i).getCount() + cartItem.getCount());
                added = true;
                break;
            }
        }

        if (added == false) {
            cartItems.add(cartItem);
        }
    }

    public void deleteCartItem(String menuId) {
        for (int i = 0; i < cartItems.size(); ++i) {
            if (cartItems.get(i).getMenuId().equals(menuId)) {
                cartItems.remove(i);
            }
        }
    }

    public void clearCartItems() {
        cartItems.clear();
    }

}

