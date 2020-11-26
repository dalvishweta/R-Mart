package com.rmart.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import com.rmart.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyNotificationManager {

    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;

    private Context mCtx;

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void notificationDialog(String roleID, String title, String message, String imageURL, String orderID, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mCtx, roleID);
        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel(roleID, roleID, NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("R-Mart description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("R-Mart")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(message)
                //.setVibrate(new long[]{0L})
                .setContentIntent(resultPendingIntent)
                .setContentInfo("Information");

        if (!TextUtils.isEmpty(imageURL) || !imageURL.equalsIgnoreCase("null")) {
            Bitmap bitmap = getBitmapFromURL(imageURL);
            if (bitmap != null) {
            /*NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            bigPictureStyle.setBigContentTitle(title);
            bigPictureStyle.setSummaryText(message);
            bigPictureStyle.bigPicture(bitmap);
            notificationBuilder.setStyle(bigPictureStyle);*/
                notificationBuilder.setLargeIcon(bitmap);
            }
        }

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(title);
        bigTextStyle.bigText(message);
        notificationBuilder.setStyle(bigTextStyle);

        int id = Integer.parseInt(orderID);
        notificationManager.notify(id, notificationBuilder.build());
    }

    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}