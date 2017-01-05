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
    public void whenActivityIsStarted_thenShowHelloWordText() throws Exception {
        TextView helloWorldText = (TextView) mainActivity.findViewById(R.id.txt_hello_world);
        assertThat(helloWorldText.getText()).isEqualTo("Hello World!");
    }
}