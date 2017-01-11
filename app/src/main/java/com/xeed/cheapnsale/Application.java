package com.xeed.cheapnsale;

import android.support.annotation.VisibleForTesting;

import com.xeed.cheapnsale.inject.ApplicationComponent;
import com.xeed.cheapnsale.inject.ApplicationModule;
import com.xeed.cheapnsale.inject.DaggerApplicationComponent;

public class Application extends android.app.Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();


    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @VisibleForTesting
    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }



}
