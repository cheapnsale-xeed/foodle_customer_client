package com.xeed.cheapnsale.backgroundservice;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.xeed.cheapnsale.Application;


public class PushFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onCreate() {
        super.onCreate();

        String token = FirebaseInstanceId.getInstance().getToken();
        ((Application) getApplication()).setFcmToken(token);
        Log.d(TAG, "Created token: " + token);
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        ((Application) getApplication()).setFcmToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // 갱신된 Token 정보를 사용자 DB 혹은 어딘가에(아직 미정..) 업데이트해주는로직추가 필요함

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

}
