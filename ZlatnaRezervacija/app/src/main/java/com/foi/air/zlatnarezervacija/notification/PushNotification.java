package com.foi.air.zlatnarezervacija.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.foi.air.zlatnarezervacija.MainActivity;
import com.foi.air.zlatnarezervacija.R;

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

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setAutoCancel(true)
                    .setContentTitle("Obavijest u vezi Vaše rezervacije")
                    .setContentText(message)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0, builder.build());
        }
    }

