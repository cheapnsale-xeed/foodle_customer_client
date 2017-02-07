package com.xeed.cheapnsale.activity;

import android.widget.Button;
import android.widget.EditText;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;

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

    @Test
    public void whenPhoneNumberLengthIsOverTen_thenSMSAuthSendButtonEnabled() throws Exception {
        EditText editPhoneNumber = (EditText) smsAuthActivity.findViewById(R.id.edit_phone_number_smsauth);
        Button buttonSmsSend= (Button) smsAuthActivity.findViewById(R.id.button_sms_send_smsauth);

        assertThat(editPhoneNumber.getHint()).isEqualTo("휴대폰 번호");

        editPhoneNumber.setText("123456789");
        assertThat(buttonSmsSend.isEnabled()).isFalse();

        editPhoneNumber.setText("1234567890");
        assertThat(buttonSmsSend.isEnabled()).isTrue();

        editPhoneNumber.setText("123456789");
        assertThat(buttonSmsSend.isEnabled()).isFalse();
    }
}