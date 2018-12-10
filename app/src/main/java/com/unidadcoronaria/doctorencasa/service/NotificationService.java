package com.unidadcoronaria.doctorencasa.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.activity.MainActivity;
import com.unidadcoronaria.doctorencasa.util.NotificationHelper;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import java.util.Date;

/**
 * Created by AGUSTIN.BALA on 11/11/2016.
 */

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "NotificationService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
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
        if (remoteMessage.getData().get("type") != null && remoteMessage.getData().get("type").equals("nextInQueue")) {
            NotificationHelper.showNotification(App.getInstance(), remoteMessage.getData().get("videocallId"), "Sos el siguiente en la fila, estate atento!");
        } else if (remoteMessage.getData().get("token") != null &&
                remoteMessage.getData().get("roomName") != null) {
            SessionUtil.saveRoomName(remoteMessage.getData().get("roomName"));
            SessionUtil.saveTwilioToken(remoteMessage.getData().get("token"));
            App.getInstance().startActivity(MainActivity.getStartIntent(App.getInstance()));
        } else if (remoteMessage.getData().get("videocallId") != null) {
            NotificationHelper.showNotification(App.getInstance(), remoteMessage.getData().get("videocallId"), "Es tu turno! El doctor te va a llamar en un instante.");
        }

    }


}