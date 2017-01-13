package com.xeed.cheapnsale.adapter;

import android.support.v4.app.FragmentManager;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.StoreDetailActivity;
import com.xeed.cheapnsale.fragments.ExpandableMenuListFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StoreMenuTabPagerAdapterTest {

    private FragmentManager supportFragmentManager;
    private StoreMenuTabPagerAdapter storeMenuTabPagerAdapter;

    @Before
    public void setUp() throws Exception {
        StoreDetailActivity storeDetailActivity = Robolectric.setupActivity(StoreDetailActivity.class);
        supportFragmentManager = storeDetailActivity.getSupportFragmentManager();
        storeMenuTabPagerAdapter = new StoreMenuTabPagerAdapter(supportFragmentManager, 3);
    }

    @Test
    public void whenAdapterIsCreated_thenTabCountIsThree() throws Exception {
        assertThat(storeMenuTabPagerAdapter.getCount()).isEqualTo(3);
    }

    @Test
    public void whenAdapterGetItems_thenReturnFragmentsAreCorrect() throws Exception {
        assertThat(storeMenuTabPagerAdapter.getItem(0).getClass()).isEqualTo(ExpandableMenuListFragment.class);
    }
}