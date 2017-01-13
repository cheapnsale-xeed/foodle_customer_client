package com.xeed.cheapnsale.inject;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.service.CheapnsaleApi;
import com.xeed.cheapnsale.service.CheapnsaleService;

import javax.inject.Singleton;
import com.xeed.cheapnsale.vo.MenuItems;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
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
    @Provides
    @Singleton
    CheapnsaleService providesCheapnsaleService() {
        return new CheapnsaleService(application);
    }

    @Provides
    @Singleton
    CheapnsaleApi providesCheapnsaleApi() {
        return new ApiClientFactory().build(CheapnsaleApi.class);
    }

    @Provides
    @Singleton
    Picasso providesPicasso(){
        return Picasso.with(application);
    }

}
