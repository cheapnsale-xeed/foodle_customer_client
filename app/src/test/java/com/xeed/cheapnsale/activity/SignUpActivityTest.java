package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.user.IdentityProvider;

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
    public void whenFacebookSignInSuccess_thenTermsActivityIsStarted() throws Exception {

        signUpActivity.signUpResultsHandler.onSuccess(new IdentityProvider() {
            @Override
            public String getDisplayName() {
                return "Facebook";
            }

            @Override
            public String getCognitoLoginKey() {
                return null;
            }

            @Override
            public boolean isUserSignedIn() {
                return false;
            }

            @Override
            public String getToken() {
                return null;
            }

            @Override
            public String refreshToken() {
                return null;
            }

            @Override
            public void signOut() {

            }

            @Override
            public String getUserName() {
                return null;
            }

            @Override
            public String getUserImageUrl() {
                return null;
            }

            @Override
            public void reloadUserInfo() {

            }
        });

        Intent expectedIntent = new Intent(signUpActivity, TermsConditionsActivity.class);
        Intent actualIntent = shadowOf(signUpActivity).getNextStartedActivity();

        assertThat(actualIntent.getStringExtra("account")).isEqualTo("페이스북으로 이용하기");
        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenGoogleSignInSuccess_thenTermsActivityIsStarted() throws Exception {

        signUpActivity.signUpResultsHandler.onSuccess(new IdentityProvider() {
            @Override
            public String getDisplayName() {
                return "Google";
            }

            @Override
            public String getCognitoLoginKey() {
                return null;
            }

            @Override
            public boolean isUserSignedIn() {
                return false;
            }

            @Override
            public String getToken() {
                return null;
            }

            @Override
            public String refreshToken() {
                return null;
            }

            @Override
            public void signOut() {

            }

            @Override
            public String getUserName() {
                return null;
            }

            @Override
            public String getUserImageUrl() {
                return null;
            }

            @Override
            public void reloadUserInfo() {

            }
        });

        Intent expectedIntent = new Intent(signUpActivity, TermsConditionsActivity.class);
        Intent actualIntent = shadowOf(signUpActivity).getNextStartedActivity();

        assertThat(actualIntent.getStringExtra("account")).isEqualTo("구글계정으로 이용하기");
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