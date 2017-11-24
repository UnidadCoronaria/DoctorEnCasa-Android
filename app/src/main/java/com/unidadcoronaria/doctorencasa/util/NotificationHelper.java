package com.unidadcoronaria.doctorencasa.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.activity.NewCallActivity;


/**
 * Created by Agustin.Bala
 */
public class NotificationHelper {


    public static void showNotification(Context context, String callId) {


        String text = "Estamos listos para atenderte. Hace click para iniciar la llamada.";
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, callId)
                .setSmallIcon(R.drawable.ic_video_call)
                .setContentTitle(context.getString(R.string.app_name))
                .setAutoCancel(true).setContentText(text);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        Intent resultIntent;
        resultIntent = new Intent(context, NewCallActivity.class);
        resultIntent.putExtra(NewCallActivity.CALL_DESTINATION_KEY, callId);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.setType(callId);
        stackBuilder.addParentStack(NewCallActivity.class);
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