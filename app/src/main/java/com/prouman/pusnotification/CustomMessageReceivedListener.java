package com.prouman.pusnotification;

/**
 * Created by om on 2/10/2017.
 */

public class CustomMessageReceivedListener {}//implements GcmHelper.GcmMessageListener  {
//    @Override
//    public void onMessageReceived(Context context, String from, Bundle data) {
//      //  Toast.makeText(context,from,Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(context, GHomeActivity.class);
//// use System.currentTimeMillis() to have a unique ID for the pending intent
//        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
//
//// build notification
//// the addAction re-use the same intent to keep the example short
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!")
//                        .setContentIntent(pIntent)
//                        .setAutoCancel(true);
////        Notification n  = new Notification.Builder(context)
////                .setContentTitle("New mail from " + "test@gmail.com")
////                .setContentText("Subject")
////                .setSmallIcon(R.mipmap.ic_launcher)
////                .setContentIntent(pIntent)
////                .setAutoCancel(true);
//        int mNotificationId = 001;
//// Gets an instance of the NotificationManager service
//        NotificationManager mNotifyMgr =
//                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//// Builds the notification and issues it.
//        mNotifyMgr.notify(mNotificationId, mBuilder.build());
//    }
//
//    @Override
//    public boolean canHandleMessage(Bundle data) {
//        return false;
//    }
//}
