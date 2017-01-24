package com.foi.air.zlatnarezervacija.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.foi.air.zlatnarezervacija.MainActivity;
import com.foi.air.zlatnarezervacija.R;
import com.foi.air.zlatnarezervacija.notification.PushNotification;
import com.foi.air.zlatnarezervacija.notification.VibrateNotification;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by masrnec on 24.1.2017..
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Context ctx = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        String tip = prefs.getString("opcija", "");
        Log.e("OVO JEEEEEEEEEE =",tip);
        if(tip.equals("1")){
            Log.e("OVO JE =",tip);
            VibrateNotification vibrateNotification = new VibrateNotification(getApplicationContext());
            vibrateNotification.showNotification(tip);
        } else   {
            Log.e("OVO JE =",tip);
            PushNotification pushNotification = new PushNotification(getApplicationContext());
            pushNotification.showNotification(remoteMessage.getData().get("message"));
        }
    }


}
