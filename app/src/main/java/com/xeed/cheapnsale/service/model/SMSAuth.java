package com.xeed.cheapnsale.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SMSAuth implements Serializable {

    @SerializedName("AUTH_ID")
    private String AUTH_ID;

    @SerializedName("AUTH_NUMBER")
    private int AUTH_NUMBER;

    @SerializedName("PHONE_NUMBER")
    private String PHONE_NUMBER;

    @SerializedName("USER_ID")
    private String USER_ID;

    public String getAUTH_ID() {
        return AUTH_ID;
    }

    public void setAUTH_ID(String AUTH_ID) {
        this.AUTH_ID = AUTH_ID;
    }

    public int getAUTH_NUMBER() {
        return AUTH_NUMBER;
    }

    public void setAUTH_NUMBER(int AUTH_NUMBER) {
        this.AUTH_NUMBER = AUTH_NUMBER;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public void setPHONE_NUMBER(String PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

}
