package com.prouman.contactmanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.IntentConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.app.Config;
import com.prouman.common.SucessDataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransferProspects extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText input_distibutorid;
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_prospects);
        progressDialog = new ProgressDialog(this);
        input_distibutorid=(EditText)findViewById(R.id.input_distibutorid);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_distibutorid.getText().toString().trim().length()>0){
                    ArrayList<ContactManagerDataObject.ContactManagerListData> selectedManagerListData=getIntent().getExtras().getParcelableArrayList(IntentConstant.CONTACTLIST);
                    deleteProspects(selectedManagerListData);
                }
                else{Toast.makeText(TransferProspects.this,"Please enter email or distributorid",Toast.LENGTH_LONG).show();}
            }
        });
        findViewById(R.id.layout_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void deleteProspects(final ArrayList<ContactManagerDataObject.ContactManagerListData> selectedManagerListData) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        final String uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        final  String hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        progressDialog.setMessage("Loading");

        if(progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();}
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.TRANSFERPROSPECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                try {
                    Gson gson=new Gson();
                    SucessDataObject sucessDataObject =  gson.fromJson(response,SucessDataObject.class);
                    if(null != sucessDataObject) {
                        if (sucessDataObject.getSuccess().equalsIgnoreCase("true")) {
                            finish();
                        }
                        Toast.makeText(TransferProspects.this, sucessDataObject.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    else{ Toast.makeText(TransferProspects.this, "Server error", Toast.LENGTH_LONG).show();}
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null) {
                    progressDialog.hide();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                String str=null;
                for(int i=0;i<selectedManagerListData.size();i++)
                {
                    if(i==0){str=selectedManagerListData.get(i).getProId();}
                    else {str=str+","+selectedManagerListData.get(i).getProId();}
                }
                params.put("pro_id",str);
                params.put("dist_id",input_distibutorid.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
}
