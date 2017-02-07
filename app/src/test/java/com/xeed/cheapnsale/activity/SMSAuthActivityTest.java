package com.xeed.cheapnsale.activity;

import com.xeed.cheapnsale.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SMSAuthActivityTest {

    private SMSAuthActivity smsAuthActivity;

    @Before
    public void setUp() throws Exception {
        smsAuthActivity = Robolectric.buildActivity(SMSAuthActivity.class).create().get();
    }

    @Test
    public void whenClickBackArrowButton_thenFinishActivity() throws Exception {
        smsAuthActivity.onOptionsItemSelected(new RoboMenuItem(android.R.id.home));
        assertThat(smsAuthActivity.isFinishing()).isTrue();

    }
}