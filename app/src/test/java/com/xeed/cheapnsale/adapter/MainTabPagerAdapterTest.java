package com.xeed.cheapnsale.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.MainActivity;
import com.xeed.cheapnsale.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainTabPagerAdapterTest {

    private  MainTabPagerAdapter mainTabPagerAdapter;
    private FragmentManager supportFragmentManager;

    @Before
    public void setUp() throws Exception {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        supportFragmentManager = mainActivity.getSupportFragmentManager();
        mainTabPagerAdapter = new MainTabPagerAdapter(supportFragmentManager, 2);
    }

    @Test
    public void getCount_returnsFragmentsNumberOfPages() throws Exception {
        assertThat(mainTabPagerAdapter.getCount()).isEqualTo(2);
    }

    @Test
    public void getItem_returnsMainFragmentWithIndex() throws Exception {
        Fragment fragment = mainTabPagerAdapter.getItem(0);
        startFragment(fragment);

        //assertThat(fragment.getView().findViewById(R.id.store_list_title)).isNotNull();
        assertThat(fragment.getView().findViewById(R.id.my_order_title)).isNull();
    }


}