package com.xeed.cheapnsale.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    @SerializedName("EMAIL")
    private String email;
    @SerializedName("MENUS")
    private ArrayList<Menu> menus;
    @SerializedName("ORDER_ID")
    private String orderId;
    @SerializedName("STORE_ID")
    private String storeId;
    @SerializedName("ORDER_AT")
    private String orderAt;
    @SerializedName("STORE_NAME")
    private String storeName;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("PICKUP_TIME")
    private String pickupTime;
    @SerializedName("FCM_TOKEN")
    private String fcmToken;
    @SerializedName("USER_NAME")
    private String userName;
    @SerializedName("USER_PHONE")
    private String userPhone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(String orderAt) {
        this.orderAt = orderAt;
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

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> mENUS) {
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

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public enum STATUS {
        READY,
        FINISH,
        PREPARE,
        CANCEL,
        DONE,
    }

    ;
}
