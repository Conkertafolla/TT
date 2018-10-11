package com.example.conke.tt;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by conke on 10/10/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message =remoteMessage.getNotification().getTitle();
        String body =remoteMessage.getNotification().getBody();
        showNotification(message,body);
    }

    private void showNotification(String message, String body) {
         Intent i =new Intent(this,MainActivity.class);
         i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder (this).setAutoCancel(true)
                .setContentTitle(message)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
}
