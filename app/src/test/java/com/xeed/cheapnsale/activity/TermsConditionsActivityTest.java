package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TermsConditionsActivityTest {

    private TermsConditionsActivity termsConditionsActivity;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void whenActivityIsStarted_thenSignUpButtonTextIsSameAsSelectedMethod() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, TermsConditionsActivity.class);
        intent.putExtra("account", "구글계정으로 이용하기");

        termsConditionsActivity = Robolectric.buildActivity(TermsConditionsActivity.class).withIntent(intent).create().get();

        TextView textSignupButton = (TextView) termsConditionsActivity.findViewById(R.id.text_sign_up_with_account);

        assertThat(textSignupButton.getText().toString()).isEqualTo("구글계정으로 이용하기");
    }

}