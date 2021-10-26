package com.prouman.notification;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prouman.R;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ComposeNotification extends AppCompatActivity implements View.OnClickListener{
EditText input_title,input_message;
    Button btn_sent;
    ImageView layout_logo;
    ProgressDialog progressDialog;
    String uproID,hash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_notification);
        SharedPreferences sharedPreferences =getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash=sharedPreferences.getString(PrefrencesConstant.hash,null);
        input_title=(EditText)findViewById(R.id.input_title);
        input_message=(EditText)findViewById(R.id.input_message);
        btn_sent=(Button)findViewById(R.id.btn_sent);
        layout_logo=(ImageView)findViewById(R.id.layout_logo);
        layout_logo.setOnClickListener(this);
        btn_sent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_sent:
                if(input_title.getText().toString().trim().length()==0&&input_message.getText().toString().trim().length()==0)
                {
                    input_title.setError(getResources().getString(R.string.notificationtitle));
                    input_message.setError(getResources().getString(R.string.notificationmessage));
                }
                else if(input_title.getText().toString().trim().length()==0){input_title.setError(getResources().getString(R.string.notificationtitle));}
                else if(input_message.getText().toString().trim().length()==0){input_message.setError(getResources().getString(R.string.notificationmessage));}
                else{sendNotification();}
                break;
            case R.id.layout_logo:
            finish();
            break;
        }
    }
    private void sendNotification() {
        progressDialog = new ProgressDialog(ComposeNotification.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.SendPushNotification_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(progressDialog!=null){ progressDialog.dismiss();}
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(ComposeNotification.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        input_message.setText("");
                        input_title.setText("");
                    }
                    else
                    {
                        Toast.makeText(ComposeNotification.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    if(progressDialog!=null){
                        progressDialog.hide();}
//                    videoViewAdapter = new NewVideoAdapter(mVideos, PrivateVideoActivity.this);
//                    mVideoRecyclerView.setAdapter(videoViewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null){
                    progressDialog.hide();}
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                params.put("title", input_title.getText().toString().trim());
                params.put("message", input_message.getText().toString().trim());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ComposeNotification.this);
        requestQueue.add(stringRequest);

    }
}
