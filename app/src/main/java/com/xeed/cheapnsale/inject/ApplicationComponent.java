package com.xeed.cheapnsale.inject;

import com.xeed.cheapnsale.activity.MainActivity;
import com.xeed.cheapnsale.activity.MapActivity;
import com.xeed.cheapnsale.activity.OrderActivity;
import com.xeed.cheapnsale.activity.SMSAuthActivity;
import com.xeed.cheapnsale.activity.SignUpActivity;
import com.xeed.cheapnsale.activity.SplashActivity;
import com.xeed.cheapnsale.activity.StoreDetailActivity;
import com.xeed.cheapnsale.activity.TermsConditionsActivity;
import com.xeed.cheapnsale.adapter.CartListAdapter;
import com.xeed.cheapnsale.adapter.MyOrderCurrentAdapter;
import com.xeed.cheapnsale.adapter.MyOrderPastAdapter;
import com.xeed.cheapnsale.fragments.MainFragment;
import com.xeed.cheapnsale.fragments.MenuListFragment;
import com.xeed.cheapnsale.fragments.MyOrderFragment;
import com.xeed.cheapnsale.fragments.StoreListFragment;
import com.xeed.cheapnsale.holder.CartListHeadHolder;
import com.xeed.cheapnsale.holder.MapStoreListHolder;
import com.xeed.cheapnsale.holder.MenuListHeadHolder;
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
    void inject(MyOrderPastAdapter.MyOrderPastHolder myOrderPastHolder);
    void inject(CartListHeadHolder cartListHeadHolder);
    void inject(StoreDetailActivity storeDetailActivity);
    void inject(OrderActivity orderActivity);
    void inject(MenuListFragment menuListFragment);
    void inject(MenuListHeadHolder menuListHeadHolder);
    void inject(MapActivity mapActivity);
    void inject(MapStoreListHolder mapStoreListHolder);
    void inject(MainActivity mainActivity);
    void inject(SMSAuthActivity smsAuthActivity);
    void inject(CartListAdapter cartListAdapter);
    void inject(SignUpActivity signUpActivity);
    void inject(SplashActivity splashActivity);
    void inject(TermsConditionsActivity termsConditionsActivity);
    void inject(MainFragment mainFragment);

}
