package com.xeed.cheapnsale.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xeed.cheapnsale.fragments.ExpandableMenuListFragment;
import com.xeed.cheapnsale.fragments.MyOrderFragment;
import com.xeed.cheapnsale.service.model.Store;

public class StoreMenuTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private Store store;

    public StoreMenuTabPagerAdapter(Store store, FragmentManager fm, int count) {
        super(fm);
        this.tabCount = count;
        this.store = store;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ExpandableMenuListFragment expandableMenuListFragment = new ExpandableMenuListFragment();
                expandableMenuListFragment.store = store;
                return expandableMenuListFragment;
            case 1:
                return new MyOrderFragment();
            case 2:
                return new MyOrderFragment();
            default:
                return null;
        }
    }

}
