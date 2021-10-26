package com.prouman.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prouman.R;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.guest_section.GuestLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jcs on 9/4/2016.
 */
public class NinjaLoginActivity extends AppCompatActivity {
    Button guestBtn;
    Intent intent;
    TextView memberBtn;
    EditText distributorId,etPassword;
    ImageView textView;
    String firstName;
    String imgName;
    String lastName;
    Button nextBtn;
    String password;
    String phone;
    ProgressDialog progressDialog;
    String referalCode;
    String uproRef;
    String uproID;
    private String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //textView = (ImageView) findViewById(R.id.logo);
        guestBtn = (Button) findViewById(R.id.btn_login);
        memberBtn = (TextView) findViewById(R.id.link_signup);
        distributorId =(EditText)findViewById(R.id.input_distributor_id);
        etPassword =(EditText)findViewById(R.id.input_password);
        referalCode = distributorId.getText().toString();
        password = etPassword.getText().toString();
        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NinjaLoginActivity.this, GuestLoginActivity.class);
                startActivity(intent);
            }
        });
       memberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referalCode = distributorId.getText().toString();
                password = etPassword.getText().toString();
                loginByRefferalId(referalCode);
            }
        });
    }

    private void loginByRefferalId(String str) {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login Into Member Account......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.MEMBER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean result = false;
                    try {
                        result = jsonObject.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (result) {

                        progressDialog.hide();
                        JSONObject jsonObject1 = jsonObject.getJSONObject("value");
                        firstName = jsonObject1.getString("upro_first_name");
                        lastName = jsonObject1.getString("upro_last_name");
                        phone = jsonObject1.getString("upro_phone");
                        imgName = jsonObject1.getString("upro_photo");
                        uproID = jsonObject1.getString("upro_id");
                        emailId = jsonObject1.getString("upro_main_email");
                        Bundle bundle = new Bundle();
                        bundle.putString("FName", firstName);
                        bundle.putString("LName", lastName);
                        bundle.putString("Phone", phone);
                        bundle.putString("imgName", imgName);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(PrefrencesConstant.uproid, uproID);
                        editor.commit();
                        Intent intent = new Intent(NinjaLoginActivity.this, NinjaMemberHome.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    else {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap();
                params.put("id", referalCode);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
