package com.xeed.cheapnsale.inject;

import com.xeed.cheapnsale.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    List<String> providesList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            list.add("Item = " + i);
        }

        return list;
    }
}
