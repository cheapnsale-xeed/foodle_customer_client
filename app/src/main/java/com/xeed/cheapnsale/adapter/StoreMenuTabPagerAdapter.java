package com.xeed.cheapnsale.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xeed.cheapnsale.fragments.ExpandableMenuListFragment;

public class StoreMenuTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public StoreMenuTabPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.tabCount = count;

    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ExpandableMenuListFragment();
            case 1:
                return new ExpandableMenuListFragment();
            case 2:
                return new ExpandableMenuListFragment();
            default:
                return null;
        }
    }

}
