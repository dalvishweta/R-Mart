package com.rmart.fcm;


import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rmart.authentication.views.AuthenticationActivity;
import com.rmart.customer_order.views.CustomerOrdersActivity;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String USER_ID = "user_id";
    public static final String ORDER_ID = "OrderId";
    public static final String ROLE_ID = "role_id";
    public static final String MOBILE_NO = "mobile_no";

    @Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);
        LoggerInfo.printLog("newToken", s);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            LoggerInfo.printLog("Data Payload: ", remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                LoggerInfo.errorLog("Exception: ", e.getMessage());
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
        LoggerInfo.printLog("Notification JSON ", json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");
            String roleID = "", title = "", message = "", imageUrl = "", userID = "", orderID = "", mobileNO ="";
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
                roleID = data.getString("role_id");
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
                mobileNO = data.getString(MOBILE_NO);
            } catch (Exception e) {

            }
            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent;
            if (roleID.equalsIgnoreCase(Utils.CUSTOMER_ID)) {
                intent = new Intent(getApplicationContext(), CustomerOrdersActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra(USER_ID, userID);
            intent.putExtra(ORDER_ID, orderID);
            intent.putExtra(ROLE_ID, roleID);
            intent.putExtra(MOBILE_NO, mobileNO);

            mNotificationManager.notificationDialog(roleID, title, message, imageUrl, orderID, intent);
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
            LoggerInfo.errorLog("Json Exception: ", e.getMessage());
        } catch (Exception e) {
            LoggerInfo.errorLog("Exception: ", e.getMessage());
        }
    }
}
