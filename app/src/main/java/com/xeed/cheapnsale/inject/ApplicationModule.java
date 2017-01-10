package com.xeed.cheapnsale.inject;

import com.xeed.cheapnsale.Application;

import dagger.Module;

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

//    @Provides
//    @Singleton
//    RecyclerView providesMenuListAdapter() {
//    }
}
