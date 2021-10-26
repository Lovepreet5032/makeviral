package com.prouman.notification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.prouman.adapter.NotificationListAdapter;
import com.prouman.app.Config;
import com.prouman.model.NotifcationdataObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by om on 2/18/2017.
 */
public class SentNotification extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    RecyclerView recyler_sentnotification;
    ProgressDialog progressDialog;
    String uproID,hash;
    Button btn_composenotification;
    LinearLayoutManager manager;
    boolean isLoading = false;
    boolean isLastPage=false;
    int totalNumberPages=0;
    int currentPage=1;
    int total_page=0;
    NotificationListAdapter adapter;
    ArrayList<NotifcationdataObj> notifcationdataObjList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public SentNotification() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
//    public static NotificationListFragment newInstance(int sectionNumber) {
//        PlaceholderFragment fragment = new PlaceholderFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sent_notification, container, false);
        progressDialog = new ProgressDialog(getActivity());
        notifcationdataObjList=new ArrayList<>();
        btn_composenotification=(Button)rootView.findViewById(R.id.btn_composenotification);
        btn_composenotification.setOnClickListener(this);
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash=sharedPreferences.getString(PrefrencesConstant.hash,null);
        recyler_sentnotification=(RecyclerView)rootView.findViewById(R.id.recyler_sentnotification);
        mSwipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        recyler_sentnotification.addOnScrollListener(recyclerViewOnScrollListener);
        manager =new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyler_sentnotification.setLayoutManager(manager);
        adapter= new NotificationListAdapter(notifcationdataObjList, getActivity());
        recyler_sentnotification.setAdapter(adapter);
        getNotification(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        return rootView;
    }
    void refreshItems() {
        // Load items
        // ...

        // Load complete
        isLoading = false;
        isLastPage=false;
        totalNumberPages=0;
        currentPage=1;
        total_page=0;
        notifcationdataObjList.clear();
        adapter.notifyDataSetChanged();
        getNotification(false);
    }
    private void getNotification(final boolean showLoading) {

        progressDialog.setMessage("Loading");
        if(showLoading){
            if(progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.show();}}
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.SentNotification_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if(progressDialog!=null){ progressDialog.dismiss();}
                if(!showLoading)
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                  //  notifcationdataObjList.clear();//=new ArrayList<>();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    currentPage=currentPage+1;
                    if (success) {
                      //  notifcationdataObjList.clear();
                       /* JSONArray jsonArray = jsonObject.getJSONArray("category");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject innerObj = jsonArray.getJSONObject(i);
                            catID = innerObj.getString("id");
                            //name = innerObj.getString("name");


                        }*/
                        JSONArray videoArray = jsonObject.getJSONArray("sent_notifications");
                        for (int j = 0; j < videoArray.length(); j++) {
                            NotifcationdataObj model = new NotifcationdataObj();
                            JSONObject videoObj = videoArray.getJSONObject(j);
                            model.setNotification_id(1);
                            model.setStr_username(videoObj.getString("upro_name"));
                            model.setStr_fromuser(videoObj.getString("upro_id"));
                            model.setStr_date(videoObj.getString("created_date"));
                            model.setStr_userimage(videoObj.getString("upro_photo"));
                            model.setStr_description(videoObj.getString("message"));
                            model.setStr_tittle(videoObj.getString("title"));
                            model.setStr_isread("1");
                            notifcationdataObjList.add(model);
                           /* else
                            {
                                progressDialog.hide();
                                Toast.makeText(getContext(),"No video Found",Toast.LENGTH_SHORT).show();
                            }*/
                            //src=\"https://player.vimeo.com/video/165380453?title=0&byline=0&portrait=0\"
                            //  String result = vSource.substring(vSource.indexOf('h'), vSource.indexOf("/"));


                        }

                    }
                    JSONObject jsonObject1=jsonObject.getJSONObject("paging");
                    currentPage=Integer.parseInt(jsonObject1.getString("page"))+1;
                    total_page=Integer.parseInt(jsonObject1.getString("total_page"));
                    int record_from=Integer.parseInt(jsonObject1.getString("record_from"));
                    int record_to=Integer.parseInt(jsonObject1.getString("record_to"));
                    int total_record=Integer.parseInt(jsonObject1.getString("total_record"));
                    if (currentPage <= total_page) {
                        //   videosAdapter.addFooter();
                    } else {
                        isLastPage = true;
                    }
                    if(progressDialog!=null){
                        progressDialog.hide();}
//                    NotificationListAdapter adapter= new NotificationListAdapter(notifcationdataObjList, getActivity());
//                    recyler_sentnotification.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//                    videoViewAdapter = new NewVideoAdapter(mVideos, PrivateVideoActivity.this);
//                    mVideoRecyclerView.setAdapter(videoViewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null){
                    progressDialog.hide();}
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
                params.put("page",String.valueOf(currentPage));
//                params.put("reg_id",regId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_composenotification:
                Intent intent=new Intent(getActivity(),ComposeNotification.class);
                startActivity(intent);
                break;
        }
    }
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = manager.getChildCount();
            int totalItemCount = manager.getItemCount();
            int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();

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
