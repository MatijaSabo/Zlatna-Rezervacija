package com.foi.air.zlatnarezervacija.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.foi.air.zlatnarezervacija.MainActivity;
import com.foi.air.zlatnarezervacija.R;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.StringTokenizer;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by masrnec on 24.1.2017..
 */

public class PushNotification  implements NotificationInterface {
    private Context context;


    public PushNotification(Context context) {
        this.context = context;

    }

    @Override
    public void showNotification(String message) {

            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
          //  PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setAutoCancel(true)
                    .setContentTitle("Zlatna Rezervacija")
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setSmallIcon(R.drawable.zlatna_rezervacija)
                 //   .setContentIntent(pendingIntent)
                    .setSound(alarmSound);



            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);


            int number = (int) ((new Date().getTime()/1000L)% Integer.MAX_VALUE);
            manager.notify(number,builder.build());

    }
    }

