package com.xeed.cheapnsale.inject;

import com.xeed.cheapnsale.OrderActivity;
import com.xeed.cheapnsale.StoreDetailActivity;
import com.xeed.cheapnsale.activity.MapActivity;
import com.xeed.cheapnsale.adapter.StoreListAdapter;

import com.xeed.cheapnsale.fragments.ExpandableMenuListFragment;
import com.xeed.cheapnsale.fragments.StoreListFragment;
import com.xeed.cheapnsale.holder.ExpandableMenuListHolder;
import com.xeed.cheapnsale.holder.CartListHolder;
import com.xeed.cheapnsale.service.CheapnsaleService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={ApplicationModule.class})
public interface ApplicationComponent {
    void inject(CheapnsaleService cheapnsaleService);
    void inject(StoreListFragment storeListFragment);
    void inject(StoreListAdapter.StoreListHolder storeListHolder);
    void inject(CartListHolder cartListHolder);
    void inject(StoreDetailActivity storeDetailActivity);
    void inject(OrderActivity orderActivity);
    void inject(ExpandableMenuListFragment expandableMenuListFragment);
    void inject(ExpandableMenuListHolder expandableMenuListHolder);
    void inject(MapActivity mapActivity);
}
