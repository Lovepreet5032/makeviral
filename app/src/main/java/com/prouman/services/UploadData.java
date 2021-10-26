package com.prouman.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.test_db.ContactDbHandler;
import com.prouman.test_db.ContactTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

public class UploadData extends IntentService {

    String  mRequestBody="";
    StringBuilder encodedParams = new StringBuilder();
    public UploadData() {
        super("UploadData");
    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        //TODO do something useful
//        return Service.START_STICKY;
//    }
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ContactDbHandler db  = new ContactDbHandler(this);
        SharedPreferences sharedPreferences =getSharedPreferences("MyPref",0);
        List<ContactTest> contactItems= db.getAllEmailContacts();
        contactItems.addAll(db.getAllMobileContacts());
        final JSONArray jsonArray=new JSONArray();
        final JSONObject jsonObjectMAin=new JSONObject();

//        try {
//          //  jsonObjectMAin.put("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null));
//            //jsonObjectMAin.put("hash", sharedPreferences.getString(PrefrencesConstant.hash,null));
//            for(int i=0;i<contactItems.size();i++) {
//                ContactTest contactTest=contactItems.get(i);
//                HashMap<String,String> jsonObject = new HashMap<>();
//              //  JSONArray jsonArray1=new JSONArray();
//                String ss="";
//                if(null != contactTest.getName()) {
//                    String split[] = contactTest.getName().split("\\s+");
//                    if(split.length>1)
//                    {
//                        ss=ss+"first_name="+split[0];
//                        ss=ss+"last_name", split[1]);
//                    }
//                    else
//                        {
//                            ss=ss+"first_name",contactTest.getName());
//                            ss=ss+"last_name"+"");
//                        }
//                }
//                else{
//                    ss=ss+"first_name"+"");
//                    ss=ss+"last_name"+"");
//                }
//                ss=ss+"email"+ contactTest.getEmail_Id());
//
//                ss=ss+"telcode"+"");//(LIKE + 91)
//                ss=ss+"phone"+ contactTest.getPhone());
//                ss=ss+"address"+ "");
//                ss=ss+"state"+ "");
//                ss=ss+"zip"+ "");
//                ss=ss+"city"+ "");
//                ss=ss+"gender", "");
//                ss=ss+"dob","" );
//                ss=ss+"iso2", "");
//                ss=ss+"country", "");
//                ss=ss+"latitude", "");
//                ss=ss+"longitude", "");
////                jsonArray1.put(jsonObject);
//                jsonArray.put(jsonObject.toString());
//                jsonObjectMAin.put(i+"",jsonArray.toString());
//
//            }
//        //    jsonObjectMAin.put("Contact",jsonArray);
//        }catch (Exception e)
//        {
//           Toast.makeText(UploadData.this,e.toString(),Toast.LENGTH_LONG).show();
//        }
        String userProId=sharedPreferences.getString(PrefrencesConstant.uproid,null);
        String strhash=sharedPreferences.getString(PrefrencesConstant.hash,null);
        try {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null));
            hashMap.put("hash", sharedPreferences.getString(PrefrencesConstant.hash,null));

              jsonArray.put(hashMap);
            encodedParams.append("[");
            for(int i=0;i<contactItems.size();i++) {
                ContactTest contactTest=contactItems.get(i);
                JSONObject jsonObject = new JSONObject();
                //  JSONArray jsonArray1=new JSONArray();
                String ss="";
                if(null != contactTest.getName()) {
                    String split[] = contactTest.getName().split("\\s+");
                    for (int j=0;j<split.length;i++){}
                    if(split.length>1)
                    {
                        jsonObject.put("first_name",split[0]);
//                        String strName="";
//                        for(int j=1;j<split.length;j++) {
//                            strName=strName+split[j-1];
//                        }
                        jsonObject.put("last_name", split[1]);
                        }
                    else
                    {
                        jsonObject.put("first_name",contactTest.getName());
                        jsonObject.put("last_name","");
                    }
                }
                else{
                    jsonObject.put("first_name","");
                    jsonObject.put("last_name","");
                }
                jsonObject.put("email", contactTest.getEmail_Id());

              //  jsonObject.put("telcode","");//(LIKE + 91)
                jsonObject.put("phone", contactTest.getPhone());
                //jsonObject.put("address", "");
                //jsonObject.put("state", "");
                //jsonObject.put("zip", "");
                //jsonObject.put("city", "");
                //jsonObject.put("gender", "");
                //jsonObject.put("dob","" );
                //jsonObject.put("iso2", "");
                //jsonObject.put("country", "");
                //jsonObject.put("latitude", "");
                //jsonObject.put("longitude", "");
//                jsonArray1.put(jsonObject);
                if(i>0){
                    encodedParams.append(",");
                }
                encodedParams.append(jsonObject.toString());
               // jsonObjectMAin.put("contacts",jsonArray);

            }
            encodedParams.append("]");
           // mRequestBody=jsonArray.toString();

            //    jsonObjectMAin.put("Contact",jsonArray);
        }catch (Exception e)
        {
            Toast.makeText(UploadData.this,e.toString(),Toast.LENGTH_LONG).show();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST,ConfigURL.IMPORT_CONTACTS,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(UploadData.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
//                        input_message.setText("");
//                        input_title.setText("");
                    }
                    else
                    {
                        Toast.makeText(UploadData.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }

//                    videoViewAdapter = new NewVideoAdapter(mVideos, PrivateVideoActivity.this);
//                    mVideoRecyclerView.setAdapter(videoViewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UploadData.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
//        {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                    return null;
//                }
//            }
           {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
          //  super.getParams();
            SharedPreferences sharedPreferences =getSharedPreferences("MyPref",0);
            Map<String, String> params = new HashMap<>();
            params.put("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null));
            params.put("hash",  sharedPreferences.getString(PrefrencesConstant.hash,null));
            params.put("contacts",encodedParams.toString());


            return params;
        }
//              @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                SharedPreferences sharedPreferences =getSharedPreferences("MyPref",0);
//                Map<String, String> params = new HashMap<>();
//                params.put("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null));
//                params.put("hash",  sharedPreferences.getString(PrefrencesConstant.hash,null));
//                params.put("contacts",String.valueOf(jsonArray));
//
//                return params;
       //     }
    };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
private Map<String,String> getMap(){
    SharedPreferences sharedPreferences =getSharedPreferences("MyPref",0);
    Map<String, String> params = new HashMap<>();
    params.put("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null));
    params.put("hash",  sharedPreferences.getString(PrefrencesConstant.hash,null));
   // params.put("contacts",jsonArray.toString());
    return params;
}
}
