package com.prouman.contactmanager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.google.android.material.tabs.TabLayout;
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

public class AddtoCampign extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    ArrayList<ContactManagerDataObject.ContactManagerListData> data;

    private ViewPager mViewPager;
    ArrayList<TagDataObject.Email> arrayListSms=new ArrayList<>();
    ArrayList<TagDataObject.Email> arrayListEmail=new ArrayList<>();
    ArrayList<TagDataObject.Email> arrayListPush=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_campign);
        progressDialog = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        data = getIntent().getParcelableArrayListExtra(IntentConstant.CONTACTLIST);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        getTagData();

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_addto_campign, menu);
//        return true;
//    }

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        RecyclerView recycler_email;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, ArrayList<TagDataObject.Email> emailArrayList,ArrayList<ContactManagerDataObject.ContactManagerListData> data,String strtype) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putParcelableArrayList("list", emailArrayList);
            args.putParcelableArrayList(IntentConstant.CONTACTLIST, data);
            args.putString("type", strtype);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_addto_campign, container, false);


            recycler_email = (RecyclerView) rootView.findViewById(R.id.recycler_email);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recycler_email.setLayoutManager(layoutManager);
            ArrayList<TagDataObject.Email> arrayList=getArguments().getParcelableArrayList("list");
            ArrayList<ContactManagerDataObject.ContactManagerListData> arrayList1=getArguments().getParcelableArrayList(IntentConstant.CONTACTLIST);
            AddtoCampignAdapter addtoCampignAdapter = new AddtoCampignAdapter(arrayList, getActivity(),arrayList1,getArguments().getString("type"));
            recycler_email.setAdapter(addtoCampignAdapter);
//            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1 && arrayListEmail.size() <= 0) {
//                AddtoCampignAdapter addtoCampignAdapter = new AddtoCampignAdapter(arrayListEmail, getActivity());
//                recycler_email.setAdapter(addtoCampignAdapter);
//
//            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2 && arrayListSms.size() <= 0) {
//                AddtoCampignAdapter addtoCampignAdapter = new AddtoCampignAdapter(arrayListSms, getActivity());
//                recycler_email.setAdapter(addtoCampignAdapter);
//            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 1 && arrayListPush.size() <= 0) {
//                AddtoCampignAdapter addtoCampignAdapter = new AddtoCampignAdapter(arrayListPush, getActivity());
//                recycler_email.setAdapter(addtoCampignAdapter);
//            } else {
//                getTagData();
//            }
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0){ return PlaceholderFragment.newInstance(position + 1, arrayListEmail,data,"email");}
            else if(position==1){ return PlaceholderFragment.newInstance(position + 1, arrayListSms,data,"sms");}
            else{ return PlaceholderFragment.newInstance(position + 1, arrayListPush,data,"push");}

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }




    private void getTagData() {
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
                        arrayListEmail=contactManagerDataObject.getData().getCampaigns().getEmail();
                        arrayListSms=contactManagerDataObject.getData().getCampaigns().getSms();
                        // arrayListPush=contactManagerDataObject.getData().getCampaigns().getPush();
                        // spin_taggroup.setAdapter(new ArrayAdapter<String>(AdvanceSearch.this,R.layout.support_simple_spinner_dropdown_item,arrayList));
                    }
                    mViewPager.setAdapter(mSectionsPagerAdapter);

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

                    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

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
