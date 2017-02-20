package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.User;
import com.xeed.cheapnsale.user.AWSMobileClient;
import com.xeed.cheapnsale.user.IdentityManager;
import com.xeed.cheapnsale.user.IdentityProvider;
import com.xeed.cheapnsale.user.signin.SignInManager;
import com.xeed.cheapnsale.user.signin.SignInProvider;
import com.xeed.cheapnsale.wrapper.AppCompatWrapperActivity;

import javax.inject.Inject;

public class SplashActivity extends AppCompatWrapperActivity {

    private static final String LOG_TAG = SplashActivity.class.getSimpleName();

    @Inject
    public SignInManager signInManager;

    @Inject
    public AWSMobileClient awsMobileClient;

    @Inject
    public CheapnsaleService cheapnsaleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getApplication()).getApplicationComponent().inject(this);
        setContentView(R.layout.activity_splash);

        showProgressDialog();
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                final SignInProvider provider = signInManager.getPreviouslySignedInProvider();

                // if the user was already previously in to a provider.
                if (provider != null) {
                    // asynchronously handle refreshing credentials and call our handler.
                    signInManager.refreshCredentialsWithProvider(SplashActivity.this,
                            provider, new SignInResultsHandler());
                } else {
                    Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        thread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogDismiss();
    }

    private class SignInResultsHandler implements IdentityManager.SignInResultsHandler {
        @Override
        public void onSuccess(final IdentityProvider provider) {
            hideProgressDialog();

            Log.d(LOG_TAG, String.format("User sign-in with previous %s provider succeeded",
                    provider.getDisplayName()));

            awsMobileClient
                    .getIdentityManager()
                    .loadUserInfoAndImage(provider, new Runnable() {
                        @Override
                        public void run() {
                            // lambda call
                            Log.d("Splash userId : ", "" + provider.getUserId());

                            final User signUpUser = new User();
                            signUpUser.setUserId(provider.getUserId());
                            signUpUser.setProvider(provider.getDisplayName());

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
                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                        finish();
                                    }else if("Y".equals(user.getTacAgree())){
                                        //TODO 이용동의 후 휴대폰인증으로 가야하나 아직 유저스토리가 나오지 않아 메인으로 이동
                                        //startActivity(new Intent(SplashActivity.this, SMSAuthActivity.class));
                                        //finish();
                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                        finish();
                                    }else{
                                        Intent intent = new Intent(SplashActivity.this, TermsConditionsActivity.class);
                                        intent.putExtra("account", provider.getDisplayName());
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }.execute();
                        }
                    });

            // The sign-in manager is no longer needed once signed in.
            SignInManager.dispose();
        }

        @Override
        public void onCancel(final IdentityProvider provider) {
            hideProgressDialog();

            Log.wtf(LOG_TAG, "Cancel can't happen when handling a previously sign-in user.");
        }

        @Override
        public void onError(final IdentityProvider provider, Exception ex) {

            Log.e(LOG_TAG,
                    String.format("Cognito credentials refresh with %s provider failed. Error: %s",
                            provider.getDisplayName(), ex.getMessage()), ex);

            FirebaseCrash.report(ex);
        }
    }

}
