package com.rmart.fcm;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rmart.baseclass.views.SplashScreen;
import com.rmart.orders.views.OrdersActivity;
import com.rmart.utilits.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");
            String rollID = "", title = "", message = "", imageUrl = "", userID = "", orderID = "", mobileNO ="";
            //parsing json data
            try {
                title = data.getString("title");
            } catch (Exception e) {

            }
            try {
                message = data.getString("message");
            } catch (Exception e) {

            }
            try {
                rollID = data.getString("role_id");
            } catch (Exception e) {

            }
            try {
                userID = data.getString("user_id");
            } catch (Exception e) {

            }
            try {
                imageUrl = data.getString("image");
            } catch (Exception e) {

            }
            try {
                orderID = data.getString("order_id");
            } catch (Exception e) {

            }

            try {
                mobileNO = data.getString("mobile_no");
            } catch (Exception e) {

            }
            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent;
            if (rollID.equalsIgnoreCase(Utils.CUSTOMER_ID)) {
                intent = new Intent(getApplicationContext(), SplashScreen.class);
            } else {
                intent = new Intent(getApplicationContext(), SplashScreen.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra("user_id", userID);
            intent.putExtra("order_id", orderID);
            intent.putExtra("role_id", rollID);
            intent.putExtra("mobile_no", mobileNO);

            mNotificationManager.notificationDialog(rollID, title, message, imageUrl, orderID, intent);
            //if there is no image
            /*if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }*/
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}
