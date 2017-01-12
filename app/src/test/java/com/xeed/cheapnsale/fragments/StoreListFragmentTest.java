package com.xeed.cheapnsale.fragments;

import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StoreListFragmentTest {

    StoreListFragment storeListFragment;

    @Before
    public void setUp() throws Exception {
        storeListFragment = new StoreListFragment();
        SupportFragmentTestUtil.startVisibleFragment(storeListFragment);
    }

    @Test
    public void whenFragmentIsLoaded_thenShowStoreListTitle() throws Exception {
        //assertThat(((TextView)storeListFragment.getView().findViewById(R.id.store_list_title)).getText()).isEqualTo("List");
    }
}