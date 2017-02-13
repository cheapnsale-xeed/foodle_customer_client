package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.text_preview_sign_up)
    public TextView textPreviewSignUp;

    @BindView(R.id.button_google_signup)
    public Button buttonGoogleSignup;

    @BindView(R.id.button_facebook_signup)
    public Button buttonFacebookSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.text_preview_sign_up)
    public void onClickPreviewButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick({ R.id.button_google_signup, R.id.button_facebook_signup })
    public void onClickSignUpButton(View view) {
        Intent intent = new Intent(SignUpActivity.this, TermsConditionsActivity.class);
        intent.putExtra("account", ((Button) view).getText().toString());
        startActivity(intent);
    }

}
