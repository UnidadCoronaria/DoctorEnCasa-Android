package com.unidadcoronaria.doctorencasa.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.util.NotificationHelper;

/**
 * Created by AGUSTIN.BALA on 11/11/2016.
 */

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "NotificationService";
    private RemoteMessage remoteMessage;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        this.remoteMessage = remoteMessage;
        if(remoteMessage.getData().get("videocallId") != null) {
            NotificationHelper.showNotification(App.getInstance(), remoteMessage.getData().get("videocallId").toString());
        }
    }
}
