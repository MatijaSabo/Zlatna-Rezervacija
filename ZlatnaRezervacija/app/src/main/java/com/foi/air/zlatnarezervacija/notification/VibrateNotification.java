package com.foi.air.zlatnarezervacija.notification;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by masrnec on 24.1.2017..
 */

public class VibrateNotification implements NotificationInterface {
    private Context context;

    public VibrateNotification(Context context) {
        this.context = context;
    }

    @Override
    public void showNotification(String message) {
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
}
