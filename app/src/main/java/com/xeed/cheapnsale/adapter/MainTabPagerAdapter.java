package com.xeed.cheapnsale.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xeed.cheapnsale.fragments.MyOrderFragment;
import com.xeed.cheapnsale.fragments.StoreListFragment;

public class MainTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MainTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StoreListFragment();
            case 1:
                return new MyOrderFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
