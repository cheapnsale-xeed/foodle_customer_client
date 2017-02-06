package com.xeed.cheapnsale.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

    @BindView(R.id.text_sms_send_smsauth)
    public TextView textSmsSendSmsauth;

    @BindView(R.id.text_auth_send_smsauth)
    public TextView textAuthSendSmsauth;

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

        editPhoneNumberSmsauth.addTextChangedListener(phoneChangedListener);
    }

    private TextWatcher phoneChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 10) {
                Log.d("AUTH : ", ""+s.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @OnClick(R.id.text_sms_send_smsauth)
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

    @OnClick(R.id.text_auth_send_smsauth)
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
