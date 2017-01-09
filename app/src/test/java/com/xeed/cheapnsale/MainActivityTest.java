package com.xeed.cheapnsale;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void whenActivityIsStarted_thenShowCheapnsaleTitle() throws Exception {
        TextView cheapnsaleTitleText = (TextView) mainActivity.findViewById(R.id.mainToolBarTitle);
        assertThat(cheapnsaleTitleText.getText()).isEqualTo("싸다싸");
    }
}