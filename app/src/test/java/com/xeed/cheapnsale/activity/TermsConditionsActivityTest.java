package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.user.IdentityManager;
import com.xeed.cheapnsale.user.IdentityProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TermsConditionsActivityTest {

    private TermsConditionsActivity termsConditionsActivity;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, TermsConditionsActivity.class);
        intent.putExtra("account", "Google");

        termsConditionsActivity = Robolectric.buildActivity(TermsConditionsActivity.class).withIntent(intent).create().get();
    }

    @Test
    public void whenActivityIsStarted_thenSignUpButtonTextIsSameAsSelectedMethod() throws Exception {
        TextView textSignupButton = (TextView) termsConditionsActivity.findViewById(R.id.text_sign_up_with_account);

        assertThat(textSignupButton.getText().toString()).isEqualTo("구글계정으로 이용하기");
    }

    @Test
    public void whenClickBackPressButton_thenSignUpActivityIsStart() throws Exception {
        termsConditionsActivity.findViewById(R.id.image_back_button_terms).performClick();

        Intent expectedIntent = new Intent(termsConditionsActivity, SignUpActivity.class);
        Intent actualIntent = shadowOf(termsConditionsActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenClickSignUpButton_thenSMSActivityIsStart() throws Exception {
        IdentityManager mockIdentityManager = mock(IdentityManager.class);
        when(termsConditionsActivity.awsMobileClient.getIdentityManager()).thenReturn(mockIdentityManager);
        when(mockIdentityManager.getCurrentIdentityProvider()).thenReturn(mock(IdentityProvider.class));

        termsConditionsActivity.findViewById(R.id.checkbox_all_agreement_terms).performClick();
        termsConditionsActivity.findViewById(R.id.text_sign_up_with_account).performClick();

        Intent expectedIntent = new Intent(termsConditionsActivity, SMSAuthActivity.class);
        Intent actualIntent = shadowOf(termsConditionsActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }
}