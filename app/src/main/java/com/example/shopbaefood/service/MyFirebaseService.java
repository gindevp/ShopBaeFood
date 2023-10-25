package com.example.shopbaefood.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.shopbaefood.MainActivity;
import com.example.shopbaefood.R;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        Intent intent = new Intent("realtime");
        intent.putExtra("message", remoteMessage.getNotification().getBody());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "My chanel ID";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("My new notification")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public static void subscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed to " + topic;
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        if (e != null) {
                            Log.d(TAG, "Subscription to " + topic + " failed: " + e.getMessage());
                        }
                    } else {
                        Log.d(TAG, msg);
                    }
                });
    }

    public static void unsubscribeFromTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(task -> {
                    String msg = "Unsubscribed from " + topic;
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        if (e != null) {
                            Log.d(TAG, "Unsubscription from " + topic + " failed: " + e.getMessage());
                        }
                    } else {
                        Log.d(TAG, msg);
                    }
                });
    }


    public static void sendMessageToTopic(String topic, String messageTitle, String messageBody) {
        RemoteMessage message = new RemoteMessage.Builder(topic)
                .setMessageId(UUID.randomUUID().toString())
                .addData("title", messageTitle)
                .addData("body", messageBody)
                .build();
        Log.d(TAG, "Send from "+topic);
        FirebaseMessaging.getInstance().send(message);
    }
}

