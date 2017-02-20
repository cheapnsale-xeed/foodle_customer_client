package com.xeed.cheapnsale.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.User;
import com.xeed.cheapnsale.user.IdentityManager;
import com.xeed.cheapnsale.user.IdentityProvider;
import com.xeed.cheapnsale.user.signin.FacebookSignInProvider;
import com.xeed.cheapnsale.user.signin.GoogleSignInProvider;
import com.xeed.cheapnsale.user.signin.SignInManager;
import com.xeed.cheapnsale.wrapper.AppCompatWrapperActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatWrapperActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.text_preview_sign_up)
    public TextView textPreviewSignUp;

    @BindView(R.id.button_facebook_signup)
    public Button buttonFacebookSignup;

    @Inject
    public SignInManager signInManager;

    @Inject
    public CheapnsaleService cheapnsaleService;

    public SignUpResultsHandler signUpResultsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getApplication()).getApplicationComponent().inject(this);

        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        signUpResultsHandler = new SignUpResultsHandler();

        signInManager.setResultsHandler(this, signUpResultsHandler);

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
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogDismiss();
    }

    public class SignUpResultsHandler implements IdentityManager.SignInResultsHandler {

        @Override
        public void onSuccess(final IdentityProvider provider) {
            Log.d(LOG_TAG, String.format("User sign-in with %s succeeded",
                    provider.getDisplayName()));

            // The sign-in manager is no longer needed once signed in.
            SignInManager.dispose();

            final Intent intent = new Intent(SignUpActivity.this, TermsConditionsActivity.class);
            final User signUpUser = new User();

            if ("Facebook".equals(provider.getDisplayName())) {
                intent.putExtra("account", "Facebook");
                signUpUser.setProvider("Facebook");
            } else if ("Google".equals(provider.getDisplayName())) {
                intent.putExtra("account", "Google");
                signUpUser.setProvider("Google");
            }

            signUpUser.setUserId(provider.getUserId());

            new AsyncTask<Void, Void, Void>() {

                User user = new User();

                @Override
                protected Void doInBackground(Void... params) {
                    user = cheapnsaleService.putUserLoginInfo(signUpUser);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    Log.d("User : ", user.toString());
                    if("Y".equals(user.getPhoneConfirm())){
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }else if("Y".equals(user.getTacAgree())){
                        //TODO 이용동의 후 휴대폰인증으로 가야하나 아직 유저스토리가 나오지 않아 메인으로 이동
                        //startActivity(new Intent(SignUpActivity.this, SMSAuthActivity.class));
                        //finish();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }else{
                        startActivity(intent);
                        finish();
                    }
                }
            }.execute();
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

            FirebaseCrash.report(ex);

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
        showProgressDialog();
        signInManager.handleActivityResult(requestCode, resultCode, data);
    }


}
