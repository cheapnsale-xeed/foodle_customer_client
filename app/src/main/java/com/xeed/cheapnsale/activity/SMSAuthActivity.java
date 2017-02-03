package com.xeed.cheapnsale.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.CheapnsaleService;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SMSAuthActivity extends AppCompatActivity {

    @Inject
    public CheapnsaleService cheapnsaleService;

    @BindView(R.id.button_sms_send_smsauth)
    public Button buttonSmsSendSmsauth;

    @BindView(R.id.button_auth_send_smsauth)
    public Button buttonAuthSendSmsauth;

    @BindView(R.id.edit_phone_number_smsauth)
    public EditText editPhoneNumberSmsauth;

    @BindView(R.id.edit_auth_number_smsauth)
    public EditText editAuthNumberSmsauth;

    private String authId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsauth);

        ((Application) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_sms_send_smsauth)
    public void onClickSmsSendButton(View view) {
        authId = UUID.randomUUID().toString().replaceAll("-","");
        cheapnsaleService.putPrepareSMSAuth(authId);

    }

    @OnClick(R.id.button_auth_send_smsauth)
    public void onClickAuthSendButton(View view) {

    }
}
