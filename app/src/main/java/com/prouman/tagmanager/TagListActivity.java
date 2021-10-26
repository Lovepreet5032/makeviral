package com.prouman.tagmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ImageView;

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
import com.prouman.app.Config;
import com.prouman.notification.ComposeNotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagListActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyler_notification;
    private static final String ARG_SECTION_NUMBER = "section_number";
    ProgressDialog progressDialog;
    String uproID, hash;
    LinearLayoutManager linearLayoutManager;
    boolean isLoading = false;
    boolean isLastPage=false;
    int totalNumberPages=0;
    int currentPage=1;
    int total_page=0;
    ImageView backBtn;
   // Button btn_composenotification;
    List<TagDataPojo.TagDataObject> tagDataObjectList;
   // List<NotifcationdataObj> tphoneBookList;
   TagsListDataAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);
        progressDialog = new ProgressDialog(this);
        tagDataObjectList = new ArrayList<>();
        recyler_notification = (RecyclerView) findViewById(R.id.recyler_notification);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        backBtn =(ImageView)findViewById(R.id.layout_logo);
        recyler_notification.addOnScrollListener(recyclerViewOnScrollListener);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyler_notification.setLayoutManager(linearLayoutManager);
        adapter = new TagsListDataAdapter(tagDataObjectList, this);
        recyler_notification.setAdapter(adapter);
        //   adapter = new NotificationListAdapter(phoneBookList, getActivity());
        // recyler_notification.setAdapter(adapter);
        getNotification(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        backBtn.setOnClickListener(this);
      //  btn_composenotification=(Button)findViewById(R.id.btn_composenotification);
        //btn_composenotification.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_composenotification:
                Intent intent=new Intent(TagListActivity.this,ComposeNotification.class);
                startActivity(intent);
                break;
            case R.id.layout_logo:
                finish();
                break;
        }
    }
    void refreshItems() {
        isLoading = false;
        isLastPage=false;
        totalNumberPages=0;
        currentPage=1;
        total_page=0;
        //  phoneBookList.clear();
        tagDataObjectList.clear();
        adapter.notifyDataSetChanged();
        getNotification(false);
    }


    private void getNotification(final boolean showLoading) {

        progressDialog.setMessage("Loading");
        if(showLoading){
            if(progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.show();}}
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GETTAGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if(!showLoading)
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                   Gson gson=new Gson();
                    TagDataPojo tagDataPojo=gson.fromJson(response,TagDataPojo.class);
                    currentPage=currentPage+1;
                    if (null !=tagDataPojo && tagDataPojo.getSuccess().equalsIgnoreCase("true")) {

                        tagDataObjectList.addAll(tagDataPojo.getData());

                        }
                        currentPage=tagDataPojo.getPaging().getPage()+1;
                        total_page=tagDataPojo.getPaging().getTotalPage();
//                        int record_from=Integer.parseInt(jsonObject1.getString("record_from"));
//                        int record_to=Integer.parseInt(jsonObject1.getString("record_to"));
//                        int total_record=Integer.parseInt(jsonObject1.getString("total_record"));
                        if (currentPage <= total_page) {
                            //   videosAdapter.addFooter();
                        } else {
                            isLastPage = true;
                        }


//                    NotificationListAdapter adapter = new NotificationListAdapter(phoneBookList, getActivity());
//                    recyler_notification.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//                    videoViewAdapter = new NewVideoAdapter(mVideos, PrivateVideoActivity.this);
//                    mVideoRecyclerView.setAdapter(videoViewAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null) {
                    progressDialog.hide();
                }
                if(!showLoading)
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                params.put("reg_id",regId);
                params.put("page",String.valueOf(currentPage));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TagListActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) > totalItemCount
                        && firstVisibleItemPosition >= 0
                        && total_page >= totalNumberPages) {
                    getNotification(true);
                }
            }
        }
    };

}


