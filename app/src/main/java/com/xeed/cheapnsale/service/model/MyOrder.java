package com.xeed.cheapnsale.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyOrder {

    @SerializedName("EMAIL")
    private String email;
    @SerializedName("ORDERS")
    private ArrayList<Order> orders;

    public String getEmail() {
        return email;
    }

    public void setEmail(String eMAIL) {
        email = eMAIL;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> oRDERS) {
        orders = oRDERS;
    }

}

