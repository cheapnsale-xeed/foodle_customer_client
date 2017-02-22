package com.xeed.cheapnsale;

import android.location.Location;

import com.xeed.cheapnsale.inject.DaggerTestApplicationComponent;
import com.xeed.cheapnsale.inject.TestApplicationComponent;
import com.xeed.cheapnsale.inject.TestApplicationModule;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

public class TestApplication extends Application implements TestLifecycleApplication {

    private TestApplicationComponent testApplicationComponent;

    private Location myLocation;

    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {

    }

    @Override
    public void afterTest(Method method) {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        testApplicationComponent = DaggerTestApplicationComponent.builder()
                .applicationModule(new TestApplicationModule(this)).build();
        setApplicationComponent(testApplicationComponent);
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }
}
