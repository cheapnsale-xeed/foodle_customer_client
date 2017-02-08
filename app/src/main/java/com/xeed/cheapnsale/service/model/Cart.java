package com.xeed.cheapnsale.service.model;

import java.util.ArrayList;

public class Cart {

    private String storeId;

    private ArrayList<Menu> menus;

    private String storeName;

    public Cart() {
        if (menus == null) {
            menus = new ArrayList<>();
        }
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getTotalCount() {
        int totalCount = 0;
        for (int i = 0; i < menus.size(); ++i) {
            totalCount += menus.get(i).getMenuItemCount();
        }

        return totalCount;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < menus.size(); ++i) {
            totalPrice += (menus.get(i).getMenuItemCount() * menus.get(i).getMenuPrice());
        }

        return totalPrice;
    }

    // 장바구니화면에서 + 버튼을 눌러서 수량을 조정 할 경우 사용
    public void plusCartItem(String menuId) {
        for (int i = 0; i < menus.size(); ++i) {
            if (menus.get(i).getMenuId().equals(menuId)) {
                menus.get(i).setMenuItemCount(menus.get(i).getMenuItemCount() + 1);
                menus.get(i).setMenuItemTotalPrice(menus.get(i).getMenuItemCount() * menus.get(i).getMenuPrice());
                break;
            }
        }
    }

    // 장바구니화면에서 - 버튼을 눌러서 수량을 조정 할 경우 사용
    public void minusCartItem(String menuId) {
        for (int i = 0; i < menus.size(); ++i) {
            if (menus.get(i).getMenuId().equals(menuId)) {
                if (menus.get(i).getMenuItemCount() < 2) break;
                menus.get(i).setMenuItemCount(menus.get(i).getMenuItemCount() - 1);
                menus.get(i).setMenuItemTotalPrice(menus.get(i).getMenuItemCount() * menus.get(i).getMenuPrice());
                break;
            }
        }
    }

    // 메뉴화면에서 담기버튼을 누를 때 사용
    public void addCartItem(Menu cartItem) {
        boolean added = false;
        for (int i = 0; i < menus.size(); ++i) {
            if (menus.get(i).getMenuId().equals(cartItem.getMenuId())) {
                menus.get(i).setMenuItemCount(menus.get(i).getMenuItemCount() + cartItem.getMenuItemCount());
                added = true;
                break;
            }
        }

        if (!added) {
            menus.add(cartItem);
        }
    }

    public void deleteCartItem(String menuId) {
        for (int i = 0; i < menus.size()-1; ++i) {
            if (menus.get(i).getMenuId().equals(menuId)) {
                menus.remove(i);
            }
        }
    }

    public void clearCartItems() {
        menus.clear();
    }

}

