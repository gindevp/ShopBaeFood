package com.example.shopbaefood.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.shopbaefood.MainActivity;
import com.example.shopbaefood.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
        Intent intent = new Intent("realtime");
        intent.putExtra("message", remoteMessage.getData().get("body"));
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
        try {
            OkHttpClient client = new OkHttpClient();
            String url = "https://fcm.googleapis.com/fcm/send";
            MediaType JSON = MediaType.get("application/json; charset=utf-8");

            JSONObject jsonObject = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("title", messageTitle);
            data.put("body", messageBody);
            jsonObject.put("to", "/topics/" + topic);
            jsonObject.put("data", data);

            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Authorization", "Bearer AAAAOPtmZD0:APA91bF2oC2BRvePPU6uQBB8_dChLyqOCpyHHCWDx2BKAZSDZWM9PHXUtFOwRlGGGpmL9EdB7edMksjsW075or0_Rhet5kvtpQ2ovFDTrkeSMmmO4NhLlwuqLDn8vdNu53cpK3GElZ0q")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    // Xử lý khi có lỗi
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    // Xử lý kết quả trả về
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

