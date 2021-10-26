package com.prouman.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.prouman.app.Config;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.registerDevice, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//             //  if(progressDialog!=null){progressDialog.dismiss();}
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if(jsonObject.getString("success").equals("true")) {
//
//                    }
//
//                    // Toast.makeText(NinjaGuestHome.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
//                } catch (JSONException e) {
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                super.getParams();
//                Map<String, String> params = new HashMap<>();
//                SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("MyPref",0);
//
//                String uproID = sharedPreferences.getString(PrefrencesConstant.guestgproid,null);
//                params.put("upro_id", uproID);
//                params.put("hash","");
//                params.put("device","a");
//                params.put("email","");
//                params.put("phone","");
//                params.put("imei",getDeviceImei());
//                params.put("reg_id",token);//sessionManager.getGCMToken());
//
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(stringRequest);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();

    }
}

