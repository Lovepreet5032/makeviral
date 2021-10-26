package com.prouman.contactmanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactMangerSearch extends AppCompatActivity {
    RecyclerView recyler_notification;
    ProgressDialog progressDialog;
    String uproID, hash;
    LinearLayoutManager linearLayoutManager;
    List<ContactManagerDataObject.ContactManagerListData> contactManagerDataObjectList;
   // ContactManagerAdapter adapter;
    private SearchView mSearchView;
    ImageView layout_logo;
    boolean isLoading = false;
    boolean isLastPage=false;
    int totalNumberPages=0;
    int currentPage=1;
    int total_page=0;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_manger_search);
        layout_logo=(ImageView)findViewById(R.id.layout_logo);
        mSearchView = (SearchView) findViewById(R.id.search);

        contactManagerDataObjectList=new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        progressDialog = new ProgressDialog(this);
        recyler_notification = (RecyclerView) findViewById(R.id.recyler_notification);
        linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyler_notification.setLayoutManager(linearLayoutManager);
    //    adapter = new ContactManagerAdapter(contactManagerDataObjectList, this);
    //    recyler_notification.setAdapter(adapter);
     //   getNotification(true);
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(final String query) {
              query.toLowerCase();
               // adapter.getFilter().filter(query);
                handler.removeCallbacksAndMessages(null);
                if(null != query && query.length()>0) {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                          getNotification(true,query);
                        }
                    }, 600);

                }
                return false;
                 }
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        };
        mSearchView.setOnQueryTextListener(listener);
        mSearchView.setQuery(getIntent().getExtras().getString("s",""),true);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.clearFocus();
        mSearchView.requestFocusFromTouch();
        layout_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getNotification(final boolean showLoading, final String qwery) {
        contactManagerDataObjectList.clear();
        progressDialog.setMessage("Loading");
        if(showLoading){
            if(progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.show();}}
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
       // final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GETCONTACT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if(!showLoading)
                {
                    //mSwipeRefreshLayout.setRefreshing(false);
                    //  phoneBookList.clear();//=new ArrayList<>();
                }
                //  ArrayList<NotifcationdataObj> notifcationdataObjList = new ArrayList<>();
                try {
                    Gson gson=new Gson();
                    ContactManagerDataObject contactManagerDataObject=  gson.fromJson(response,ContactManagerDataObject.class);

                 //   currentPage=currentPage+1;
                    if (contactManagerDataObject.getSuccess().equalsIgnoreCase("true")) {

                        currentPage=contactManagerDataObject.getPaging().getPage()+1;
                        total_page=contactManagerDataObject.getPaging().getTotalPage();//Integer.parseInt(jsonObject1.getString("total_page"));
                        int record_from=contactManagerDataObject.getPaging().getRecordFrom();//Integer.parseInt(jsonObject1.getString("record_from"));
                        int record_to=contactManagerDataObject.getPaging().getRecordTo();
                        int total_record=contactManagerDataObject.getPaging().getTotalRecord();
//                        if (currentPage <= total_page) {
//                            //   videosAdapter.addFooter();
//                        } else {
//                            isLastPage = true;
//                        }
                    }
                    contactManagerDataObjectList.addAll(contactManagerDataObject.getData());
//                    NotificationListAdapter adapter = new NotificationListAdapter(phoneBookList, getActivity());
//                    recyler_notification.setAdapter(adapter);
              //      adapter.notifyDataSetChanged();
//                    videoViewAdapter = new NewVideoAdapter(mVideos, PrivateVideoActivity.this);
//                    mVideoRecyclerView.setAdapter(videoViewAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null) {
                    progressDialog.hide();
                }
                if(!showLoading)
                {
                  //  mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                params.put("search[keyword]=",qwery);
                // params.put("reg_id",regId);
            //    params.put("page",String.valueOf(currentPage));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag("req");
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
                    getNotification(true,mSearchView.getQuery().toString());
                }
            }
        }
    };

}
