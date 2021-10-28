package com.prouman.guest_section;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prouman.HomeActivity;
import com.prouman.R;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.Util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GuestLoginActivity extends Fragment {
    private static final int REQUEST_SIGNUP = 0;
    private static final String TAG = "LOGG";
    String affLink;
    TextView bactToHome;
    EditText emailEdt;
    String firstName;
    String imgName;
    String lastName;
    Button guestLogin;
    Button nextBtn;
    ImageView link_signup;
    String phone;
    ProgressDialog progressDialog;
    String referalCode;
    String shopLink;

    CheckBox check_term,check_privacy;
    String uproRef;
    String uproID;
    private String emailId;
    private String websiteUrl;
    SessionManager session;
    String G_status;
    LinearLayout llFullEdit;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        final View rootView = inflater.inflate(
                R.layout.guest_login, container, false);
      //  setContentView(R.layout.guest_login);
        session = new SessionManager(getActivity());
        emailEdt = (EditText) rootView.findViewById(R.id.input_email);
        llFullEdit =  rootView.findViewById(R.id.llFullEdit);
        emailEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    emailEdt.setHint("");
                else
                    emailEdt.setHint("Referral Code");
            }
        });
        G_status=session.getPreferences(getActivity(),"G_status");
         guestLogin = (Button) rootView.findViewById(R.id.btn_login);
        link_signup = (ImageView) rootView.findViewById(R.id.link_signup);
        check_term=(CheckBox)rootView.findViewById(R.id.check_term);
        String termconditon=getResources().getString(R.string.accept_t)+"<a style='color=#000000' href='https://app.prouman.network/auth_public/view_terms_conditions'>"+" "+getResources().getString(R.string.terms_condition)+"</a>";
       // String privacy="Accept <a href='https://www.theshopcom.com/it/legal/shop/privacy/'>Privacy</a>";
        check_privacy=(CheckBox)rootView.findViewById(R.id.check_privacy);
        check_term.setText(Html.fromHtml(termconditon));//getActivity().getResources().getString(R.string.Acceptterms)));
        check_term.setMovementMethod(LinkMovementMethod.getInstance());
        check_privacy.setText(Html.fromHtml(getResources().getString(R.string.accept_t)+"<a style='color=#000000' href='https://app.prouman.network/auth_public/view_privacy'>"+" "+getResources().getString(R.string.privacy)+"</a>"));//getActivity().getResources().getString(R.string.Acceptprivacy)));
        check_privacy.setMovementMethod(LinkMovementMethod.getInstance());
        guestLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!G_status.equals("1")) {
                    if (emailEdt != null) {
                        referalCode = emailEdt.getText().toString();
                        if (check_privacy.isChecked() && check_term.isChecked()) {
                            loginByRefferalId(referalCode);
                        } else {
                            Toast.makeText(getActivity(), "Please Accept terms and privacy", Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                    Toast.makeText(getActivity(), "No id Found", Toast.LENGTH_LONG).show();

                }
                else{
                    Bundle bundle=new Bundle();
                    SharedPreferences sharedPreferences =getActivity().getSharedPreferences("MyPref",0);
                    bundle.putString("FName", sharedPreferences.getString(PrefrencesConstant.guestfirstname, ""));
                    bundle.putString("LName", sharedPreferences.getString(PrefrencesConstant.guestlastname, ""));
                    bundle.putString("Phone", sharedPreferences.getString(PrefrencesConstant.guestlastPhone, ""));
                    bundle.putString("imgName", sharedPreferences.getString(PrefrencesConstant.guestImagename, ""));
                    bundle.putString("uproRef", sharedPreferences.getString(PrefrencesConstant.guestuproref, ""));
                    bundle.putString("uproAffLink", sharedPreferences.getString(PrefrencesConstant.guestuproasfflink, ""));
                    bundle.putString("uproShopLink", sharedPreferences.getString(PrefrencesConstant.guestuproshoplink, ""));
                    bundle.putString("upro_main_email", sharedPreferences.getString(PrefrencesConstant.guestmainemail, ""));
                    bundle.putString(PrefrencesConstant.uproid, sharedPreferences.getString(PrefrencesConstant.guestgproid, ""));
                    bundle.putString("websiteUrl", sharedPreferences.getString(PrefrencesConstant.guestWebsite, ""));
                    SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
                    // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    //editor.putString(PrefrencesConstant.guestgproid, uproID);
//                    editor.putString("G_FName", firstName);
//                    editor.putString("G_LName", lastName);
//                    editor.putString("G_Phone", phone);
//                    editor.putString("G_imgName", imgName);
//                    editor.putString("G_uproRef", uproRef);
//                    editor.putString("G_uproAffLink", affLink);
//                    editor.putString("G_uproShopLink", shopLink);
//                    editor.putString("G_upro_main_email",emailId);
//                    editor.putString(PrefrencesConstant.guestgproid, uproID);
//                    editor.putString("G_websiteUrl", websiteUrl);
                    editor.putString(PrefrencesConstant.uproid, sharedPreferences.getString(PrefrencesConstant.guestgproid, ""));
                    editor.putString(PrefrencesConstant.hash,"");
                    editor.commit();
                    Intent intent = new Intent(getActivity(), NinjaGuestHome.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                   // getActivity().finish();
                }
            }
        });
        link_signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HomeActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        if(G_status.equals("1"))
        {
            emailEdt.setVisibility(View.GONE);
            check_privacy.setVisibility(View.GONE);
            check_term.setVisibility(View.GONE);
            llFullEdit.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        G_status=session.getPreferences(getActivity(),"G_status");
        if(G_status.equals("1"))
        {
            emailEdt.setVisibility(View.GONE);
            check_privacy.setVisibility(View.GONE);
            check_term.setVisibility(View.GONE);
            llFullEdit.setVisibility(View.GONE);
        }
        else{
            emailEdt.setVisibility(View.VISIBLE);
            check_privacy.setVisibility(View.VISIBLE);
            check_term.setVisibility(View.VISIBLE);
            llFullEdit.setVisibility(View.VISIBLE);
        }
    }

    private void loginByRefferalId(final String referalCode) {
        progressDialog = new ProgressDialog(getActivity(),R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login Into Guest Account.......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GUEST_URL, new Listener<String>() {
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
                        uproRef = jsonObject1.getString("upro_referance");
                        affLink = jsonObject1.getString("upro_affiliation_link");
                        shopLink = jsonObject1.getString("upro_shoplink");
                        uproID = jsonObject1.getString("upro_id");
                        emailId = jsonObject1.getString("upro_main_email");
                        websiteUrl = jsonObject1.getString("upro_subdomain_link");
                        Bundle bundle = new Bundle();
                        bundle.putString("FName", firstName);
                        bundle.putString("LName", lastName);
                        bundle.putString("Phone", phone);
                        bundle.putString("imgName", imgName);
                        bundle.putString("uproRef", uproRef);
                        bundle.putString("uproAffLink", affLink);
                        bundle.putString("uproShopLink", shopLink);
                        bundle.putString("upro_main_email",emailId);
                        bundle.putString(PrefrencesConstant.uproid, uproID);
                        bundle.putString("websiteUrl", websiteUrl);
                        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
                        // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        //editor.putString(PrefrencesConstant.guestgproid, uproID);
                        editor.putString(PrefrencesConstant.guestfirstname, firstName);
                        editor.putString(PrefrencesConstant.guestlastname, lastName);
                        editor.putString(PrefrencesConstant.guestlastPhone, phone);
                        editor.putString(PrefrencesConstant.guestImagename, imgName);
                        editor.putString(PrefrencesConstant.guestuproref, uproRef);
                        editor.putString(PrefrencesConstant.guestuproasfflink, affLink);
                        editor.putString(PrefrencesConstant.guestuproshoplink, shopLink);
                        editor.putString(PrefrencesConstant.guestmainemail,emailId);
                        editor.putString(PrefrencesConstant.guestgproid, uproID);
                        editor.putString(PrefrencesConstant.guestWebsite, websiteUrl);
                        editor.putString(PrefrencesConstant.uproid, uproID);
                        editor.putString(PrefrencesConstant.hash,"");

                        editor.putString(PrefrencesConstant.term_link,jsonObject1.getString("term_link"));
                        editor.putString(PrefrencesConstant.privacy_link,jsonObject1.getString("privacy_link"));
                        editor.putString(PrefrencesConstant.oms_referral_link,jsonObject1.getString("oms_referral_link"));
                        editor.putString(PrefrencesConstant.oms_referral_nocopy_link,jsonObject1.getString("oms_referral_nocopy_link"));
                        editor.putString(PrefrencesConstant.shop_pro_link,jsonObject1.getString("shop_pro_link"));
                        editor.putString(PrefrencesConstant.shop_link,jsonObject1.getString("shop_link"));
                        editor.putString(PrefrencesConstant.shortcodes,jsonObject1.getString("shortcodes"));
                        editor.putString(PrefrencesConstant.progetto_link,jsonObject1.getString("progetto_link"));
                        editor.putString(PrefrencesConstant.copyrightlink,jsonObject1.getString("copyright_link"));

                     //   editor.putString(PrefrencesConstant.viralninjalink,jsonObject1.getString("viral_ninja_link"));
                       // editor.putString(PrefrencesConstant.kyanilink,jsonObject1.getString("kyani_link"));
                      //  editor.putString(PrefrencesConstant.academylink,jsonObject1.getString("academy_link"));
                     //   editor.putString(PrefrencesConstant.rsslink,jsonObject1.getString("rss_link"));
                   //     editor.putString(PrefrencesConstant.podcastlink,jsonObject1.getString("podcast_link"));
                       // editor.putString(PrefrencesConstant.uprosubdomain,jsonObject1.getString("upro_shoplink"));
                        try{
                            editor.putString(PrefrencesConstant.guestDistributorId,jsonObject1.getString("upro_distributor_id"));
                            editor.putString(PrefrencesConstant.facebooklink,jsonObject1.getString("facebook_link"));
                            editor.putString(PrefrencesConstant.twitterlink,jsonObject1.getString("twitter_link"));
                            editor.putString(PrefrencesConstant.googlelink,jsonObject1.getString("google_link"));
                            editor.putString(PrefrencesConstant.youtubelink,jsonObject1.getString("youtube_link"));
                            editor.putString(PrefrencesConstant.pinterestLink,jsonObject1.getString("pinterest_link"));
                            editor.putString(PrefrencesConstant.linkedinLink,jsonObject1.getString("linkedin_link"));
                            editor.putString(PrefrencesConstant.vimeoLink,jsonObject1.getString("vimeo_link"));
                            editor.putString(PrefrencesConstant.instagramLink,jsonObject1.getString("instagram_link"));
                            editor.putString(PrefrencesConstant.skypeLink,jsonObject1.getString("skype_link"));
                            editor.putString("upro_shop_link",jsonObject1.getString("upro_shop_link"));
                        }
                        catch (Exception e){}
                        editor.commit();
                        emailEdt.setText("");
                        session.setPreferences(getActivity(), "G_status", "1");
                        Intent intent = new Intent(getActivity(), NinjaGuestHome.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                      /*  getActivity().finish();*/
                        return;
                    }
                    else {
                        if(progressDialog!=null){
                        progressDialog.dismiss();}
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new ErrorListener() {
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
                params.put("id",referalCode);
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
