package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.SMSAuth;

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

    private String authID;
    private int authNumber;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsauth);

        ((Application) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_sms_send_smsauth)
    public void onClickSmsSendButton(View view) {
        authID = UUID.randomUUID().toString().replaceAll("-","");

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                authNumber = cheapnsaleService.putPrepareSMSAuth(authID);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();


    }

    @OnClick(R.id.button_auth_send_smsauth)
    public void onClickAuthSendButton(View view) {
        final SMSAuth smsAuth = new SMSAuth();
        smsAuth.setAUTH_ID("1234");
        smsAuth.setAUTH_NUMBER(7150);
        smsAuth.setPHONE_NUMBER("01050985674");

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                // parameter : authID, authNumber
//                result = cheapnsaleService.putPrepareSMSAuth(authID);
                result = cheapnsaleService.getConfirmSMSAuth(smsAuth);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if ("ALLOW".equals(result)) {
                    Toast.makeText(getApplicationContext(), "ALLOW", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SMSAuthActivity.this, MainActivity.class);
//                    startActivity(intent);
                }

            }
        }.execute();
    }
}
