package com.xeed.cheapnsale.inject;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.service.CheapnsaleApi;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.user.signin.SignInManager;

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

    @Provides
    @Singleton
    SignInManager providesSignInManager(){
        return SignInManager.getInstance(application);
    }



}
