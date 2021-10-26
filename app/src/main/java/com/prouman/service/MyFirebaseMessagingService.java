package com.prouman.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.prouman.Util.NotificationUtils;
import com.prouman.model.NotifcationdataObj;
import com.prouman.notification.NotificationDetail;
import com.prouman.test_db.ContactDbHandler;

import org.json.JSONException;
import org.json.JSONObject;

import me.leolin.shortcutbadger.ShortcutBadger;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
//     else   if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification().getBody(),remoteMessage);
//        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message,RemoteMessage remoteMessage) {
//        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();
//        }else{
            // If the app is in background, firebase itself handles the notification
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        //}
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        ContactDbHandler db=new ContactDbHandler(getApplicationContext());
        NotifcationdataObj notifcationdataObj=new NotifcationdataObj();
        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");
            notifcationdataObj.setStr_date(timestamp);
            notifcationdataObj.setStr_description(message);
            notifcationdataObj.setStr_fromuser( data.getString("upro_id"));
            notifcationdataObj.setStr_tittle(title);
            notifcationdataObj.setStr_userimage( data.getString("upro_photo"));
            notifcationdataObj.setStr_username( data.getString("upro_name"));
            notifcationdataObj.setStr_isread("0");
           int id= db.addNotification(notifcationdataObj);
            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("batch",data.getString("badge"));
            editor.commit();

//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", message);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
//            } else {
                // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
//                resultIntent.putExtra("message", message);
            Intent resultIntent=new Intent(getApplicationContext(), NotificationDetail.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //  resultIntent.setData(Uri.parse("shop://theshopcom"));
            resultIntent.putExtra("notification_id",id);
            resultIntent.putExtra("notification_data", notifcationdataObj);
//            context.startActivity(intent);
                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent,data.getString("badge"));
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl,data.getString("badge"));
                }
           // }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent,String badge) {
        notificationUtils = new NotificationUtils(context);
      //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
        ShortcutBadger.removeCount(getApplicationContext());
        boolean success = ShortcutBadger.applyCount(getApplicationContext(), Integer.parseInt(badge));
        if(success) {
           // Toast.makeText(getApplicationContext(), "Set count=" + 8 + ", success=" + success, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl,String badge) {
        notificationUtils = new NotificationUtils(context);
     //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
        ShortcutBadger.removeCount(getApplicationContext());
        boolean success = ShortcutBadger.applyCount(getApplicationContext(), Integer.parseInt(badge));
        if(success) {
          //  Toast.makeText(getApplicationContext(), "Set count=" + badge + ", success=" + success, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSendError(String msgID, Exception exception) {
        Log.e("wouter", "onSendError ", exception );
        super.onSendError(msgID, exception);

    }
}
