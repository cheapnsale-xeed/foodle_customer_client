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
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

import javax.inject.Inject;

public class CheapnsaleService {

    @Inject
    CheapnsaleApi cheapnsaleApi;

    public CheapnsaleService(Context context) {
        ((Application) context).getApplicationComponent().inject(this);
    }

    public ArrayList<Store> getStoreList() {
        JsonElement stores = cheapnsaleApi.getStores();
        return new Gson().fromJson(stores, new TypeToken<ArrayList<Store>>() {}.getType());
    }

}

