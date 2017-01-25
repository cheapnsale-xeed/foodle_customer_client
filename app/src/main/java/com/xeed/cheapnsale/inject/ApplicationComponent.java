package com.xeed.cheapnsale.inject;

import com.xeed.cheapnsale.OrderActivity;
import com.xeed.cheapnsale.StoreDetailActivity;
import com.xeed.cheapnsale.activity.MapActivity;
import com.xeed.cheapnsale.adapter.MyOrderCurrentAdapter;

import com.xeed.cheapnsale.fragments.ExpandableMenuListFragment;
import com.xeed.cheapnsale.fragments.MyOrderFragment;
import com.xeed.cheapnsale.fragments.StoreListFragment;
import com.xeed.cheapnsale.holder.ExpandableMenuHeadHolder;
import com.xeed.cheapnsale.holder.CartListHolder;
import com.xeed.cheapnsale.holder.MapStoreListHolder;
import com.xeed.cheapnsale.holder.StoreListHolder;
import com.xeed.cheapnsale.service.CheapnsaleService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={ApplicationModule.class})
public interface ApplicationComponent {
    void inject(CheapnsaleService cheapnsaleService);
    void inject(StoreListFragment storeListFragment);
    void inject(StoreListHolder storeListHolder);
    void inject(MyOrderFragment myOrderFragment);
    void inject(MyOrderCurrentAdapter.MyOrderCurrentHolder myOrderCurrentHolder);
    void inject(CartListHolder cartListHolder);
    void inject(StoreDetailActivity storeDetailActivity);
    void inject(OrderActivity orderActivity);
    void inject(ExpandableMenuListFragment expandableMenuListFragment);
    void inject(ExpandableMenuHeadHolder expandableMenuHeadHolder);
    void inject(MapActivity mapActivity);
    void inject(MapStoreListHolder mapStoreListHolder);

}
