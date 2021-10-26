package com.prouman.omspages;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.prouman.Util.IntentConstant;
import com.prouman.Util.PrefrencesConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.prouman.Util.ConfigURL.GETPAGES;

public class PageDetail extends AppCompatActivity implements View.OnClickListener{
    ImageView backBtn;
    TextView title;
    RecyclerView recycler_pagedetail;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    PagesListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);
        sharedPreferences =this.getSharedPreferences("MyPref",0);
        backBtn =(ImageView)findViewById(R.id.layout_logo);
        title =(TextView) findViewById(R.id.title);
        recycler_pagedetail=(RecyclerView)findViewById(R.id.recycler_pagedetail);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recycler_pagedetail.setLayoutManager(layoutManager);
        title.setText(getIntent().getExtras().getString("tittle",""));
        backBtn.setOnClickListener(this);
        getPagesDetailList();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_logo:
                finish();
                break;
            case R.id.link_pages:

                break;
        }
    }
    public void getPagesDetailList()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        String url=GETPAGES;
     //   String strtitle=getIntent().getExtras().getString("tittle");
//         if(strtitle.equalsIgnoreCase(getResources().getString(R.string.link_pages)))
//            {
//                url=ConfigURL.LINK_PAGES;
//            }
//       else if(strtitle.equalsIgnoreCase(getResources().getString(R.string.capture_pages)))
//        {
//            url=ConfigURL.CAPTURE_PAGES;
//        }
//        else if(strtitle.equalsIgnoreCase(getResources().getString(R.string.video_capture_pages)))
//        {
//            url=ConfigURL.VIDEO_CAPTURE_PAGES;
//        }
//       else if(strtitle.equalsIgnoreCase(getResources().getString(R.string.video_pages)))
//        {
//            url=ConfigURL.VIDEO_LINK_PAGES;
//        }
//       else
//        {
//            url=ConfigURL.OMS_PAGES;
//        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(progressDialog!=null){ progressDialog.dismiss();}
                try {
                    response = URLDecoder.decode(URLEncoder.encode(response, "iso-8859-1"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson=new Gson();
                PagesListDataobject dataobject=gson.fromJson(response,PagesListDataobject.class);
                if (dataobject.getSuccess().equalsIgnoreCase("true")) {
                    adapter = new PagesListAdapter(dataobject.getPages(), PageDetail.this);
                    recycler_pagedetail.setAdapter(adapter);
                }
                if(progressDialog!=null){
                    progressDialog.hide();}



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
                params.put("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null));
                params.put("hash", sharedPreferences.getString(PrefrencesConstant.hash,null));
                params.put("section_id", getIntent().getExtras().getString(IntentConstant.SECTION_ID));
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
