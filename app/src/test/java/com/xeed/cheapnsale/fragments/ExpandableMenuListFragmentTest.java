package com.xeed.cheapnsale.fragments;

import android.support.v7.widget.RecyclerView;

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
public class ExpandableMenuListFragmentTest {

    private ExpandableMenuListFragment expandableMenuListFragment;

    @Before
    public void setUp() throws Exception {
        expandableMenuListFragment = new ExpandableMenuListFragment();
        SupportFragmentTestUtil.startVisibleFragment(expandableMenuListFragment);
    }

    @Test
    public void whenGetRecyclerViewChildCount_thenCountValueIsTen() throws Exception {
        RecyclerView recyclerView = (RecyclerView) expandableMenuListFragment.getView().findViewById(R.id.menu_list_recycler_view);
        assertThat(recyclerView.getAdapter().getItemCount()).isEqualTo(10);
    }
}