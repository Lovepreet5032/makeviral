package com.prouman.videonewscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.member_all.EmptyListAdapter;
import com.prouman.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoHub extends AppCompatActivity {
    private RecyclerView mVideoRecyclerView;
    private List<VideoData> mVideos;
    //  private PrivateVideoAdapter videoViewAdapter;
    private static final String catID = "video";
    EmptyListAdapter emptyListAdapter;
    private ProgressDialog progressDialog=null;
    String mCatID,uproID,hash;
    VideoHubAdapter videoViewAdapter;
    private ImageView backBtn;
    private RelativeLayout topLayout;
    private RelativeLayout backToGuest;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_hub);
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash=sharedPreferences.getString(PrefrencesConstant.hash,null);//"$2a$08$9TuKz4cYGwFUQYNMSpQ6S.7EU5XQRK9hgPvNLUqY4GeyUYyefEzZa"; //sharedPreferences.getString(PrefrencesConstant.hash,null);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            mCatID = extras.getString("catID");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
        }

        backToGuest = (RelativeLayout)findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)findViewById(R.id.layout_top));
        backBtn =(ImageView)findViewById(R.id.layout_logo);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                finish();


            }
        });
        mVideoRecyclerView = (RecyclerView)findViewById(R.id.video_recycler_view);

        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //handler = new DBHandler(getActivity());


        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mVideoRecyclerView.addItemDecoration(itemDecoration);
        updateUI();

       /* RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mVideoRecyclerView.setItemAnimator(new DefaultItemAnimator());*/
    }


    private void updateUI() {
        //mVideos = handler.getAllVideo();

        mVideos = new ArrayList<>();
      /*  if (mVideos.equals("null") || mVideos.size() == 0){
            mVideoRecyclerView.setAdapter(emptyListAdapter);
        }else {*/
        //  videoViewAdapter = new PrivateVideoAdapter(mVideos, this);

        getVideo();
//        videoViewAdapter = new NewVideoAdapter(mVideos, this);
//        mVideoRecyclerView.setAdapter(videoViewAdapter);




    }

    private void getVideo() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.Video_Hub_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(progressDialog!=null){ progressDialog.dismiss();}
               response= AppConstant.encodedResponse(response);
                Gson gson=new Gson();
                VideosDataObject dataobject=gson.fromJson(response,VideosDataObject.class);
                if (dataobject.getSuccess().equalsIgnoreCase("true")) {
                    mVideos.addAll(dataobject.getVideos());
                }
                if(progressDialog!=null){
                    progressDialog.hide();}
                videoViewAdapter = new VideoHubAdapter(mVideos, VideoHub.this);
                mVideoRecyclerView.setAdapter(videoViewAdapter);


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
                params.put("upro_id", uproID);
                params.put("directory", getIntent().getStringExtra("videoId"));
                params.put("hash", hash);
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

