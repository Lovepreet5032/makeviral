package com.prouman.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

import com.prouman.R;

import static com.prouman.R.id.back_btn;

/**
 * Created by dsingh on 9/5/2016.
 */
public class VPlayerActivity extends AppCompatActivity {

    String url,upro_title;
    WebView videoView;
    ProgressDialog pDialog;
    private RelativeLayout backToGuest;
    private RelativeLayout bottomLayout;
    private boolean isShowShareLayout;
    private ProgressBar loadProgress;
    private ImageView backBtn;
    private RelativeLayout shareLayout;
    private TextView title;
    private RelativeLayout topLayout;
    private WebView web;
    private OrientationEventListener myOrientationEventListener;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        Intent intent= getIntent();
         url =  intent.getStringExtra("videoUrl");
        upro_title = intent.getStringExtra("title");
      title = (TextView)findViewById(R.id.title);
       title.setText(upro_title);
       backToGuest = (RelativeLayout)findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)findViewById(R.id.layout_top));
       // loadProgress = ((ProgressBar)findViewById(R.id.));
        backBtn =(ImageView)findViewById(back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View v) {


                            finish();






                            }
        });
        bottomLayout = ((RelativeLayout)findViewById(R.id.layout_bottom));
        customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
       videoView = (WebView)findViewById(R.id.web_details);
     /*  isShowShareLayout = getIntent().getBooleanExtra("showShareUrl", false);
        if (isShowShareLayout) {
            bottomLayout.setVisibility(0);
        }*/

           /* backToGuest.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    finish();
                   // WebViewer.overridePendingTransition(2131034130, 2131034128);
                }
            });*/

       // videoView= (WebView)findViewById(R.id.web_details);

        // Create a progressbar
      /*  videoView.getSettings().setJavaScriptEnabled(true);
        videoView.getSettings().setAppCacheEnabled(true);
        videoView.getSettings().setBuiltInZoomControls(true);
        videoView.getSettings().setSaveFormData(true);
        String frameVideo = changedHeaderHtml(url);
        videoView.loadDataWithBaseURL(null, frameVideo, "text/html", "UTF-8", null);*/
        // Show progressbar
        // pDialog.show();
        // this.title.setText("" + getIntent().getStringExtra("title"));
        try {
            /*videoView.getSettings().setJavaScriptEnabled(true);
            videoView.getSettings().setAllowFileAccess(true);
            videoView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY)*/

          /*  DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int wwidth = displaymetrics.widthPixels;*/
            mWebViewClient = new myWebViewClient();
            videoView.setWebViewClient(mWebViewClient);

            mWebChromeClient = new myWebChromeClient();
            videoView.setWebChromeClient(mWebChromeClient);
            WebSettings frameSetting = videoView.getSettings();
            frameSetting.setJavaScriptEnabled(true);
            frameSetting.setAppCacheEnabled(true);
            frameSetting.setDomStorageEnabled(true);
            frameSetting.setMinimumFontSize(10);
            frameSetting.setLoadWithOverviewMode(true);
            frameSetting.setSaveFormData(true);
            frameSetting.setUseWideViewPort(true);

            videoView.setVerticalScrollBarEnabled(false);
            frameSetting.setDisplayZoomControls(true);
            //videoView.setWebChromeClient(new WebChromeClient());

            //frameSetting.getSaveFormData();

            videoView.setInitialScale(1);
            String frameVideo = changedHeaderHtml(url);
            videoView.loadDataWithBaseURL(null, frameVideo, "text/html", "UTF-8", null);
          /*  myOrientationEventListener = new OrientationEventListener(this) {
                @Override
                public void onOrientationChanged(int orientation) {

                    if (getResources().getConfiguration().orientation == 1)
                    {
                        topLayout.setVisibility(View.GONE);
                        if (isShowShareLayout) {
                            bottomLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    do
                    {
                        topLayout.setVisibility(View.VISIBLE);


                    } while (isShowShareLayout);
                    bottomLayout.setVisibility(View.GONE);
                }
            };
            if (myOrientationEventListener.canDetectOrientation()) {
                myOrientationEventListener.enable();
            }
            bottomLayout.setVisibility(View.VISIBLE);
            return;*/

        }





        //myOrientationEventListener = new OrientationEventListener(this, 3)

        // Start the MediaController
          /*videoView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            //
            videoView.setWebViewClient(new WebViewClient()
            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });*/
            /*videoView.getSettings().setJavaScriptEnabled(true);
            String frameVideo = changedHeaderHtml(videoUrl);
           videoView.loadData(url, "text/html", "utf-8");*/
        //pDialog.hide();

        catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
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
        videoView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates. 
        videoView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates. 
        if (inCustomView()) {
            hideCustomView();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && videoView.canGoBack()) {
                videoView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one 
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            videoView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(VPlayerActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates. 
            if (mCustomView == null)
                return;

            videoView.setVisibility(View.VISIBLE);
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
     /*        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
        }
*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
    public static String changedHeaderHtml(String paramString)
    {
        return "<head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>" + paramString + "</body></html>";
    }

}
