package com.prouman;

import android.app.Application;

import com.prouman.Util.TypefaceUtil;

/**
 * Created by om on 2/10/2017.
 */

public class NinjaApplication extends Application {

    private final String TAG = "reg";

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Helvetica-normal.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
//        GcmHelper.getInstance()
//                .setAuthorizedEntity("704200797536")
//                .addRegistrationCallback(getApplicationContext(), new CustomRegistrationListener(), true)
//                .addOnMessageReceivedCallback(new CustomMessageReceivedListener())
//                .init(this);
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//    String cd=pref.getString("regId", "s");
//    if (pref.getString("regId", "s").equals("s")) {
//        try {
//
//            RxNotificationUtil.verifyGooglePlayService(this);
//            RxNotification.getToken("704200797536")
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Observer<String>() {
////                            @Override
////                            public void onCompleted() {
////                                Log.d(TAG, "onCompleted");
////
////                            }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.d(TAG, "Register - onError - " + e.getMessage());
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(String token) {
//                            Log.d(TAG, token);
//                            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//                            SharedPreferences.Editor editor = pref.edit();
//                            editor.putString("regId", token);
//                            editor.commit();
//                        }
//                    });
//        } catch (DeviceUnsupportedException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        } catch (GooglePlayServicesOutDatedException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        } catch (GooglePlayServicesNotInstalledException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        } catch (UnknownErrorException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
}
}
