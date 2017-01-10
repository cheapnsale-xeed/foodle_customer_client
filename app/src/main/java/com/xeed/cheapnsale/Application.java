package com.xeed.cheapnsale;

import com.xeed.cheapnsale.inject.ApplicationComponent;

public class Application extends android.app.Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // setApplicationComponent’
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }



}
