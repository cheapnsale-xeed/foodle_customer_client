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

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MyOrderFragmentTest {

    MyOrderFragment myOrderFragment;

    @Before
    public void setUp() throws Exception {
        myOrderFragment = new MyOrderFragment();
        SupportFragmentTestUtil.startVisibleFragment(myOrderFragment);
    }

    @Test
    public void whenFragmentIsLoaded_thenShowMyOrderTitle() throws Exception {
        assertThat(((TextView)myOrderFragment.getView().findViewById(R.id.my_order_title)).getText()).isEqualTo("My Order");
    }
}
