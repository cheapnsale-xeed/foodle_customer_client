package com.xeed.cheapnsale.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

    private boolean isGetMyLocation = false;
    private boolean isGetProvider = false;

    public LocationManager mLocationManager;

    private Intent intent;

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
                    intent = new Intent(SplashActivity.this, SignUpActivity.class);
                    isGetProvider = true;
                    checkProviderAndMyLocationThenStartNextActivity();
                }
            }
        });
        thread.start();

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogDismiss();
    }
    private class SignInResultsHandler implements IdentityManager.SignInResultsHandler {

        @Override
        public void onSuccess(final IdentityProvider provider) {

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
                                        intent = new Intent(SplashActivity.this, MainActivity.class);
                                    }else if("Y".equals(user.getTacAgree())){
                                        intent = new Intent(SplashActivity.this, SMSAuthActivity.class);
                                    }else{
                                        intent = new Intent(SplashActivity.this, TermsConditionsActivity.class);
                                        intent.putExtra("account", provider.getDisplayName());
                                    }
                                    isGetProvider = true;
                                    checkProviderAndMyLocationThenStartNextActivity();
                                    finish();
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
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("Splash, longitude", ""+location.getLongitude()); // 경도
            Log.d("Splash, Latitude", ""+location.getLatitude()); // 위도

            if (getApplication() != null) {
                ((Application) getApplication()).setMyLocation(location);
                mLocationManager.removeUpdates(mLocationListener);

                isGetMyLocation = true;
                checkProviderAndMyLocationThenStartNextActivity();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void checkProviderAndMyLocationThenStartNextActivity() {
        if (isGetProvider && isGetMyLocation) {
            hideProgressDialog();
            startActivity(intent);
            finish();
        }
    }

}
