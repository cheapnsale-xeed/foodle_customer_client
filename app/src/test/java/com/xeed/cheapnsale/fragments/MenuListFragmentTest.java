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
public class MenuListFragmentTest {

    MenuListFragment menuListFragment;

    @Before
    public void setUp() throws Exception {
        menuListFragment = new MenuListFragment();
        SupportFragmentTestUtil.startVisibleFragment(menuListFragment);

    }

    @Test
    public void whenFragmentIsLoaded_thenShowMenuListItems() throws Exception {
//        assertThat(((ImageView)menuListFragment.getView().findViewById(R.id.menu_item_image)).getimage )

        assertThat(menuListFragment.recyclerView.getAdapter().getItemCount()).isEqualTo(3);

        assertThat(((TextView)menuListFragment.getView().findViewById(R.id.menu_item_name)).getText()).isEqualTo("Item = 0");
        assertThat(((TextView)menuListFragment.getView().findViewById(R.id.menu_item_price)).getText()).isEqualTo("22,000원");

    }
}