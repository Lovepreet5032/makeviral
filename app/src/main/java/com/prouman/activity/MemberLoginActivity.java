package com.prouman.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prouman.HomeActivity;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.Util.SessionManager;
import com.prouman.member_all.GHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

public class MemberLoginActivity extends Fragment {
    private static final int REQUEST_SIGNUP = 0;
    private static final String TAG = "LOGG";
LinearLayout llEdit2,llEdit1;
    EditText edit1;
    EditText edit2;
    String firstName;
    String imgName;
    String lastName;
    Button loginBtn;
    LinearLayout link_signup;
    String password;
    String phone;
    String upro_id, hash;
    String websiteUrl;
    ProgressDialog progressDialog;
    String referalCode;
    ImageView logoPhoto;
    TextView tv1;
    SessionManager session;
    String affLink,shopLink,emailId;
    JSONArray prospect_fields;
    TextView txt_forgotpass;
    String L_status;
    @Override
    public View onCreateView(LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.member_login, container, false);

        // logoPhoto = (ImageView) findViewById(R.id.logo);
        session = new SessionManager(getActivity());
        L_status =session.getPreferences(getActivity(),"L_status");
        //  session.checkLogin();
        edit1 = (EditText) rootView.findViewById(R.id.input_distributor_id);
        edit2 = (EditText) rootView.findViewById(R.id.input_password);
        llEdit1 = (LinearLayout) rootView.findViewById(R.id.llEdit1);
        llEdit2 = (LinearLayout) rootView.findViewById(R.id.llEdit2);
        txt_forgotpass=(TextView)rootView.findViewById(R.id.txt_forgotpass);
        txt_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://app.prouman.network/auth/forgotten_password")));
            }
        });
        edit1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    edit1.setHint("");
                else
                    edit1.setHint(R.string.Distributor_id);
            }
        });
        edit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    edit2.setHint("");
                else
                    edit2.setHint("Password");
            }
        });
        loginBtn = (Button) rootView.findViewById(R.id.btn_login);
        link_signup = (LinearLayout) rootView.findViewById(R.id.link_signup);
        referalCode = edit1.getText().toString();
        password = edit2.getText().toString();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!L_status.equals("1")) {
                    referalCode = edit1.getText().toString();
                    password = edit2.getText().toString();
                    loginByRefferalId(referalCode);
                }
                else {
                    Intent intent = new Intent(getActivity(), GHomeActivity.class);
                    startActivity(intent);
                 //   getActivity().finish();
                }
            }
        });
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences =getActivity().getSharedPreferences("MyPref",0);
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
                // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(PrefrencesConstant.uproid, sharedPreferences.getString("L_uproId",""));
                editor.putString(PrefrencesConstant.hash,sharedPreferences.getString("L_hash",""));
                editor.putString("FName", sharedPreferences.getString("L_FName",""));
                editor.putString("LName",sharedPreferences.getString("L_LName",""));
                editor.putString("Phone", sharedPreferences.getString("L_Phone",""));
                editor.putString("imgName",sharedPreferences.getString("L_imgName",""));
                editor.putString("upro_subdomain_link",sharedPreferences.getString("L_upro_subdomain_link",""));
                //   editor.putString("Lpodcast_link",sharedPreferences.getString("podcast_link",""));
                //editor.putString("upro_id",hash);
                editor.commit();
                Intent i = new Intent(getActivity(),HomeActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        if(L_status.equals("1")) {
            edit1.setVisibility(View.GONE);
            edit2.setVisibility(View.GONE);
            txt_forgotpass.setVisibility(View.GONE);
            llEdit1.setVisibility(View.GONE);
            llEdit2.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        L_status =session.getPreferences(getActivity(),"L_status");
        if(L_status.equals("1")) {
            edit1.setVisibility(View.GONE);
            edit2.setVisibility(View.GONE);
            llEdit1.setVisibility(View.GONE);
            llEdit2.setVisibility(View.GONE);
            txt_forgotpass.setVisibility(View.GONE);
        }
        else{
            edit1.setVisibility(View.VISIBLE);
            edit2.setVisibility(View.VISIBLE);
            llEdit1.setVisibility(View.VISIBLE);
            llEdit2.setVisibility(View.VISIBLE);
            txt_forgotpass.setVisibility(View.VISIBLE);
        }
    }

    private void loginByRefferalId(String str) {
        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login Into Member Account......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.MEMBER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(progressDialog!=null){progressDialog.dismiss();}
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean result = false;
                    try {
                        result = jsonObject.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (result) {
                        if(progressDialog!=null){
                        progressDialog.hide();}
                        JSONObject jsonObject1 = jsonObject.getJSONObject("value");
                        firstName = jsonObject1.getString("upro_first_name");
                        lastName = jsonObject1.getString("upro_last_name");
                        phone = jsonObject1.getString("upro_phone");
                        imgName = jsonObject1.getString("upro_photo");
                        upro_id = jsonObject1.getString("upro_id");
                        websiteUrl = jsonObject1.getString("upro_subdomain_link");
                        hash = jsonObject1.getString(PrefrencesConstant.hash);
                        affLink = jsonObject1.getString("upro_affiliation_link");
                        shopLink = jsonObject1.getString("upro_shoplink");

                        emailId = jsonObject1.getString("upro_main_email");
                        prospect_fields = jsonObject1.getJSONArray("prospect_fields");
                        AppConstant.jsonArray = prospect_fields;
                        Bundle bundle = new Bundle();
                        bundle.putString("FName", firstName);
                        bundle.putString("LName", lastName);
                        bundle.putString("Phone", phone);
                        bundle.putString("imgName", imgName);
                        bundle.putString("upro_id", upro_id);
                        bundle.putString("upro_subdomain_link", websiteUrl);
                        // session.createMemberLoginSession(firstName,lastName,phone,imgName,upro_id,websiteUrl,hash,affLink,shopLink,emailId);
                        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
                        // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("L_uproId", upro_id);
                        editor.putString("L_hash",hash);
                        editor.putString("L_FName", firstName);
                        editor.putString("L_LName",lastName);
                        editor.putString("L_Phone", phone);
                        editor.putString("L_imgName",imgName);
                        editor.putString("L_upro_subdomain_link", websiteUrl);
                        editor.putString("upro_affiliation_link", affLink);
                        editor.putString(PrefrencesConstant.uproid, upro_id);
                        editor.putString(PrefrencesConstant.hash,hash);
                        editor.putString("FName", firstName);
                        editor.putString("LName",lastName);
                        editor.putString("Phone", phone);
                        editor.putString("imgName",imgName);
                        editor.putString("upro_main_email",emailId);
                        editor.putString("upro_subdomain_link", websiteUrl);
                        editor.putString("jsonarray", prospect_fields.toString());
                        editor.putString("Lcopyright_link",jsonObject1.getString("copyright_link"));
                        editor.putString(PrefrencesConstant.shop_link,jsonObject1.getString("shop_link"));
                               // editor.putString(PrefrencesConstant.memberviralninjalink,jsonObject1.getString("viral_ninja_link"));
                            //    editor.putString(PrefrencesConstant.memeberKyanilink,jsonObject1.getString("kyani_link"));
                            //    editor.putString(PrefrencesConstant.kyanilink,jsonObject1.getString("kyani_link"));
                            //    editor.putString("Lacademy_link",jsonObject1.getString("academy_link"));
                            //    editor.putString(PrefrencesConstant.memberrsslink,jsonObject1.getString("rss_link"));
                          //      editor.putString("Lpodcast_link",jsonObject1.getString("podcast_link"));
                        editor.putString("Lupro_subdomain_link",jsonObject1.getString("upro_shoplink"));
                        try{editor.putString("Lupro_distributor_id",jsonObject1.getString("upro_distributor_id"));
                            editor.putString("facebook_link",jsonObject1.getString("facebook_link"));
                            editor.putString("twitter_link",jsonObject1.getString("twitter_link"));
                            editor.putString("google_link",jsonObject1.getString("google_link"));
                            editor.putString("youtube_link",jsonObject1.getString("youtube_link"));
                            editor.putString("pinterest_link",jsonObject1.getString("pinterest_link"));
                            editor.putString("linkedin_link",jsonObject1.getString("linkedin_link"));
                            editor.putString("vimeo_link",jsonObject1.getString("vimeo_link"));
                            editor.putString("instagram_link",jsonObject1.getString("instagram_link"));
                            editor.putString("skype_link",jsonObject1.getString("skype_link"));
                            JSONObject jsonobjectstats=jsonObject1.getJSONObject("stats");
                            editor.putString(PrefrencesConstant.total_prospects,jsonobjectstats.getString("total_prospects"));
                            editor.putString(PrefrencesConstant.growth_rate,jsonobjectstats.getString("growth_rate"));
                            editor.putString(PrefrencesConstant.last_prospects,jsonobjectstats.getString("last_prospects"));}
                        catch (Exception e){}
                        //editor.putString("upro_id",hash);
                        //editor.putString("upro_id",hash);
                        editor.commit();
                        session.setPreferences(getActivity(), "L_status", "1");
                        session.setPreferences(getActivity(), "status", "1");
                        edit1.setText("");
                        edit2.setText("");
                        Intent intent = new Intent(getActivity(), GHomeActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                       // getActivity().finish();
                        return;
                    }
                    Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null){progressDialog.dismiss();}
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
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}

