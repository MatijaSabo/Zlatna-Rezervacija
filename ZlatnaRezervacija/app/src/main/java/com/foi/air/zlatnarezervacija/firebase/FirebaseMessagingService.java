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
import com.foi.air.zlatnarezervacija.notification.NotificationInterface;
import com.foi.air.zlatnarezervacija.notification.PushNotification;
import com.foi.air.zlatnarezervacija.notification.VibrateNotification;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by masrnec on 24.1.2017..
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationInterface notificationInterface;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String tip = prefs.getString("opcija", "");
        /*  Odabir koji se modul/nacin obavjestavanja poziva */
        if(tip.equals("1")){
                notificationInterface = new VibrateNotification(getApplicationContext());
        } else   {
                notificationInterface = new PushNotification(getApplicationContext());
        }
        notificationInterface.showNotification(remoteMessage.getData().get("message"));
    }
}
