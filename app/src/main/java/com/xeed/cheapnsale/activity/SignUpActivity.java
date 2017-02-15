package com.xeed.cheapnsale.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.user.IdentityManager;
import com.xeed.cheapnsale.user.IdentityProvider;
import com.xeed.cheapnsale.user.signin.FacebookSignInProvider;
import com.xeed.cheapnsale.user.signin.GoogleSignInProvider;
import com.xeed.cheapnsale.user.signin.SignInManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.text_preview_sign_up)
    public TextView textPreviewSignUp;

    @BindView(R.id.button_facebook_signup)
    public Button buttonFacebookSignup;

    public SignInManager signInManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        signInManager = SignInManager.getInstance(this);

        signInManager.setResultsHandler(this, new SignUpResultsHandler());

        // Initialize facebook sign-in buttons.
        signInManager.initializeSignInButton(FacebookSignInProvider.class,
                this.findViewById(R.id.button_facebook_signup));


        // Initialize google sign-in buttons.
        signInManager.initializeSignInButton(GoogleSignInProvider.class,
                this.findViewById(R.id.button_google_signup));
    }

    @OnClick(R.id.text_preview_sign_up)
    public void onClickPreviewButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class SignUpResultsHandler implements IdentityManager.SignInResultsHandler {

        @Override
        public void onSuccess(final IdentityProvider provider) {
            Log.d(LOG_TAG, String.format("User sign-in with %s succeeded",
                    provider.getDisplayName()));

            // The sign-in manager is no longer needed once signed in.
            SignInManager.dispose();

            Intent intent = new Intent(SignUpActivity.this, TermsConditionsActivity.class);

            if("Facebook".equals(provider.getDisplayName())){
                intent.putExtra("account", ((Button) findViewById(R.id.button_facebook_signup)).getText().toString());
            }else if("Google".equals(provider.getDisplayName())){
                intent.putExtra("account", ((Button) findViewById(R.id.button_google_signup)).getText().toString());
            }
            startActivity(intent);
        }

        @Override
        public void onCancel(final IdentityProvider provider) {
            Log.d(LOG_TAG, String.format("User sign-in with %s canceled.",
                    provider.getDisplayName()));
        }

        @Override
        public void onError(final IdentityProvider provider, final Exception ex) {
            Log.e(LOG_TAG, String.format("User Sign-in failed for %s : %s",
                    provider.getDisplayName(), ex.getMessage()), ex);

            final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
            errorDialogBuilder.setTitle("Sign-In Error");
            errorDialogBuilder.setMessage(
                    String.format("Sign-in with %s failed.\n%s", provider.getDisplayName(), ex.getMessage()));
            errorDialogBuilder.setNeutralButton("Ok", null);
            errorDialogBuilder.show();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        signInManager.handleActivityResult(requestCode, resultCode, data);
    }


}
