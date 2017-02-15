package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        ButterKnife.bind(this);

        if (getIntent().getExtras() != null && getIntent().getExtras().get("account") != null) {
            textSignUpWithAccount.setText(getIntent().getExtras().get("account").toString());
        }
    }

    @OnClick(R.id.image_back_button_terms)
    public void onClickBackButton(View view) {
        onBackPressed();
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
