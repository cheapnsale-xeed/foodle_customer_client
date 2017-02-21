package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.User;
import com.xeed.cheapnsale.user.AWSMobileClient;
import com.xeed.cheapnsale.user.IdentityProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsConditionsActivity extends AppCompatActivity {

    @BindView(R.id.image_back_button_terms)
    ImageView imageBackButtonTerms;

    @BindView(R.id.checkbox_all_agreement_terms)
    CheckBox checkboxAllAgreementTerms;

    @BindView(R.id.checkbox_agreement_terms_1)
    CheckBox checkboxAgreementTerms1;

    @BindView(R.id.checkbox_agreement_terms_2)
    CheckBox checkboxAgreementTerms2;

    @BindView(R.id.checkbox_agreement_terms_3)
    CheckBox checkboxAgreementTerms3;

    @BindView(R.id.checkbox_agreement_terms_4)
    CheckBox checkboxAgreementTerms4;

    @BindView(R.id.text_agreement_terms_1)
    TextView textAgreementTerms1;

    @BindView(R.id.text_agreement_terms_2)
    TextView textAgreementTerms2;

    @BindView(R.id.text_agreement_terms_3)
    TextView textAgreementTerms3;

    @BindView(R.id.text_agreement_terms_4)
    TextView textAgreementTerms4;

    @BindView(R.id.text_sign_up_with_account)
    TextView textSignUpWithAccount;

    @Inject
    public AWSMobileClient awsMobileClient;

    @Inject
    public CheapnsaleService cheapnsaleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getApplication()).getApplicationComponent().inject(this);
        setContentView(R.layout.activity_terms_conditions);

        ButterKnife.bind(this);

        if (getIntent().getExtras() != null && getIntent().getExtras().get("account") != null) {
            if ("Facebook".equals(getIntent().getExtras().get("account").toString())) {
                textSignUpWithAccount.setText(getString(R.string.word_sign_up_with_facebook));
            }else{
                textSignUpWithAccount.setText(getString(R.string.word_sign_up_with_google_account));
            }
        }
    }

    @OnClick(R.id.image_back_button_terms)
    public void onClickBackButton(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TermsConditionsActivity.this, SignUpActivity.class));
        super.onBackPressed();
    }

    @OnClick({ R.id.checkbox_all_agreement_terms,
               R.id.checkbox_agreement_terms_1,
               R.id.checkbox_agreement_terms_2,
               R.id.checkbox_agreement_terms_3,
               R.id.checkbox_agreement_terms_4 })
    public void onClickTerms(CheckBox view) {
        boolean isChecked = view.isChecked();

        boolean isAllChecked = checkboxAllAgreementTerms.isChecked();

        switch (view.getId()) {
            case R.id.checkbox_all_agreement_terms:
                checkboxAgreementTerms1.setChecked(isChecked);
                checkboxAgreementTerms2.setChecked(isChecked);
                checkboxAgreementTerms3.setChecked(isChecked);
                checkboxAgreementTerms4.setChecked(isChecked);

                textSignUpWithAccount.setEnabled(isChecked);
                break;
            case R.id.checkbox_agreement_terms_1:
                if (isAllChecked) {
                    checkboxAllAgreementTerms.setChecked(false);
                    textSignUpWithAccount.setEnabled(false);
                } else if (checkboxAgreementTerms2.isChecked() && checkboxAgreementTerms3.isChecked() && checkboxAgreementTerms4.isChecked()) {
                    checkboxAllAgreementTerms.setChecked(true);
                    textSignUpWithAccount.setEnabled(true);
                }
                break;
            case R.id.checkbox_agreement_terms_2:
                if (isAllChecked) {
                    checkboxAllAgreementTerms.setChecked(false);
                    textSignUpWithAccount.setEnabled(false);
                } else if (checkboxAgreementTerms1.isChecked() && checkboxAgreementTerms3.isChecked() && checkboxAgreementTerms4.isChecked()) {
                    checkboxAllAgreementTerms.setChecked(true);
                    textSignUpWithAccount.setEnabled(true);
                }
                break;
            case R.id.checkbox_agreement_terms_3:
                if (isAllChecked) {
                    checkboxAllAgreementTerms.setChecked(false);
                    textSignUpWithAccount.setEnabled(false);
                } else if (checkboxAgreementTerms1.isChecked() && checkboxAgreementTerms2.isChecked() && checkboxAgreementTerms4.isChecked()) {
                    checkboxAllAgreementTerms.setChecked(true);
                    textSignUpWithAccount.setEnabled(true);
                }
                break;
            case R.id.checkbox_agreement_terms_4:
                if (isAllChecked) {
                    checkboxAllAgreementTerms.setChecked(false);
                    textSignUpWithAccount.setEnabled(false);
                } else if (checkboxAgreementTerms1.isChecked() && checkboxAgreementTerms2.isChecked() && checkboxAgreementTerms3.isChecked()) {
                    checkboxAllAgreementTerms.setChecked(true);
                    textSignUpWithAccount.setEnabled(true);
                }
                break;

        }
    }

    @OnClick( {R.id.text_agreement_terms_1, R.id.text_agreement_terms_2, R.id.text_agreement_terms_3, R.id.text_agreement_terms_4})
    public void onClickTextAgreements(TextView view) {

        Intent intent = new Intent(this, TermsConditionsDetailActivity.class);
        intent.putExtra("termsId", view.getId());
        startActivity(intent);
    }

    @OnClick(R.id.text_sign_up_with_account)
    public void onClickSignUpWithAccount(TextView view){

        IdentityProvider currentIdentityProvider = awsMobileClient.getIdentityManager().getCurrentIdentityProvider();

        final User user = new User();
        user.setUserId(currentIdentityProvider.getUserId());
        user.setProvider(currentIdentityProvider.getDisplayName());
        user.setTacAgree("Y");

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                cheapnsaleService.putUserLoginInfo(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                startActivity(new Intent(TermsConditionsActivity.this, SMSAuthActivity.class));
                finish();
            }
        }.execute();

    }

}
