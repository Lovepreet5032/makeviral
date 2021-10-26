package com.prouman.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.prouman.R;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by jcs on 10/2/2016.
 */
public class WatchTheWebinar extends AppCompatActivity {
    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;
    String url;
    private RelativeLayout backToGuest;
    private RelativeLayout bottomLayout;
    private boolean isShowShareLayout;
    private ProgressBar loadProgress;
    private ImageView backBtn;
    private OrientationEventListener myOrientationEventListener;
    private RelativeLayout shareLayout;
    private TextView title;
    private RelativeLayout topLayout;
    private String strShareUrl,firstName,lastName,phone,img_name,websiteUrl;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    String uproID;
    String hash="";
    ImageView call_tv,join_the_kyani_tv,sms_tv,share_tv;
    /**
     * Called when the activity is first created.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_the_webinar);
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPref", 0);
        sharedPreferences =this.getSharedPreferences("MyPref",0);
        RelativeLayout layout_main=(RelativeLayout)findViewById(R.id.layout_main);
        if(sharedPreferences.getString(PrefrencesConstant.hash,"").equalsIgnoreCase("")){
            layout_main.setBackground(getResources().getDrawable(R.drawable.bg_guest));
        }
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash= sharedPreferences.getString(PrefrencesConstant.hash,null);
        firstName=sharedPreferences.getString("FName", null);
        lastName=sharedPreferences.getString("LName",null);
        phone= sharedPreferences.getString("Phone", null);
        img_name=sharedPreferences.getString("imgName",null);
        websiteUrl=sharedPreferences.getString("upro_subdomain_link", null);
        sharedPreferences.getString(PrefrencesConstant.hash,null);
        title = (TextView) findViewById(R.id.text_guest_right_menu_title);
        backToGuest = (RelativeLayout) findViewById(R.id.layout_back_to_guest);
        topLayout = (RelativeLayout) findViewById(R.id.layout_top);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        call_tv= (ImageView) findViewById(R.id.call_tv);
        join_the_kyani_tv= (ImageView) findViewById(R.id.join_the_kyani_tv);
        sms_tv= (ImageView) findViewById(R.id.sms_tv);
        share_tv= (ImageView) findViewById(R.id.share_tv);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                finish();

            }
        });
        bottomLayout = (RelativeLayout) findViewById(R.id.layout_bottom);
        customViewContainer = (FrameLayout) findViewById(R.id.web_main_container);
        webView = (WebView) findViewById(R.id.web_details);
       // shareTv = (ImageView) findViewById(R.id.share_tv);
        share_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT, strShareUrl);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        mWebViewClient = new myWebViewClient();
        webView.setWebViewClient(mWebViewClient);


        mWebChromeClient = new myWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);
       /* webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSaveFormData(true);*/
        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMinimumFontSize(10);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setVerticalScrollBarEnabled(false);

        //  url = changedHeaderHtml(getIntent().getStringExtra("videoUrl"));
//        url = getIntent().getStringExtra("videoUrl");
//        //url = changedHeaderHtml("<body bgcolor=\"#000000\"><iframe src="+url+" width=\"640\" height=\"360\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>");
//        url = changedHeaderHtml(url);
//        // url=""
//        strShareUrl=getIntent().getStringExtra("videoUrl");
        getVideo();

        //  String frameVideo = changedHeaderHtml(url);

        String s = "<iframe src=\"";
//        int ix = frameVideo.indexOf(s) + s.length();
//        strShareUrl = frameVideo.substring(ix, frameVideo.indexOf("\"", ix + 1));
        // frameVideo.substring(startIndex,endIndex-3);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        call_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });
        sms_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });
        share_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT, strShareUrl);
                startActivity(Intent.createChooser(shareIntent, "Share via"));

            }
        });
        join_the_kyani_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences sharedPreferences =WatchTheWebinar.this.getSharedPreferences("MyPref",0);
//                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.kyanilink, ""))));
            }
        });
    }
    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= 19) {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this);
            Intent sendIntent = new Intent("android.intent.action.SEND", Uri.parse("tel:" + phone));
            sendIntent.putExtra("address", phone);
            sendIntent.setType("text/plain");
            sendIntent.putExtra("android.intent.extra.TEXT", "");
            if (defaultSmsPackageName != null) {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);
            return;
        }
        Intent smsIntent = new Intent("android.intent.action.VIEW");
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", phone);
        smsIntent.putExtra("sms_body", "message");
        startActivity(smsIntent);
    }

    private void onCall() {
        if (Build.VERSION.SDK_INT >= 19) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        123);
            } else {
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+phone)));
            return;
            }
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            Intent sendIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
            sendIntent.putExtra("address", phone);
            if (tm != null && tm.getSimState() == 5) {
                startActivity(sendIntent);
                return;
            }
            return;
        }
        Intent callIntent = new Intent("android.intent.action.DIAL");
        callIntent.setPackage("com.android.phone");
        callIntent.setData(Uri.parse(phone));
        startActivity(callIntent);
    }
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            return;

        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(WatchTheWebinar.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Log.d("TAG", "Call Permission Not Granted");
                } else {
                    onCall();
                }
            default:
        }
    }

    public static String changedHeaderHtml(String strUrl) {
        return "<html><head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body bgcolor=\"#000000\" marginheight=\"0\" marginwidth=\"0\"><iframe src="+strUrl+" width=\"640\" height=\"360\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen marginheight=\"0\" marginwidth=\"0\"></iframe></body></html>";
        // return "<html><head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>" + strUrl + "</body></html>";
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        if (inCustomView()) {
            hideCustomView();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("WebPlayer Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            webView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(WatchTheWebinar.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            webView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
    private void getVideo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GET_WEBINAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(progressDialog!=null){progressDialog.hide();}
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jobjwebinar=jsonObject.getJSONObject("webinar");
                    String  webinar = jobjwebinar.getString("wlink");

                    String vId = jobjwebinar.getString("id");
                   // vTitle = jobjwebinar.getString("title");

                    // vSource = jobjwebinar.getString("wpiframe");
                    url = changedHeaderHtml(webinar);
                    webView.setInitialScale(1);
                    //  frameVideo = changedHeaderHtml(url);
                    strShareUrl= webinar;
                    webView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
                  //  webView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null){progressDialog.hide();}
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                if(!hash.equals("")) {
                    params.put("hash", hash);
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

}
