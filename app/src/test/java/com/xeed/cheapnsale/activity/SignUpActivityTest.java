package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SignUpActivityTest {

    private SignUpActivity signUpActivity;

    @Before
    public void setUp() throws Exception {
        signUpActivity = Robolectric.buildActivity(SignUpActivity.class).create().get();
    }

    @Test
    public void whenClickGoogleSignupButtons_thenTermsActivityIsStarted() throws Exception {
        Button googleSignupButton = (Button) signUpActivity.findViewById(R.id.button_google_signup);
        googleSignupButton.performClick();

        Intent expectedIntent = new Intent(signUpActivity, TermsConditionsActivity.class);
        Intent actualIntent = shadowOf(signUpActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenClickFacebookSignupButtons_thenTermsActivityIsStarted() throws Exception {
        Button facebookSignupButton = (Button) signUpActivity.findViewById(R.id.button_facebook_signup);
        facebookSignupButton.performClick();

        Intent expectedIntent = new Intent(signUpActivity, TermsConditionsActivity.class);
        Intent actualIntent = shadowOf(signUpActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenClickPreviewText_thenMainActivityIsStarted() throws Exception {
        TextView previewText = (TextView) signUpActivity.findViewById(R.id.text_preview_sign_up);
        previewText.performClick();

        Intent expectedIntent = new Intent(signUpActivity, MainActivity.class);
        Intent actualIntent = shadowOf(signUpActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }
}