package com.xeed.cheapnsale;

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

//        initializeApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

//    private void initializeApplication() {
//        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());
//    }

}
