package com.xeed.cheapnsale.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsConditionsDetailActivity extends AppCompatActivity {

    @BindView(R.id.text_title_terms_detail)
    public TextView textTitleTermDetail;

    @BindView(R.id.text_terms_detail)
    public TextView textTermsDetail;

    private int termsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_terms_detail);

        ButterKnife.bind(this);

        if(getIntent().getExtras() != null && getIntent().getExtras().get("termsId") != null){
            termsId = (int) getIntent().getExtras().get("termsId");
        }

        switch (termsId) {
            case R.id.text_agreement_terms_1:
                textTitleTermDetail.setText(getResources().getString(R.string.txt_agreement_terms_1));
                textTermsDetail.setText(getResources().getString(R.string.txt_agreement_terms_1));
                break;
            case R.id.text_agreement_terms_2:
                textTitleTermDetail.setText(getResources().getString(R.string.txt_agreement_terms_2));
                textTermsDetail.setText(getResources().getString(R.string.txt_agreement_terms_2));
                break;
            case R.id.text_agreement_terms_3:
                textTitleTermDetail.setText(getResources().getString(R.string.txt_agreement_terms_3));
                textTermsDetail.setText(getResources().getString(R.string.txt_agreement_terms_3));
                break;
            case R.id.text_agreement_terms_4:
                textTitleTermDetail.setText(getResources().getString(R.string.txt_agreement_terms_4));
                textTermsDetail.setText(getResources().getString(R.string.txt_agreement_terms_4));
                break;
        }
    }

    @OnClick(R.id.image_back_button_terms_detail)
    public void onClickBackButton(ImageView view) {
        onBackPressed();
    }
}
