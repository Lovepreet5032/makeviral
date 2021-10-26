package com.prouman.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.prouman.R;

/**
 * Created by dsingh on 9/5/2016.
 */
public class VideoPlayerFragment extends Fragment {
    private static final String VIDEO_ID = "video_id";
    WebView videoView;
  ProgressDialog pDialog;
    private static final String videoUrl = "video";
    private static final String videoTitle = "vTitle";
    String mVideoUrl,vTitle;
    public static  VideoPlayerFragment newInstance(String str,String title)
    {
        Bundle args= new Bundle() ;
        args.putString(videoUrl,str);
        args.putString(videoTitle,title);
        VideoPlayerFragment  fragment = new VideoPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mVideoUrl = getArguments().getString(videoUrl);
        vTitle= getArguments().getString(videoTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.video_player,container,false);
        videoView= (WebView)rootView.findViewById(R.id.web_details);
        // Create a progressbar
        pDialog = new ProgressDialog(getContext());
        // Set progressbar title
        pDialog.setTitle(vTitle);
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
       // pDialog.show();
       // this.title.setText("" + getIntent().getStringExtra("title"));
        try {
            videoView.getSettings().setJavaScriptEnabled(true);
            videoView.getSettings().setAllowFileAccess(true);
            videoView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

            DisplayMetrics displaymetrics = new DisplayMetrics();
           getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int wwidth = displaymetrics.widthPixels;

            Log.e("h & w",wwidth+"-"+height);

            //String data_html = "<!DOCTYPE HTML> <html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:og=\"http://opengraphprotocol.org/schema/\" xmlns:fb=\"http://www.facebook.com/2008/fbml\"> <head></head> <body style=\"margin:0 0 0 0; padding:0 0 0 0;\"> <iframe width='"+wwidth+"' height='"+height+"'"\" frameborder=\"0\"></iframe> </body> </html> ";

            videoView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }
            });

            ///videoView.loadDataWithBaseURL("http://vimeo.com", data_html, "text/html", "UTF-8", null);
           /* WebSettings frameSetting = this.videoView.getSettings();
        //frameSetting.setJavaScriptEnabled(true);
     *//*   frameSetting.setDomStorageEnabled(true);
        frameSetting.setMinimumFontSize(18);
        frameSetting.setLoadWithOverviewMode(true);
        frameSetting.setUseWideViewPort(true);
        frameSetting.setBuiltInZoomControls(true);
        frameSetting.setDisplayZoomControls(false);*//*
       videoView.setWebChromeClient(new WebChromeClient());
        //String frameVideo = changedHeaderHtml(videoUrl);
       // videoView.loadDataWithBaseURL(null, frameVideo, "text/html", "UTF-8", null);
        //myOrientationEventListener = new OrientationEventListener(this, 3)

            // Start the MediaController
          videoView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            //videoView.setInitialScale(1);
            videoView.setWebViewClient(new WebViewClient()
            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }
            });
            videoView.getSettings().setJavaScriptEnabled(true);
            String frameVideo = changedHeaderHtml(videoUrl);
           videoView.loadData(mVideoUrl, "text/html", "utf-8");*/
            pDialog.hide();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

//        videoView.requestFocus();



        return rootView;
    }
    public static String changedHeaderHtml(String paramString)
    {
        return "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width,height- user-scalable=no\" />" + paramString + "" +
                "<iframe width='\"+wwidth+\"' height='\"+height+\"\" frameborder=\"0\"></head>"+
                "</body>" +
                "</html>";
    }
}
