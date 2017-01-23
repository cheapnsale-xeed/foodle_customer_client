package com.xeed.cheapnsale.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order {

    @SerializedName("MENUS")
    private ArrayList<OrderMenu> menus;
    @SerializedName("ORDER_ID")
    private String orderId;
    @SerializedName("STORE_ID")
    private String storeId;
    @SerializedName("STORE_NAME")
    private String storeName;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("PICKUP_TIME")
    private String pickupTime;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getPickupTime() {
        return pickupTime;
    }
    public void setPickupTime(String pICKUP_TIME) {
        pickupTime = pICKUP_TIME;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String sTATUS) {
        status = sTATUS;
    }
    public ArrayList<OrderMenu> getMenus() {
        return menus;
    }
    public void setMenus(ArrayList<OrderMenu> mENUS) {
        menus = mENUS;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String oRDER_ID) {
        orderId = oRDER_ID;
    }
    public String getStoreId() {
        return storeId;
    }
    public void setStoreId(String sTORE_ID) {
        storeId = sTORE_ID;
    }

    public class OrderMenu extends Menu {

        @SerializedName("MENU_COUNT")
        private int menuCount;
        @SerializedName("MENU_TOTAL_PRICE")
        private int menuTotalPrice;

        public int getMenuCount() {
            return menuCount;
        }
        public void setMenuCount(int mENU_COUNT) {
            menuCount = mENU_COUNT;
        }
        public int getMenuTotalPrice() {
            return menuTotalPrice;
        }
        public void setMenuTotalPrice(int mENU_TOTAL_PRICE) {
            menuTotalPrice = mENU_TOTAL_PRICE;
        }

    }

}
