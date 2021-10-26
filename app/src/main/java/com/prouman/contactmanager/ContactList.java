package com.prouman.contactmanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.prouman.Util.IntentConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.app.Config;
import com.prouman.common.SucessDataObject;
import com.prouman.mesg_data.MessageListActivity;
import com.prouman.sms_template.SmsActivity;
import com.prouman.test_db.ContactTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactList extends AppCompatActivity implements ContactAdapterInterface,SearchTagRemoveListener{
    RecyclerView recyler_notification;
    ProgressDialog progressDialog;
    String uproID, hash;
    boolean isLoading = false;
    boolean isLastPage=false;
    int totalNumberPages=0;
    int currentPage=1;
    int total_page=0;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayoutManager linearLayoutManager;
    List<ContactManagerDataObject.ContactManagerListData> contactManagerDataObjectList;
    ArrayList<ContactManagerDataObject.ContactManagerListData> selectedcontactManagerListData=new ArrayList<>();
    ContactManagerAdapter adapter;
    private SearchView mSearchView;
    ImageView layout_logo;
    String query="";
    HashMap<String,String> queryMap=new HashMap<>();
    Toolbar toolbar;
    boolean advanceSearch=false;
    final Handler handler = new Handler();
    ArrayList<String> arrayListCompaignId=new ArrayList<>();
    TagDataObject contactManagerDataObject=null;
    AlertDialog alertDialog=null;
    boolean isExport=false;
    RecyclerView recyler_searchtag;
    SearchTagAdapter searchTagAdapter;
    ArrayList<SearchTagLocalObject> searchTagLocalObjects=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        layout_logo=(ImageView)findViewById(R.id.layout_logo);
        mSearchView = (SearchView) findViewById(R.id.search);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contactManagerDataObjectList=new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        progressDialog = new ProgressDialog(this);
        recyler_notification = (RecyclerView) findViewById(R.id.recyler_notification);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        recyler_notification.addOnScrollListener(recyclerViewOnScrollListener);
        linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyler_notification.setLayoutManager(linearLayoutManager);
        adapter = new ContactManagerAdapter(contactManagerDataObjectList, this,this);
        recyler_notification.setAdapter(adapter);
        recyler_searchtag=(RecyclerView)findViewById(R.id.recyler_searchtag);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyler_searchtag.setLayoutManager(layoutManager);
        recyler_searchtag.setVisibility(View.GONE);
        if(null != getIntent() && null != getIntent().getExtras()){
            advanceSearch=true;
            queryMap= (HashMap<String, String>) getIntent().getExtras().getSerializable("query");
            searchTagLocalObjects=getIntent().getExtras().getParcelableArrayList("querylist");
            recyler_searchtag.setVisibility(View.VISIBLE);
            searchTagAdapter= new SearchTagAdapter(ContactList.this,searchTagLocalObjects,ContactList.this);
            recyler_searchtag.setAdapter(searchTagAdapter);
        }
        getNotification(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                query = query.toLowerCase();
                ContactList.this.query=query;
                currentPage=0;
                //  mSearchView.setQuery("",false);


                    // startActivity(new Intent(ContactList.this, ContactMangerSearch.class).putExtra("s", query));
                    handler.removeCallbacksAndMessages(null);
                    if (null != query && !"".equalsIgnoreCase(query)) {

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                getNotification(true);
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
        layout_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void refreshItems() {
        isLoading = false;
        isLastPage=false;
        totalNumberPages=0;
        currentPage=1;
        total_page=0;
        //  phoneBookList.clear();
        contactManagerDataObjectList.clear();
        adapter.notifyDataSetChanged();
        getNotification(false);
    }
    private void getNotification(final boolean showLoading) {
        if(currentPage==0){contactManagerDataObjectList.clear();}
        progressDialog.setMessage("Loading");
        if(showLoading){
            if(progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.show();}}
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GETCONTACT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if(!showLoading)
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                    //  phoneBookList.clear();//=new ArrayList<>();
                }
                //  ArrayList<NotifcationdataObj> notifcationdataObjList = new ArrayList<>();
                try {
                    Gson gson=new Gson();
                    ContactManagerDataObject contactManagerDataObject=  gson.fromJson(response,ContactManagerDataObject.class);

                    currentPage=currentPage+1;
                    if (contactManagerDataObject.getSuccess().equalsIgnoreCase("true")) {

                        currentPage=contactManagerDataObject.getPaging().getPage()+1;
                        total_page=contactManagerDataObject.getPaging().getTotalPage();//Integer.parseInt(jsonObject1.getString("total_page"));
                        int record_from=contactManagerDataObject.getPaging().getRecordFrom();//Integer.parseInt(jsonObject1.getString("record_from"));
                        int record_to=contactManagerDataObject.getPaging().getRecordTo();
                        int total_record=contactManagerDataObject.getPaging().getTotalRecord();
                        if (currentPage <= total_page) {
                            //  videosAdapter.addFooter();
                        } else {
                            isLastPage = true;
                        }
                        if(isExport)
                        {
                            isExport=false;
                            isLastPage = true;
                        }
                    }
                    contactManagerDataObjectList.addAll(contactManagerDataObject.getData());
//                    NotificationListAdapter adapter = new NotificationListAdapter(phoneBookList, getActivity());
//                    recyler_notification.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
               // params.put("reg_id",regId);
                if(!isExport){
                if(queryMap.size()==0) {
                    params.put("search[keyword]=", query);
                }
                else{
                    advanceSearch=false;
                    params.putAll(queryMap);}
                }
                else{
                   params.put("export","1");
                    currentPage=0;
                }
                params.put("page",String.valueOf(currentPage));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        mSearchView.setQuery("",false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contactmanager, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intentSearch =new Intent(ContactList.this,AdvanceSearch.class);
                intentSearch.putParcelableArrayListExtra("querylist",searchTagLocalObjects);
                startActivityForResult(intentSearch,109);
                return true;
            case R.id.action_addcompign:
                selectedcontactManagerListData.clear();
              //  if(null == TagDataObject.getInstance().getData()){
                for (int i=0;i<contactManagerDataObjectList.size();i++) {
                    if(contactManagerDataObjectList.get(i).isSelected()){
                        selectedcontactManagerListData.add(contactManagerDataObjectList.get(i));
                    }
                }
                if(selectedcontactManagerListData.size()>0) {
                    Intent intent=new Intent(ContactList.this,AddtoCampign.class);
                    intent.putParcelableArrayListExtra(IntentConstant.CONTACTLIST,selectedcontactManagerListData);
                    startActivity(intent);
//                  if(null== contactManagerDataObject) {
//                      getTagData(1);
//                  }
//                  else{
//                      addtocampignDailog(contactManagerDataObject,1);
//                  }
                }
                else{
                  Toast.makeText(ContactList.this,"Please select one contact to continue",Toast.LENGTH_LONG).show();
                }
//                }
//                else{addtocampignDailog();}
                return true;
            case R.id.action_addtogroup:
                selectedcontactManagerListData.clear();
                for (int i=0;i<contactManagerDataObjectList.size();i++) {
                    if(contactManagerDataObjectList.get(i).isSelected()){
                        selectedcontactManagerListData.add(contactManagerDataObjectList.get(i));
                    }
                }
                if(selectedcontactManagerListData.size()>0) {
                    Intent intent=new Intent(ContactList.this,AddToGroup.class);
                    intent.putParcelableArrayListExtra(IntentConstant.CONTACTLIST,selectedcontactManagerListData);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ContactList.this,"Please select one contact to continue",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_broadcast:
                selectedcontactManagerListData.clear();
                for (int i=0;i<contactManagerDataObjectList.size();i++) {
                    if(contactManagerDataObjectList.get(i).isSelected()){
                        selectedcontactManagerListData.add(contactManagerDataObjectList.get(i));
                    }
                }
                if(selectedcontactManagerListData.size()>0) {
                    Intent intent=new Intent(ContactList.this,AddtoBroadCast.class);
                    intent.putParcelableArrayListExtra(IntentConstant.CONTACTLIST,selectedcontactManagerListData);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ContactList.this,"Please select one contact to continue",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_sendsms:
                selectedcontactManagerListData.clear();
                ArrayList<ContactTest> numberList =new ArrayList<ContactTest>();
                boolean hasEmail=false;
                for (int i=0;i<contactManagerDataObjectList.size();i++) {
                    if(contactManagerDataObjectList.get(i).isSelected()){
                        ContactTest contactTest=new ContactTest();
                        contactTest.setPhone(contactManagerDataObjectList.get(i).getPhone());
                        contactTest.setEmail_Id(contactManagerDataObjectList.get(i).getEmail());
                        contactTest.setName(contactManagerDataObjectList.get(i).getFirstName());
                        if(null != contactManagerDataObjectList && null != contactManagerDataObjectList.get(i).getPhone() && !contactManagerDataObjectList.get(i).getPhone().equalsIgnoreCase(""))
                        {hasEmail=true;
                            numberList.add(contactTest);}
                        selectedcontactManagerListData.add(contactManagerDataObjectList.get(i));
                    }
                }
                if(selectedcontactManagerListData.size()>0) {

                if(hasEmail) {
                    Intent msgIntent = new Intent(ContactList.this, SmsActivity.class);
                    msgIntent.putParcelableArrayListExtra("phoneNumber", numberList);
                    msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(msgIntent);
                }
                else{Toast.makeText(this,"Please select contact having phone number",Toast.LENGTH_LONG).show();}
                }
                else {
                    Toast.makeText(ContactList.this,"Please select one contact to continue",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_sendemail:
                selectedcontactManagerListData.clear();
                ArrayList<ContactTest> numberListEmail =new ArrayList<ContactTest>();
                boolean hassms=false;
                for (int i=0;i<contactManagerDataObjectList.size();i++) {
                    if(contactManagerDataObjectList.get(i).isSelected()){
                        ContactTest contactTest=new ContactTest();
                        contactTest.setPhone(contactManagerDataObjectList.get(i).getPhone());
                        contactTest.setEmail_Id(contactManagerDataObjectList.get(i).getEmail());
                        contactTest.setName(contactManagerDataObjectList.get(i).getFirstName());
                        if(null != contactManagerDataObjectList && null != contactManagerDataObjectList.get(i).getEmail() && !contactManagerDataObjectList.get(i).getEmail().equalsIgnoreCase(""))
                        {hassms=true;
                            numberListEmail.add(contactTest);}
                    }
                }
                if(hassms) {
                    Intent msgIntent = new Intent(ContactList.this, MessageListActivity.class);
                    msgIntent.putParcelableArrayListExtra("phoneNumber", numberListEmail);
                    msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(msgIntent);
                }
                else{Toast.makeText(this,"Please select contact having phone number",Toast.LENGTH_LONG).show();}
                return true;
            case R.id.action_editprospects:
                selectedcontactManagerListData.clear();
                for (int i=0;i<contactManagerDataObjectList.size();i++) {
                    if(contactManagerDataObjectList.get(i).isSelected()){
                        selectedcontactManagerListData.add(contactManagerDataObjectList.get(i));
                    }
                }
                if(selectedcontactManagerListData.size()>0) {
                    if(selectedcontactManagerListData.size()==1) {
                        startActivity(new Intent(ContactList.this, EditCampaigns.class).putExtra(IntentConstant.CONTACTLIST,selectedcontactManagerListData.get(0)));
                    }
                    else{Toast.makeText(this,"Please select only one contact to edit",Toast.LENGTH_LONG).show();}
                }
                else{Toast.makeText(this,"Please select atleast one contact to continue",Toast.LENGTH_LONG).show();}
                return true;
            case R.id.action_transferprospects:
                selectedcontactManagerListData.clear();
                for (int i=0;i<contactManagerDataObjectList.size();i++) {
                    if(contactManagerDataObjectList.get(i).isSelected()){
                        selectedcontactManagerListData.add(contactManagerDataObjectList.get(i));
                    }
                }
                if(selectedcontactManagerListData.size()>0) {

                        startActivity(new Intent(ContactList.this, TransferProspects.class).putParcelableArrayListExtra(IntentConstant.CONTACTLIST,selectedcontactManagerListData));

                }
                else{Toast.makeText(this,"Please select atleast one contact to continue",Toast.LENGTH_LONG).show();}
                return true;
            case R.id.action_deleteprospects:
                selectedcontactManagerListData.clear();
                for (int i=0;i<contactManagerDataObjectList.size();i++) {
                    if(contactManagerDataObjectList.get(i).isSelected()){
                        selectedcontactManagerListData.add(contactManagerDataObjectList.get(i));
                    }
                }
                if(selectedcontactManagerListData.size()>0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ContactList.this);
                alertDialogBuilder.setMessage("Are you sure you want to delete contact");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteProspects(selectedcontactManagerListData);

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = alertDialogBuilder.create();

                alertDialog.show();
        }
        else {Toast.makeText(this,"Please select atleast one contact to continue",Toast.LENGTH_LONG).show();}
        return true;
            case R.id.action_exportcontact:
                isExport=true;
                contactManagerDataObjectList.clear();
                adapter.notifyDataSetChanged();
                getNotification(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 109) {
            if (resultCode == RESULT_OK) {
                contactManagerDataObjectList.clear();
                this.query = "";
                advanceSearch = true;
                queryMap = (HashMap<String, String>) data.getExtras().getSerializable("query");
                searchTagLocalObjects = data.getExtras().getParcelableArrayList("querylist");
//                for (Map.Entry<String, String> key: queryMap.entrySet()) {
//                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
//                    searchTagLocalObject.setKey(key.getKey());
//                    searchTagLocalObject.setValue(key.getKey().replace("search[","")+" : "+key.getValue());
//                    searchTagLocalObjects.add(searchTagLocalObject);
//                }
                recyler_searchtag.setVisibility(View.VISIBLE);
                searchTagAdapter = new SearchTagAdapter(ContactList.this, searchTagLocalObjects, ContactList.this);
                recyler_searchtag.setAdapter(searchTagAdapter);
                currentPage = 0;
                getNotification(true);
            }
        }
    }
    private void deleteProspects(final ArrayList<ContactManagerDataObject.ContactManagerListData> selectedManagerListData) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        final String uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        final  String hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        progressDialog.setMessage("Loading");

        if(progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();}
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.DELETEPROSPECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                try {
                    Gson gson=new Gson();
                    SucessDataObject sucessDataObject =  gson.fromJson(response,SucessDataObject.class);

                    //  currentPage=currentPage+1;
                    if (sucessDataObject.getSuccess().equalsIgnoreCase("true")) {
                      Toast.makeText(ContactList.this,sucessDataObject.getMessage(),Toast.LENGTH_LONG).show();
                    }
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

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                String str=null;
                for(int i=0;i<selectedManagerListData.size();i++)
                {
                    if(i==0){str=selectedManagerListData.get(i).getProId();}
                    else {str=str+","+selectedManagerListData.get(i).getProId();}
                }
                 params.put("pro_id",str);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }





    @Override
    public void updateListselection(boolean selected, int position) {
        contactManagerDataObjectList.get(position).setSelected(selected);
        //recyler_notification.stopScroll();
       // adapter.notifyItemChanged(position);
    }

    @Override
    public void removeText(String text) {
        queryMap.remove(text);
        currentPage=0;
        contactManagerDataObjectList.clear();
        adapter.notifyDataSetChanged();
        for (int i=0;i<searchTagLocalObjects.size();i++){
        if(searchTagLocalObjects.get(i).getKey().equalsIgnoreCase(text)){
            searchTagLocalObjects.remove(i);}
        }
        searchTagAdapter.notifyDataSetChanged();
        if(searchTagLocalObjects.size()==0)
        {
            recyler_searchtag.setVisibility(View.GONE);
        }
        getNotification(true);
    }
}
