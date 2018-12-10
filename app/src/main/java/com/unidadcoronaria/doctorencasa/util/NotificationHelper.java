package com.unidadcoronaria.doctorencasa.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;


/**
 * Created by Agustin.Bala
 */
public class NotificationHelper {


    public static void showNotification(Context context, String callId, String text) {


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, callId)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(context.getString(R.string.app_name)).setColor(ResourcesCompat.getColor(App.getInstance().getResources(), R.color.colorAccent, null))
                .setAutoCancel(true).setContentText(text);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        Intent resultIntent;
        resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        builder.setContentIntent(stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));


        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = context.getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, "Doctor en casa", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(text);
            channel.setLightColor(ContextCompat.getColor(App.getInstance(), R.color.red));
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
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