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

import com.amazonaws.mobileconnectors.apigateway.annotation.Operation;
import com.amazonaws.mobileconnectors.apigateway.annotation.Parameter;
import com.amazonaws.mobileconnectors.apigateway.annotation.Service;
import com.google.gson.JsonElement;
import com.xeed.cheapnsale.service.model.Order;
import com.xeed.cheapnsale.service.model.Payment;
import com.xeed.cheapnsale.service.model.Store;


@Service(endpoint = "https://cv47nyx5yl.execute-api.ap-northeast-1.amazonaws.com/prod")
public interface CheapnsaleApi {

    @Operation(path = "/stores", method = "GET")
    JsonElement getStores();

    @Operation(path = "/store/{storeId}", method = "GET")
    Store getStore(@Parameter(name = "storeId", location = "path") String storeId);

    @Operation(path = "/orders/{email}", method = "POST")
    JsonElement getMyOrder(@Parameter(name = "email", location = "path") String email);

    @Operation(path = "/orders/{email}/current", method = "POST")
    JsonElement getMyCurrentOrder(@Parameter(name = "email", location = "path") String email);

    @Operation(path = "payments/prepare", method = "PUT")
    Payment putPreparePayments(Order order);

    @Operation(path = "/sms/prepare", method = "PUT")
    int putPrepareSMSAuth(String authID);
}

