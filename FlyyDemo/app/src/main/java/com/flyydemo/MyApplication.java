package com.flyydemo;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import theflyy.com.flyy.Flyy;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Flyy.init(getApplicationContext(), "03ed949d8fb79b2becb0", Flyy.PRODUCTION);
        FirebaseApp.initializeApp(getApplicationContext());
    }
}
