/*
 * Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.xeed.cheapnsale.service.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Store implements Serializable {

    @SerializedName("ID")
    private String id;
    @SerializedName("CATEGORY")
    private String category;
    @SerializedName("REG_NUM")
    private String regNum;
    @SerializedName("NAME")
    private String name;
    @SerializedName("PAYMENT_TYPE")
    private String paymentType;
    @SerializedName("AVG_PREP_TIME")
    private String avgPrepTime;
    @SerializedName("MAIN_IMG")
    private String mainImageUrl;
    @SerializedName("MENUS")
    private ArrayList<Menu> menus = new ArrayList<>();
    @SerializedName("PHONE")
    private String phoneNumber;
    @SerializedName("GPS_COORDINATES_LAT")
    private Double gpsCoordinatesLat = 0d;
    @SerializedName("GPS_COORDINATES_LONG")
    private Double gpsCoordinatesLong = 0d;
    @SerializedName("OPEN_TIME")
    private String openTime;
    @SerializedName("CLOSE_TIME")
    private String closeTime;
    @SerializedName("ADDRESS")
    private String address;
    @SerializedName("END_TIME")
    private String endTime;

    private int distanceToStore = 0;

    public int getDistanceToStore() {
        return distanceToStore;
    }

    public void setDistanceToStore(int distanceToStore) {
        this.distanceToStore = distanceToStore;
    }

    public Double getGpsCoordinatesLat() {
        return gpsCoordinatesLat;
    }

    public void setGpsCoordinatesLat(Double gpsCoordinatesLat) {
        this.gpsCoordinatesLat = gpsCoordinatesLat;
    }

    public Double getGpsCoordinatesLong() {
        return gpsCoordinatesLong;
    }

    public void setGpsCoordinatesLong(Double gpsCoordinatesLong) {
        this.gpsCoordinatesLong = gpsCoordinatesLong;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAvgPrepTime() {
        return avgPrepTime;
    }

    public void setAvgPrepTime(String avgPrepTime) {
        this.avgPrepTime = avgPrepTime;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static Comparator<Store> DistanceToStoreAsc = new Comparator<Store>() {

        public int compare(Store s1, Store s2) {

            int distanceToStore1 = s1.getDistanceToStore();
            int distanceToStore2 = s2.getDistanceToStore();

	   /*For ascending order*/
            return distanceToStore1 - distanceToStore2;
        }
    };
}
