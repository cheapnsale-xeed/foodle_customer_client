package com.xeed.cheapnsale.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class CommonUtil {

    public static void makePhoneCall(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        try {
            activity.startActivity(Intent.createChooser(intent, ""));
        } catch (Exception e) {
            Log.e("CommonUtil", e.getMessage());
        }
    }
}
