package com.prouman.videonewscreen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.prouman.R;

public class InappWebview extends AppCompatActivity {
    WebView webview;
    private ImageView backBtn;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp_webview);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        webview=(WebView)findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        title=(TextView)findViewById(R.id.title);
        title.setText(getIntent().getExtras().getString("title",""));
        webview.loadUrl(getIntent().getExtras().getString("url",""));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                    finish();
                }
        });
    }
}
