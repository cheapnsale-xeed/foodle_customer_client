package com.xeed.cheapnsale;

import android.support.annotation.VisibleForTesting;

import com.xeed.cheapnsale.inject.ApplicationComponent;
import com.xeed.cheapnsale.inject.ApplicationModule;
import com.xeed.cheapnsale.inject.DaggerApplicationComponent;
import com.xeed.cheapnsale.vo.Cart;

public class Application extends android.app.Application {

    private ApplicationComponent applicationComponent;

    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    private String userEmail = "cheapnsale.xeed@gmail.com";

    public String getUserEmail() {
        return userEmail;
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
