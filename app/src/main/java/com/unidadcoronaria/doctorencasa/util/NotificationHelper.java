package com.unidadcoronaria.doctorencasa.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;


/**
 * Created by Agustin.Bala
 */
public class NotificationHelper {


    public static void showNotification(Context context, String callId) {


        String text = "Es tu turno! El doctor te va a llamar en un instante.";
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, callId)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(context.getString(R.string.app_name))
                .setAutoCancel(true).setContentText(text);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        Intent resultIntent;
        resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        builder.setContentIntent(stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        mNotificationManager.notify(Integer.parseInt(callId), notification);
    }

    public static void dismissNotification(Context context, int id) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(id);
    }
}