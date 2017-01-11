package com.xeed.cheapnsale.inject;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.vo.MenuItems;

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
    List<MenuItems> providesList() {
        List<MenuItems> list = new ArrayList<>();
        MenuItems item;
        for (int i = 0; i < 10; i ++) {
            item = new MenuItems(0, "Item = " + i, "22,000원", "");
            list.add(item);
        }

        return list;
    }
}
