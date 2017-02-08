package com.xeed.cheapnsale.backgroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.MainActivity;

import java.util.Map;

public class PushFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendPushNotification(remoteMessage);
    }

    private void sendPushNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Resources res = getResources();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(res.getString(R.string.notification), data.get(res.getString(R.string.notification)));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setTicker(notification.getTitle())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, builder.build());
    }
}
