package com.xeed.cheapnsale;

import android.widget.ImageButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StoreDetailActivityTest {

    private StoreDetailActivity storeDetailActivity;

    @Before
    public void setUp() throws Exception {
        storeDetailActivity = Robolectric.buildActivity(StoreDetailActivity.class).create().get();
    }

    @Test
    public void whenStoreDetailActivityIsLoaded_thenBackButtonExist() throws Exception {

    }

}