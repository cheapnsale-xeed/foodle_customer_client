package com.xeed.cheapnsale.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.SMSAuth;
import com.xeed.cheapnsale.user.AWSMobileClient;
import com.xeed.cheapnsale.user.IdentityProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

public class SMSAuthActivity extends AppCompatActivity {

    private final long COUNTDOWN_START_TIME = 180000;
    private final long COUNTDOWN_INTERVAL = 450;

    @Inject
    public CheapnsaleService cheapnsaleService;

    @Inject
    public AWSMobileClient awsMobileClient;

    @BindView(R.id.button_sms_send_smsauth)
    public Button buttonSmsSendSmsauth;

    @BindView(R.id.button_auth_send_smsauth)
    public Button buttonAuthSendSmsauth;

    @BindView(R.id.edit_phone_number_smsauth)
    public EditText editPhoneNumberSmsauth;

    @BindView(R.id.edit_auth_number_smsauth)
    public EditText editAuthNumberSmsauth;

    @BindView(R.id.layout_remain_time_smsauth)
    public LinearLayout layoutRemainTimeSmsauth;

    @BindView(R.id.text_remain_time_smsauth)
    public CountdownView textRemainTimeSmsauth;

    public String authId;
    private String smsMessage;
    private Toolbar toolbar;
    private SMSAuth smsAuth;
    private String authResult;
    private IdentityProvider identityProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsauth);

        ((Application) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        setTitle(getResources().getString(R.string.txt_sms_auth));

        toolbar = (Toolbar) findViewById(R.id.tool_bar_smsauth);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.ico_back));

        smsAuth = new SMSAuth();
        editPhoneNumberSmsauth.addTextChangedListener(phoneChangedListener);
        editAuthNumberSmsauth.addTextChangedListener(authChangedListener);

        textRemainTimeSmsauth.setOnCountdownEndListener(countdownEndListener);

        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);

        identityProvider = awsMobileClient.getIdentityManager().getCurrentIdentityProvider();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SMSAuthActivity.this, SignUpActivity.class));
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }

    @OnClick(R.id.button_sms_send_smsauth)
    public void onClickSmsSendButton(View view) {

        if (!buttonSmsSendSmsauth.isEnabled()) return;

        final String phoneNumber = editPhoneNumberSmsauth.getText().toString();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                buttonSmsSendSmsauth.setText(R.string.txt_sms_resend);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                authId = cheapnsaleService.putPrepareSMSAuth(phoneNumber);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                smsAuth.setAUTH_ID(authId);
                smsAuth.setPHONE_NUMBER(phoneNumber);
                smsAuth.setUSER_ID(identityProvider.getUserId());

                buttonSmsSendSmsauth.setEnabled(false);
                editAuthNumberSmsauth.setEnabled(true);
                textRemainTimeSmsauth.start(COUNTDOWN_START_TIME);
                layoutRemainTimeSmsauth.setVisibility(View.VISIBLE);
                textRemainTimeSmsauth.setOnCountdownIntervalListener(COUNTDOWN_INTERVAL, countdownIntervalListener);

            }
        }.execute();
    }

    @OnClick(R.id.button_auth_send_smsauth)
    public void onClickAuthSendButton(View view) {

        if (!buttonAuthSendSmsauth.isEnabled()) return;

        smsAuth.setAUTH_NUMBER(Integer.parseInt(editAuthNumberSmsauth.getText().toString()));

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                authResult = cheapnsaleService.getConfirmSMSAuth(smsAuth);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if ("ALLOW".equals(authResult)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SMSAuthActivity.this);
                    builder.setMessage(R.string.word_auth_confirm);
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(SMSAuthActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.show();

                } else {
                    editAuthNumberSmsauth.setText("");
                    Toast.makeText(getApplicationContext(), R.string.txt_wrong_auth_number, Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();
    }

    private TextWatcher phoneChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 10) {
                buttonSmsSendSmsauth.setEnabled(true);
            } else {
                buttonSmsSendSmsauth.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher authChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 6) {
                buttonAuthSendSmsauth.setEnabled(true);
            } else {
                buttonAuthSendSmsauth.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                Bundle bundle = intent.getExtras();

                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");

                    SmsMessage[] msgs = new SmsMessage[pdus.length];

                    for (int i = 0; i < msgs.length; i ++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }

                    smsMessage = msgs[0].getMessageBody().toString();
                    if (smsMessage != null) {
                        setAuthEditFill(smsMessage);
                    }
                }
            }
        }
    };

    private CountdownView.OnCountdownIntervalListener countdownIntervalListener = new CountdownView.OnCountdownIntervalListener() {
        @Override
        public void onInterval(CountdownView cv, long remainTime) {
            if (remainTime <= 171000) {
                buttonSmsSendSmsauth.setEnabled(true);
                textRemainTimeSmsauth.setOnCountdownIntervalListener(0, null);
            }
            Log.d("auth count interval: ", "" + remainTime);
        }
    };

    private CountdownView.OnCountdownEndListener countdownEndListener = new CountdownView.OnCountdownEndListener() {
        @Override
        public void onEnd(CountdownView cv) {
            layoutRemainTimeSmsauth.setVisibility(View.GONE);
            editAuthNumberSmsauth.setText("");
            editAuthNumberSmsauth.setEnabled(false);
        }
    };

    private void setAuthEditFill(String smsMessage) {
        if (smsMessage.contains("푸들")){
            int endPos = smsMessage.indexOf("을");
            int startPos = endPos - 6;
            editAuthNumberSmsauth.setText(smsMessage.substring(startPos, endPos));
        }
    }
}
