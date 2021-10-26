package com.prouman.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.prouman.HomeActivity;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.NotificationUtils;
import com.prouman.Util.SessionManager;
import com.prouman.model.NotifcationdataObj;
import com.prouman.notification.NotificationDetail;
import com.prouman.test_db.ContactDbHandler;

import org.json.JSONException;
import org.json.JSONObject;


public class SplashActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;
    SessionManager manager;
    ImageView logoIv;
    private NotificationUtils notificationUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager=new SessionManager(getApplicationContext());
        if(manager.getString(AppConstant.STRINGLANGUAGE,"").equals("")) {
            AppConstant.setLanguageForApp("it",SplashActivity.this);
            manager.put(AppConstant.STRINGLANGUAGE,"it");
        }
        else
        {
           AppConstant.setLanguageForApp(manager.getString(AppConstant.STRINGLANGUAGE,""),SplashActivity.this);
        }
        setContentView(R.layout.splash_activity);
        displaySplashScreen();





    }

    private void displaySplashScreen() {
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        if (null != getIntent() && null != getIntent().getExtras() && null != getIntent().getExtras().keySet() && getIntent().getExtras().keySet().size()>0) {

                            String datakey = "";
                            for (String key : getIntent().getExtras().keySet()) {
                                String value = getIntent().getExtras().getString(key);
                                if (key.equalsIgnoreCase("data")) {
                                    datakey = getIntent().getExtras().getString(key);
                                }
                                Log.d("ssss", "Key: " + key + " Value: " + value);
                            }
                            if (datakey.length() > 0) {
                                Log.e("dd", "Data Payload: " + datakey);

                                try {
                                    JSONObject json = new JSONObject(datakey);
                                    handleDataMessage(json);
                                } catch (Exception e) {
                                    Log.e("dd", "Exception: " + e.getMessage());
                                    Intent ii = new Intent(SplashActivity.this, LoginSelectionActivity.class);
                                    finish();
                                    startActivity(ii);

                                }

                            }
                            else{
                                Intent ii = new Intent(SplashActivity.this, LoginSelectionActivity.class);
                                finish();
                                startActivity(ii);
                            }
                        } else {
                            Intent i = new Intent(SplashActivity.this, LoginSelectionActivity.class);
                            finish();
                            startActivity(i);


                        }
                    }



                });
                }catch(InterruptedException e){
                    e.printStackTrace();
                    Intent i = new Intent(SplashActivity.this, LoginSelectionActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        };
        timerThread.start();
    }
    private void handleDataMessage(JSONObject json) {
        //Log.e(TAG, "push json: " + json.toString());
        ContactDbHandler db=new ContactDbHandler(getApplicationContext());
        NotifcationdataObj notifcationdataObj=new NotifcationdataObj();
        try {
            String title = json.getString("title");
            String message = json.getString("message");
            boolean isBackground = json.getBoolean("is_background");
            String imageUrl = json.getString("image");
            String timestamp = json.getString("timestamp");
            JSONObject payload = json.getJSONObject("payload");
            notifcationdataObj.setStr_date(timestamp);
            notifcationdataObj.setStr_description(message);
            notifcationdataObj.setStr_fromuser( json.getString("upro_id"));
            notifcationdataObj.setStr_tittle(title);
            notifcationdataObj.setStr_userimage( json.getString("upro_photo"));
            notifcationdataObj.setStr_username( json.getString("upro_name"));
            notifcationdataObj.setStr_isread("0");
            int id= db.addNotification(notifcationdataObj);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("batch",json.getString("badge"));
            editor.commit();

            Intent resultIntent=new Intent(getApplicationContext(), NotificationDetail.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            resultIntent.putExtra("notification_id",id);
            resultIntent.putExtra("notification_data", notifcationdataObj);
            finish();
            startActivity(resultIntent);
        } catch (JSONException e) {
            Log.e("dsd", "Json Exception: " + e.getMessage());
            Intent i = new Intent(SplashActivity.this, LoginSelectionActivity.class);
            finish();
            startActivity(i);
        } catch (Exception e) {
            Log.e("sccs", "Exception: " + e.getMessage());
            Intent i = new Intent(SplashActivity.this, LoginSelectionActivity.class);
            finish();
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //finish();
    }
}
