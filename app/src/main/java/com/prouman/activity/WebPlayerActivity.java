package com.prouman.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.Util.SessionManager;
import com.prouman.videonewscreen.Caption;
import com.prouman.videonewscreen.InappWebview;
import com.prouman.videonewscreen.VideoData;

import java.util.ArrayList;

/**
 * Created by jcs on 9/19/2016.
 */
public class WebPlayerActivity extends AppCompatActivity  implements View.OnTouchListener, Handler.Callback{
    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private WebView web_videodesc;
    private myWebViewClient mWebViewClient;
    private myWebViewClient mWebViewClientdesc;
    String url;
    private RelativeLayout backToGuest;
    private LinearLayout bottomLayout;
    private boolean isShowShareLayout;
    private ProgressBar loadProgress;
    private ImageView backBtn;
    private OrientationEventListener myOrientationEventListener;
    private RelativeLayout shareLayout;
    private TextView title;
    private ImageView shareTv;
    ImageView call,message;
    private RelativeLayout topLayout;
    private String strShareUrl;
    SharedPreferences sharedPreferences;
    LinearLayout layout_caption;
    ScrollView scrollview;
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    private VideoData mVideoData;
    private final Handler handler = new Handler(this);
    AlertDialog alertDialog=null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * Called when the activity is first created.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_web_view);
        SessionManager sessionManager = new SessionManager(this);
        RelativeLayout layout_main=(RelativeLayout)findViewById(R.id.layout_main);
        sharedPreferences=this.getSharedPreferences("MyPref",0);
        if(sharedPreferences.getString(PrefrencesConstant.hash,"").equalsIgnoreCase("")){
            layout_main.setBackground(getResources().getDrawable(R.drawable.bg_guest));
        }
        title = (TextView) findViewById(R.id.title);
        web_videodesc=(WebView)findViewById(R.id.web_videodesc);
        backToGuest = (RelativeLayout) findViewById(R.id.layout_back_to_guest);
        topLayout = (RelativeLayout) findViewById(R.id.layout_top);
        backBtn = (ImageView) findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                finish();

            }
        });
        title.setText(getIntent().getStringExtra("title"));
        bottomLayout = (LinearLayout) findViewById(R.id.layout_bottom);
        customViewContainer = (FrameLayout) findViewById(R.id.web_main_container);
        webView = (WebView) findViewById(R.id.web_details);
        webView.setOnTouchListener(this);
        shareTv = (ImageView) findViewById(R.id.share_tv);
        call = (ImageView) findViewById(R.id.call);
        message = (ImageView) findViewById(R.id.message);
        layout_caption=(LinearLayout)findViewById(R.id.layout_caption);
        mVideoData=getIntent().getExtras().getParcelable("vdata");
        if(mVideoData.getPrivateVideo().equalsIgnoreCase("1")){showPasswordDialog();}
        scrollview=(ScrollView)findViewById(R.id.scrollview);
        final ArrayList<Caption> captionArrayList=getIntent().getExtras().getParcelableArrayList("caption");
        if(null != captionArrayList && captionArrayList.size()>0)
        {
            for(int i=0;i<captionArrayList.size();i++)
            {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.row_dynamic_caption, null);
               final ImageView imageView = (ImageView) view.findViewById(R.id.img);
                TextView txt = (TextView) view.findViewById(R.id.txt);
                Caption caption=captionArrayList.get(i);
                txt.setText(caption.getLabel());
               // final ImageView imageView=new ImageView(WebPlayerActivity.this);
                imageView.setTag(String.valueOf(i));
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
                view.setLayoutParams(params);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(WebPlayerActivity.this, InappWebview.class);
                        intent.putExtra("url",captionArrayList.get(Integer.valueOf((String) imageView.getTag())).getLink());
                        intent.putExtra("title",captionArrayList.get(Integer.valueOf((String) imageView.getTag())).getLabel());
                        startActivity(intent);
                    }
                });
//                imageView.setMaxWidth(50);
//                imageView.setMaxHeight(50);
                layout_caption.addView(view);
                Picasso.with(this).load(caption.getIcon()).placeholder(R.drawable.round_corner).into(imageView);
            }
        }
        else{layout_caption.setVisibility(View.GONE);}
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sharedPreferences.getString("Phone", null), null)));
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 19) {
                    String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(WebPlayerActivity.this);
                    Intent sendIntent = new Intent("android.intent.action.SEND", Uri.parse("tel:" + sharedPreferences.getString("Phone", null)));
                    sendIntent.putExtra("address", sharedPreferences.getString("Phone", null));
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra("android.intent.extra.TEXT", strShareUrl);
                    if (defaultSmsPackageName != null) {
                        sendIntent.setPackage(defaultSmsPackageName);
                    }
                    startActivity(sendIntent);
                    return;
                }
                Intent smsIntent = new Intent("android.intent.action.VIEW");
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address",  sharedPreferences.getString("Phone", null));
                smsIntent.putExtra("sms_body", strShareUrl);
                smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(smsIntent);
            }
        });
        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Ninja App");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, strShareUrl);
//                startActivity(Intent.createChooser(shareIntent, "Share via"));
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT, strShareUrl);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        mWebViewClient = new myWebViewClient();
        mWebViewClientdesc=new myWebViewClient();
        webView.setWebViewClient(mWebViewClient);
      //  web_videodesc.setWebViewClient(mWebViewClientdesc);
        mWebChromeClient = new myWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);
        webView.setInitialScale(0);
        webView.setHorizontalScrollBarEnabled(false);
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

        settings.setPluginState(WebSettings.PluginState.ON);

        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setVerticalScrollBarEnabled(false);

        web_videodesc.setWebChromeClient(mWebChromeClient);
        web_videodesc.setInitialScale(0);
        web_videodesc.setHorizontalScrollBarEnabled(false);
        WebSettings settingsdesc = web_videodesc.getSettings();
        settingsdesc.setAllowFileAccess(true);
        settingsdesc.setJavaScriptEnabled(true);
        settingsdesc.setDomStorageEnabled(true);
        settingsdesc.setMinimumFontSize(20);

        settingsdesc.setLoadWithOverviewMode(true);

        settingsdesc.setPluginState(WebSettings.PluginState.ON);

        settingsdesc.setBuiltInZoomControls(false);
        settingsdesc.setDisplayZoomControls(false);
        settingsdesc.setUseWideViewPort(true);
        settingsdesc.setLoadWithOverviewMode(true);
        web_videodesc.setVerticalScrollBarEnabled(false);

      //  url = changedHeaderHtml(getIntent().getStringExtra("videoUrl"));
        url = getIntent().getStringExtra("videoUrl");
        //url = changedHeaderHtml("<body bgcolor=\"#000000\"><iframe src="+url+" width=\"640\" height=\"360\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>");
        url = changedHeaderHtml(url);
       // url=""
        strShareUrl=getIntent().getStringExtra("share_url");
        webView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
        try {
         //   Log.e("errorwebdesc","  " +htmlTextWeb());
            web_videodesc.loadUrl(getIntent().getExtras().getString("desc", ""));
      //      web_videodesc.loadData(htmlTextWeb(),"text/html", "UTF-8");
        }catch (Exception e){
            Log.e("errorwebdesc",e.toString() +"  " +htmlTextWeb());
        }
      //  String frameVideo = changedHeaderHtml(url);

        String s = "<iframe src=\"";
//        int ix = frameVideo.indexOf(s) + s.length();
//        strShareUrl = frameVideo.substring(ix, frameVideo.indexOf("\"", ix + 1));
        // frameVideo.substring(startIndex,endIndex-3);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public static String changedHeaderHtml(String strUrl) {
//        String yourData = "<div id='made-in-ny'></div>\n" +
//                "\n" +
//                "<script src='https://player.vimeo.com/api/player.js'></script>\n" +
//                "<script>\n" +
//                "    var options = {\n" +
//                "        id: 59777392,\n" +
//                "        width: 540,\n" +
//                "        loop: true\n" +
//                "    };\n" +
//                "\n" +
//                "    var player = new Vimeo.Player('made-in-ny', options);\n" +
//                "\n" +
//                "    player.setVolume(0);\n" +
//                "\n" +
//                "    player.on('play', function() {\n" +
//                "        console.log('played the video!');\n" +
//                "    });\n" +
//                "</script>";
       return "<html><head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body bgcolor=\"#000000\" marginheight=\"0\" marginwidth=\"0\"><iframe src="+strUrl+" width=\"640\" height=\"320\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen marginheight=\"0\" marginwidth=\"0\"></iframe></body></html>";
        // return "<html><head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>" + strUrl + "</body></html>";
    }
public String htmlTextWeb()
{
    String desc="";
    if(getIntent().hasExtra("desc")){
        desc  =getIntent().getExtras().getString("desc", "");
        Log.e("desc",desc);
    }
   return desc;//"<html><head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head><body bgcolor=\"#ffffff\" marginheight=\"0\" marginwidth=\"0\">"+desc+"</body></html>";
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

  /*  @Override
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
    }*/
@Override
public void onBackPressed() {


        if (inCustomView()) {
            hideCustomView();

        }

       else if ((mCustomView == null) && webView.canGoBack()) {
            webView.goBack();

        }
    else {
            super.onBackPressed();
        }
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

    @Override
    public boolean handleMessage(Message msg) {
//        if (msg.what == CLICK_ON_URL){
//            handler.removeMessages(CLICK_ON_WEBVIEW);
//            return true;
//        }
//        if (msg.what == CLICK_ON_WEBVIEW){
//            scrollview.setVisibility(View.GONE);
//            bottomLayout.setVisibility(View.GONE);
//            layout_caption.setVisibility(View.GONE);
//            web_videodesc.setVisibility(View.GONE);
//            return true;
//        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.web_details && event.getAction() == MotionEvent.ACTION_DOWN){
            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        }
        return false;
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
            scrollview.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
            layout_caption.setVisibility(View.GONE);
            web_videodesc.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(WebPlayerActivity.this);
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
            web_videodesc.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.VISIBLE);
            layout_caption.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
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
            handler.sendEmptyMessage(CLICK_ON_URL);
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
    public void showPasswordDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.password_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        TextView title_video=(TextView)dialogView.findViewById(R.id.title_video);
        Button btn_login=(Button)dialogView.findViewById(R.id.btn_login);
        Button btn_cancel=(Button)dialogView.findViewById(R.id.btn_cancel);
        final EditText input_password = (EditText) dialogView.findViewById(R.id.input_password);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      //  title_video.setText(mVideoData.getVideoName());
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_password.getText().toString().length()==0)
                {
                    Toast.makeText(WebPlayerActivity.this,"Please enter Password",Toast.LENGTH_LONG).show();
                }
                else if(!input_password.getText().toString().equals(mVideoData.getPrivate_password()))
                {
                    Toast.makeText(WebPlayerActivity.this,"Please enter valid Password",Toast.LENGTH_LONG).show();
                }
                else
                    {
                        alertDialog.dismiss();
                    }
            }
        });
        alertDialog =  dialogBuilder.create();
        //dialogBuilder.setTitle("Custom dialog");
        //dialogBuilder.setMessage("Enter text below");
//        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //do something with edt.getText().toString();
//            }
//        });
//        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //pass
//            }
//        });

        alertDialog.show();
    }
}
