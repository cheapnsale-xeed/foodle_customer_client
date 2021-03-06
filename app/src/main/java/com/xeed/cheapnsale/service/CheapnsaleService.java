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

package com.xeed.cheapnsale.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.service.model.Order;
import com.xeed.cheapnsale.service.model.Payment;
import com.xeed.cheapnsale.service.model.SMSAuth;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.service.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

// 각 메소드의 파라미터는 한개만 허용이됨, 왜냐하면 lambda의 handleRequest에서 input이 한개이기 때문임

public class CheapnsaleService {

    @Inject
    CheapnsaleApi cheapnsaleApi;

    private Gson gson = new Gson();

    public CheapnsaleService(Context context) {
        ((Application) context).getApplicationComponent().inject(this);
    }

    public ArrayList<Store> getStoreList() {
        JsonElement stores = cheapnsaleApi.getStores();
        return gson.fromJson(stores, new TypeToken<ArrayList<Store>>() {}.getType());
    }

    public Store getStore(String storeId) {
        return cheapnsaleApi.getStore(storeId);
    }

    public ArrayList<Order> getMyOrder(String email) {
        JsonElement orders = cheapnsaleApi.getMyOrder(email);
        return gson.fromJson(orders, new TypeToken<ArrayList<Order>>(){}.getType());
    }

    public ArrayList<Order> getMyCurrentOrder(String email) {
        JsonElement orders = cheapnsaleApi.getMyCurrentOrder(email);
        return gson.fromJson(orders, new TypeToken<ArrayList<Order>>(){}.getType());
    }

    public Payment putPreparePayment(Order order) {
        return cheapnsaleApi.putPreparePayments(order);
    }

    public String putPrepareSMSAuth(String authID) {
        return cheapnsaleApi.putPrepareSMSAuth(authID);
    }

    public String getConfirmSMSAuth(SMSAuth smsAuth) {
        return cheapnsaleApi.getConfirmSMSAuth(smsAuth);
    }

    public User putUserLoginInfo(User user){
        return cheapnsaleApi.putUserLoginInfo(user);
    }

    public ArrayList<Store> getStoreListByLocation(HashMap<String, Double> myLocation) {
        JsonElement stores = cheapnsaleApi.getStoresByLocation(myLocation);
        return gson.fromJson(stores, new TypeToken<ArrayList<Store>>() {}.getType());
    }
}

