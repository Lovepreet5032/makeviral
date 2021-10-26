package com.prouman.member_all;

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
import android.widget.LinearLayout;
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
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.adapter.VideoViewAdapter;
import com.prouman.model.VideoModel;
import com.prouman.view.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemSecVideoActivity extends AppCompatActivity {
    private RecyclerView mVideoRecyclerView;
    private List<VideoModel> mVideos;
    private VideoViewAdapter videoViewAdapter;
    private static final String catID = "video";
    EmptyListAdapter emptyListAdapter;
    private ProgressDialog progressDialog=null;
    String mCatID,uproID;
    private RelativeLayout topLayout;
    private RelativeLayout backToGuest;
    ImageView backBtn;
    TextView title;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        LinearLayout layout_main=(LinearLayout)findViewById(R.id.layout_main);
      //  SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        if(sharedPreferences.getString(PrefrencesConstant.hash,"").equalsIgnoreCase("")){
            layout_main.setBackground(getResources().getDrawable(R.drawable.bg_guest));
        }
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        backToGuest = (RelativeLayout)findViewById(R.id.layout_back_to_guest);
        topLayout = (RelativeLayout)findViewById(R.id.layout_top);
        mVideoRecyclerView = (RecyclerView)findViewById(R.id.video_recycler_view);
        backBtn =(ImageView)findViewById(R.id.layout_logo);
        title=(TextView)findViewById(R.id.title);
        title.setText(AppConstant.videoscreentitle);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
             finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            mCatID = extras.getString("catID");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
        }
        mVideos = new ArrayList<>();
        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //handler = new DBHandler(getActivity());


        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL_LIST);
        mVideoRecyclerView.addItemDecoration(itemDecoration);
        getVideo();

        // updateUI();
    }



    private void updateUI() {
        //mVideos = handler.getAllVideo();



        if (mVideos.equals("null") || mVideos.size() == 0){
            emptyListAdapter= new EmptyListAdapter();
            mVideoRecyclerView.setAdapter(emptyListAdapter);
        }
        {
            videoViewAdapter = new VideoViewAdapter(mVideos, this);

            mVideoRecyclerView.setAdapter(videoViewAdapter);
            videoViewAdapter.notifyDataSetChanged();
        }



    }

    private void getVideo() {
        progressDialog = new ProgressDialog(MemSecVideoActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.Video_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(progressDialog!=null){progressDialog.dismiss();}
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                       /* JSONArray jsonArray = jsonObject.getJSONArray("category");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject innerObj = jsonArray.getJSONObject(i);
                            catID = innerObj.getString("id");
                            //name = innerObj.getString("name");


                        }*/
                        JSONArray videoArray = jsonObject.getJSONArray("videos");
                        for (int j = 0; j < videoArray.length(); j++) {
                            VideoModel model = new VideoModel();
                            JSONObject videoObj = videoArray.getJSONObject(j);
                            String vId = videoObj.getString("id");
                            String vTitle = videoObj.getString("title");
                            String vDesc = videoObj.getString("description");
                            String vSource = videoObj.getString("source");
                            String vThumb = videoObj.getString("thumbnail");
                            //String private = videoObj.getString("private");
                            //src=\"https://player.vimeo.com/video/165380453?title=0&byline=0&portrait=0\"
                            //  String result = vSource.substring(vSource.indexOf('h'), vSource.indexOf("/"));
                            String catVID = videoObj.getString("category");
                            model.setCategory(catVID);
                            model.setId(vId);
                            model.setTitle(vTitle);
                            model.setSource(vSource);
                            model.setDescription(vDesc);
                            model.setThumbnail(vThumb);
                            if (mCatID.equals(catVID)) {

                                mVideos.add(model);
                                if(progressDialog!=null){
                                progressDialog.hide();}
                            }

                        }

                    }
                    if (mVideos.equals("null") || mVideos.size() == 0){
                        if(progressDialog!=null){
                        progressDialog.hide();}
                        emptyListAdapter= new EmptyListAdapter("Empty View");
                        mVideoRecyclerView.setAdapter(emptyListAdapter);
                    }
                    {
                        videoViewAdapter = new VideoViewAdapter(mVideos, getApplicationContext());

                        mVideoRecyclerView.setAdapter(videoViewAdapter);
                        videoViewAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null){progressDialog.dismiss();}
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
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

