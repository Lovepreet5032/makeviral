package com.prouman.contactmanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddToGroup extends AppCompatActivity {
    ProgressDialog progressDialog;
    ArrayList<TagDataObject.Groups> arrayListGroup;
    RecyclerView recycler_email;
    ArrayList<ContactManagerDataObject.ContactManagerListData> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_email = (RecyclerView) findViewById(R.id.recycler_email);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        data = getIntent().getParcelableArrayListExtra(IntentConstant.CONTACTLIST);
        recycler_email.setLayoutManager(layoutManager);
        arrayListGroup=new ArrayList<>();
        getTagData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getTagData() {
        progressDialog=new ProgressDialog(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        final String uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        final  String hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        progressDialog.setMessage("Loading");

        if(progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();}
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GETGROUPS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                try {
                    Gson gson=new Gson();
                    TagDataObject   contactManagerDataObject =  gson.fromJson(response,TagDataObject.class);

                    //  currentPage=currentPage+1;
                    if (contactManagerDataObject.getSuccess().equalsIgnoreCase("true")) {
                        arrayListGroup=contactManagerDataObject.getData().getGroups();
                        // arrayListPush=contactManagerDataObject.getData().getCampaigns().getPush();
                        // spin_taggroup.setAdapter(new ArrayAdapter<String>(AdvanceSearch.this,R.layout.support_simple_spinner_dropdown_item,arrayList));
                    }
                    AddtoGroupAdapter addtoCampignAdapter = new AddtoGroupAdapter(arrayListGroup, AddToGroup.this,data);
                    recycler_email.setAdapter(addtoCampignAdapter);

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
                // params.put("reg_id",regId);
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
}
