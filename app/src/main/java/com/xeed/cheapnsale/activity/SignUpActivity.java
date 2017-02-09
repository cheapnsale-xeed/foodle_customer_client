package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
    public Button googleSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        String preview = "우선 둘러볼께요";
        SpannableString content = new SpannableString(preview);
        content.setSpan(new UnderlineSpan(), 0, preview.length(), 0);
        textPreviewSignUp.setText(content);
    }

    @OnClick(R.id.text_preview_sign_up)
    public void onClickPreviewButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_google_signup)
    public void onClickGoogleSignUpButton(View view) {
        Intent intent = new Intent(SignUpActivity.this, SMSAuthActivity.class);
        startActivity(intent);
    }


}
