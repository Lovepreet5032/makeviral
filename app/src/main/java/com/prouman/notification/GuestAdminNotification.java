package com.prouman.notification;

import android.app.ProgressDialog;
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
import com.prouman.test_db.ContactDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by om on 3/2/2017.
 */
public class GuestAdminNotification extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
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
    public GuestAdminNotification() {
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
    List<NotifcationdataObj> phoneBookList;
    List<NotifcationdataObj> tphoneBookList;
    ContactDbHandler db;
    NotificationListAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gnotification_list_fragment, container, false);
        db = new ContactDbHandler(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        phoneBookList = new ArrayList<>();
        tphoneBookList=db.getAllNotification();
//        if (phoneBookList.size() > 0) {
//           // Toast.makeText(getActivity(), phoneBookList.get(1).getStr_description(), Toast.LENGTH_LONG).show();
//        }
        recyler_notification = (RecyclerView) rootView.findViewById(R.id.recyler_notification);
        mSwipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        recyler_notification.addOnScrollListener(recyclerViewOnScrollListener);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", 0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyler_notification.setLayoutManager(linearLayoutManager);
        adapter = new NotificationListAdapter(phoneBookList, getActivity());
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
       // phoneBookList.clear();
        phoneBookList.clear();
        adapter.notifyDataSetChanged();
        getNotification(false);
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        phoneBookList = db.getAllNotification();
//        adapter = new NotificationListAdapter(phoneBookList, getActivity());
//        recyler_notification.setAdapter(adapter);
//    }

    private void getNotification(final boolean showLoading) {

        progressDialog.setMessage("Loading");
        if(showLoading){
            if(progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.show();}}
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.getAdminNotification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if(!showLoading)
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                  //  phoneBookList.clear();
                }
                //  ArrayList<NotifcationdataObj> notifcationdataObjList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    currentPage=currentPage+1;
                    if (success) {
                      //  phoneBookList.clear();
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
                            boolean flag=false;
                            int pos=0;
                            String readStatus="0";
                            for(int i=0;i<tphoneBookList.size();i++) {
                                if(tphoneBookList.get(i).getNotification_id()==Integer.parseInt(videoObj.getString("id")))
                                {
                                    flag=true;
                                    pos=i;
                                    readStatus=tphoneBookList.get(i).getStr_isread();
                                    return;
                                }
                            }
                            if(flag)
                            {
                                model.setNotification_id(Integer.parseInt(videoObj.getString("id")));
                                model.setStr_username(videoObj.getString("upro_name"));
                                model.setStr_fromuser(videoObj.getString("upro_id"));
                                model.setStr_date(videoObj.getString("created_date"));
                                model.setStr_userimage(videoObj.getString("upro_photo"));
                                model.setStr_description(videoObj.getString("message"));
                                model.setStr_tittle(videoObj.getString("title"));
//                                model.setStr_isread(readStatus);
                                model.setStr_isread(videoObj.getString("seen_status"));
                                phoneBookList.add(model);
                            }
                            else
                            {
                                model.setNotification_id(Integer.parseInt(videoObj.getString("id")));
                                model.setStr_username(videoObj.getString("upro_name"));
                                model.setStr_fromuser(videoObj.getString("upro_id"));
                                model.setStr_date(videoObj.getString("created_date"));
                                model.setStr_userimage(videoObj.getString("upro_photo"));
                                model.setStr_description(videoObj.getString("message"));
                                model.setStr_tittle(videoObj.getString("title"));
                                //model.setStr_isread("0");
                                model.setStr_isread(videoObj.getString("seen_status"));
                                phoneBookList.add(model);
                            }
                           /* else
                            {
                                progressDialog.hide();
                                Toast.makeText(getContext(),"No video Found",Toast.LENGTH_SHORT).show();
                            }*/
                            //src=\"https://player.vimeo.com/video/165380453?title=0&byline=0&portrait=0\"
                            //  String result = vSource.substring(vSource.indexOf('h'), vSource.indexOf("/"));


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
                    }

//                    NotificationListAdapter adapter = new NotificationListAdapter(phoneBookList, getActivity());
//                    recyler_notification.setAdapter(adapter);
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
             //   params.put("hash", hash);
                params.put("reg_id",regId);
                params.put("page",String.valueOf(currentPage));
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

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_composenotification:
//                Intent intent = new Intent(getActivity(), ComposeNotification.class);
//                startActivity(intent);
//                break;
//        }
//    }

}



