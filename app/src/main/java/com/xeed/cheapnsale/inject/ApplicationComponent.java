package com.xeed.cheapnsale.inject;

import com.xeed.cheapnsale.fragments.StoreListFragment;
import com.xeed.cheapnsale.service.CheapnsaleService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={ApplicationModule.class})
public interface ApplicationComponent {
    void inject(CheapnsaleService cheapnsaleService);
    void inject(StoreListFragment storeListFragment);
}
