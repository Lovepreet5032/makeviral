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
import com.prouman.Util.PrefrencesConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.prouman.Util.ConfigURL.GETPAGESECTION;

public class PagesMainActivity extends AppCompatActivity  {
    ImageView backBtn;
    TextView title, link_pages, capture_pages, video_capture_pages, video_pages, oms_pages;
    RecyclerView recyler_pages;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    PageMainListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pages_main_activity);
        sharedPreferences =this.getSharedPreferences("MyPref",0);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyler_pages = (RecyclerView) findViewById(R.id.recyler_pages);
        recyler_pages.setLayoutManager(manager);
        backBtn = (ImageView) findViewById(R.id.layout_logo);
        getPagesDetailList();
        //link_pages = (TextView) findViewById(R.id.link_pages);
        //capture_pages = (TextView) findViewById(R.id.capture_pages);
        //video_capture_pages = (TextView) findViewById(R.id.video_capture_pages);
        //video_pages = (TextView) findViewById(R.id.video_pages);
      //  oms_pages = (TextView) findViewById(R.id.oms_pages);
      //  link_pages.setOnClickListener(this);
        //capture_pages.setOnClickListener(this);
        //video_capture_pages.setOnClickListener(this);
        //video_pages.setOnClickListener(this);
        //oms_pages.setOnClickListener(this);
        //backBtn.setOnClickListener(this);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getPagesDetailList() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        String url = GETPAGESECTION;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                try {
                    response = URLDecoder.decode(URLEncoder.encode(response, "iso-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                PageMainListDataObject dataobject = gson.fromJson(response, PageMainListDataObject.class);
                if (dataobject.getSuccess().equalsIgnoreCase("true")) {
                    adapter = new PageMainListAdapter(dataobject.getSections(), PagesMainActivity.this);
                    recyler_pages.setAdapter(adapter);
                }
                if (progressDialog != null) {
                    progressDialog.hide();
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
                params.put("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid, null));
                params.put("hash", sharedPreferences.getString(PrefrencesConstant.hash, null));
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

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_logo:
                finish();
                break;
            case R.id.link_pages:
                startActivity(new Intent(PagesMainActivity.this,PageDetail.class).putExtra("tittle",getResources().getString(R.string.link_pages)));
                break;
            case R.id.capture_pages:
                startActivity(new Intent(PagesMainActivity.this,PageDetail.class).putExtra("tittle",getResources().getString(R.string.capture_pages)));
                break;
            case R.id.video_capture_pages:
                startActivity(new Intent(PagesMainActivity.this,PageDetail.class).putExtra("tittle",getResources().getString(R.string.video_capture_pages)));
                break;
            case R.id.video_pages:
                startActivity(new Intent(PagesMainActivity.this,PageDetail.class).putExtra("tittle",getResources().getString(R.string.video_pages)));
                break;
            case R.id.oms_pages:
                startActivity(new Intent(PagesMainActivity.this,PageDetail.class).putExtra("tittle",getResources().getString(R.string.oms_pages)));
                break;

        }
    }*/
}
