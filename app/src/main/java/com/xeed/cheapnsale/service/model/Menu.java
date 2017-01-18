package com.xeed.cheapnsale.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Menu implements Serializable{

    private int menuType = 0;

    private int menuItemCount = 0;
    private int menuItemTotalPrice = 0;
    @SerializedName("MENU_ID")
    private String menuId = null;
    @SerializedName("MENU_NAME")
    private String menuName = null;
    @SerializedName("MENU_PRICE")
    private int menuPrice = 0;
    @SerializedName("MENU_IMG")
    private String menuImg = null;

    public Menu(){

    }

    public Menu(int menuType, String menuName, int menuPrice, String menuImg) {
        this.menuType = menuType;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuImg = menuImg;
    }

    public int getMenuItemCount() {
        return menuItemCount;
    }

    public void setMenuItemCount(int menuItemCount) {
        this.menuItemCount = menuItemCount;
    }

    public int getMenuItemTotalPrice() {
        return menuItemTotalPrice;
    }

    public void setMenuItemTotalPrice(int menuItemTotalPrice) {
        this.menuItemTotalPrice = menuItemTotalPrice;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }
}
