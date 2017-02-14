package com.xeed.cheapnsale;

import android.location.Location;
import android.support.annotation.VisibleForTesting;

import com.xeed.cheapnsale.inject.ApplicationComponent;
import com.xeed.cheapnsale.inject.ApplicationModule;
import com.xeed.cheapnsale.inject.DaggerApplicationComponent;
import com.xeed.cheapnsale.service.model.Cart;

public class Application extends android.app.Application {

    private ApplicationComponent applicationComponent;

    private Cart cart;
    private String userEmail = "cheapnsale.xeed@gmail.com";
    private String userName = "이혜수";
    private String userPhone = "010-1234-5678";
    private Location myLocation;
    private String fcmToken = null;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    public Cart getCart() {
        return cart;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        if (cart == null) {
            cart = new Cart();
        }
    }



    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @VisibleForTesting
    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
