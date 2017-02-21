package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.model.SMSAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SMSAuthActivityTest {

    private SMSAuthActivity smsAuthActivity;

    @Before
    public void setUp() throws Exception {
        smsAuthActivity = Robolectric.buildActivity(SMSAuthActivity.class).create().get();
    }

    @Test
    public void whenClickBackArrowButton_thenSignUpActivityIsStart() throws Exception {
        smsAuthActivity.onOptionsItemSelected(new RoboMenuItem(android.R.id.home));

        Intent expectedIntent = new Intent(smsAuthActivity, SignUpActivity.class);
        Intent actualIntent = shadowOf(smsAuthActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
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

    @Test
    public void whenSMSSendButtonClicked_thenReturnAuthId() throws Exception {
        EditText editPhoneNumber = (EditText) smsAuthActivity.findViewById(R.id.edit_phone_number_smsauth);
        Button buttonSmsSend= (Button) smsAuthActivity.findViewById(R.id.button_sms_send_smsauth);
        LinearLayout remainTime = (LinearLayout) smsAuthActivity.findViewById(R.id.layout_remain_time_smsauth);

        editPhoneNumber.setText("01012345678");

        when(smsAuthActivity.cheapnsaleService.putPrepareSMSAuth(anyString())).thenReturn("9999");

        buttonSmsSend.performClick();

        assertThat(buttonSmsSend.getText()).isEqualTo("인증번호 재전송");
        assertThat(buttonSmsSend.isEnabled()).isFalse();
        assertThat(remainTime.getVisibility()).isEqualTo(View.VISIBLE);

        assertThat(smsAuthActivity.authId).isEqualTo("9999");

    }

    @Test
    public void whenAuthEditLengthIsEight_thenAuthSendButtonEnabled() throws Exception {
        EditText editAuthNumber = (EditText) smsAuthActivity.findViewById(R.id.edit_auth_number_smsauth);
        Button buttonAuthSend = (Button) smsAuthActivity.findViewById(R.id.button_auth_send_smsauth);

        assertThat(editAuthNumber.getHint()).isEqualTo("인증번호 입력");

        editAuthNumber.setText("12345");
        assertThat(buttonAuthSend.isEnabled()).isFalse();

        editAuthNumber.setText("123456");
        assertThat(buttonAuthSend.isEnabled()).isTrue();

        editAuthNumber.setText("12345");
        assertThat(buttonAuthSend.isEnabled()).isFalse();

    }

    @Test
    public void whenAuthAllow_thenShowDialogAndFinish() throws Exception {
        EditText editAuthNumber = (EditText) smsAuthActivity.findViewById(R.id.edit_auth_number_smsauth);
        Button buttonAuthSend = (Button) smsAuthActivity.findViewById(R.id.button_auth_send_smsauth);

        when(smsAuthActivity.cheapnsaleService.getConfirmSMSAuth(any(SMSAuth.class))).thenReturn("ALLOW");

        editAuthNumber.setText("123456");
        buttonAuthSend.performClick();

        AlertDialog authDialog = (AlertDialog) ShadowDialog.getLatestDialog();
        assertThat(authDialog.isShowing()).isTrue();

        Button confirmButton = (Button) authDialog.findViewById(android.R.id.button1);

        confirmButton.performClick();

        Intent expectedIntent = new Intent(smsAuthActivity, MainActivity.class);
        Intent actualIntent = shadowOf(smsAuthActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();

    }

    @Test
    public void whenAuthDeny_thenShowToastAndClearAuthEdit() throws Exception {
        EditText editAuthNumber = (EditText) smsAuthActivity.findViewById(R.id.edit_auth_number_smsauth);
        Button buttonAuthSend = (Button) smsAuthActivity.findViewById(R.id.button_auth_send_smsauth);

        when(smsAuthActivity.cheapnsaleService.getConfirmSMSAuth(any(SMSAuth.class))).thenReturn("DENY");

        editAuthNumber.setText("123456");
        buttonAuthSend.performClick();

        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("잘못된 인증번호");
        assertThat(editAuthNumber.getText().toString()).isEqualTo("");
    }
}