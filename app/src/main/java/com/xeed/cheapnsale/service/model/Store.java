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

public class Store implements Serializable {

    @SerializedName("ID")
    private String id = null;
    @SerializedName("CATEGORY")
    private String category = null;
    @SerializedName("REG_NUM")
    private String regNum = null;
    @SerializedName("NAME")
    private String name = null;
    @SerializedName("PAYMENT_TYPE")
    private String paymentType;
    @SerializedName("AVG_PREP_TIME")
    private String avgPrepTime;
    @SerializedName("MAIN_IMG")
    private String mainImageUrl = null;
    @SerializedName("MENUS")
    private ArrayList<Menu> menus = new ArrayList<>();
    @SerializedName("GPS_COORDINATES_LAT")
    private Double gpsCoordinatesLat = 0d;
    @SerializedName("GPS_COORDINATES_LONG")
    private Double gpsCoordinatesLong = 0d;

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
}
