package com.xeed.cheapnsale.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("EMAIL")
    private String email;

    @SerializedName("PHONE")
    private String phone;

    @SerializedName("PHONE_CONFIRM")
    private String phoneConfirm;

    @SerializedName("PROVIDER")
    private String provider;

    @SerializedName("TAC_AGREE")
    private String tacAgree;

    @SerializedName("USER_ID")
    private String userId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneConfirm() {
        return phoneConfirm;
    }

    public void setPhoneConfirm(String phoneConfirm) {
        this.phoneConfirm = phoneConfirm;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTacAgree() {
        return tacAgree;
    }

    public void setTacAgree(String tacAgree) {
        this.tacAgree = tacAgree;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneConfirm='" + phoneConfirm + '\'' +
                ", provider='" + provider + '\'' +
                ", tacAgree='" + tacAgree + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
