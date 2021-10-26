package com.prouman.contactmanager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.gson.Gson;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.IntentConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.app.Config;
import com.prouman.common.SucessDataObject;
import com.prouman.model.CountryListDataObject;
import com.prouman.ninjaforms.CustomAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class EditCampaigns extends AppCompatActivity implements View.OnClickListener{
    ProgressDialog progressDialog;
    EditText input_name,input_surname,input_email;
    TextView inputDOB;
    EditText input_phone,input_city,input_state,input_streetaddress,input_zipcode;
    Spinner spin_group;
    Spinner spin_country,spin_gender,spin_category,spin_source,spin_phone;

    Button btn_submit;
    ImageView layout_logo;
    ArrayList<CountryListDataObject.CountryData> arrayListCountryCode = new ArrayList<>();
    TextView textViewDate;
    ContactManagerDataObject.ContactManagerListData contactManagerListData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_campaigns);
        progressDialog=new ProgressDialog(this);
        contactManagerListData = getIntent().getExtras().getParcelable(IntentConstant.CONTACTLIST);
        inputDOB=(TextView)findViewById(R.id.inputDOB);
        spin_category=(Spinner) findViewById(R.id.spin_category);
        spin_source=(Spinner) findViewById(R.id.spin_source);
        spin_group=(Spinner) findViewById(R.id.spin_group);
        input_name=(EditText)findViewById(R.id.input_name);
        input_surname=(EditText)findViewById(R.id.input_surname);
        spin_gender=(Spinner)findViewById(R.id.spin_gender);
        input_email=(EditText)findViewById(R.id.input_email);
        spin_phone=(Spinner)findViewById(R.id.spin_phone);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        input_phone=(EditText)findViewById(R.id.input_phone);
        input_city=(EditText)findViewById(R.id.input_city);
        input_state=(EditText)findViewById(R.id.input_state);
        input_streetaddress=(EditText)findViewById(R.id.input_streetaddress);
        input_zipcode=(EditText)findViewById(R.id.input_zipcode);

        btn_submit.setOnClickListener(this);
        input_name.setText(contactManagerListData.getFirstName());
        input_email.setText(contactManagerListData.getEmail());
        input_surname.setText(contactManagerListData.getLastName());
        inputDOB.setText(contactManagerListData.getDob());
        input_phone.setText(contactManagerListData.getPhone());
        input_city.setText(contactManagerListData.getCity());
        input_state.setText(contactManagerListData.getState());
        input_streetaddress.setText(contactManagerListData.getAddress());
        input_zipcode.setText(contactManagerListData.getZip());
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StringBuilder sb = new StringBuilder();
//                HashMap<String,String> hashMap=new HashMap<>();
//                String strQuery="";
//
//                if(input_name.getText().toString().trim().length()>0) {
//                    hashMap.put("search[name]=",input_name.getText().toString());
//                }
//                if(input_surname.getText().toString().trim().length()>0) {
//                    hashMap.put("search[surname]=",input_surname.getText().toString());
//                }
//                if(input_email.getText().toString().trim().length()>0) {
//                    hashMap.put("search[email]=",input_email.getText().toString());
//                }
//
//                Intent data = new Intent();
//                //  data.putExtra("query",sb.toString());
//                data.putExtra("query",hashMap);
//                setResult(RESULT_OK,data);
//                finish();
//            }
//        });
        getNotification();
        findViewById(R.id.layout_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//        String countryCodeValue = tm.getNetworkCountryIso();
        Gson gson = new Gson();
        CountryListDataObject countryListDataObject=gson.fromJson(AppConstant.loadCountryJson(this), CountryListDataObject.class);
        ArrayList<CountryListDataObject.CountryData> arrayList=new ArrayList<>();
        if(null == contactManagerListData.getCountry()) {
            arrayList.add(null);
        }
        if(null != contactManagerListData.getCountry() &&  contactManagerListData.getCountry().equalsIgnoreCase("N\\/A"))
        {
            arrayList.add(null);
        }
        arrayList.addAll(countryListDataObject.getListCountryData());
        arrayListCountryCode.addAll(arrayList);


        spin_country=(Spinner)findViewById(R.id.spin_country);
        //  spin_country.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        // spinner.setTag(element.getId());
        //   spin_country.setBackground(getResources().getDrawable(R.drawable.whiteback_roundcorner));

        CustomAdapter arrayAdapter = new CustomAdapter(this,
                R.layout.row_spinner, R.id.txt_countrycode, arrayListCountryCode);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_country.setAdapter(arrayAdapter);
        CustomAdapter arrayAdaptercount = new CustomAdapter(this,
                R.layout.row_spinner, R.id.txt_countrycode, arrayListCountryCode);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_country.setAdapter(arrayAdapter);
        spin_phone.setAdapter(arrayAdaptercount);
      if(null != contactManagerListData.getCountry()) {
          for (int k = 0; k < arrayListCountryCode.size(); k++) {
              CountryListDataObject.CountryData countryData = arrayListCountryCode.get(k);
              if (countryData.getCountry().equalsIgnoreCase(contactManagerListData.getCountry())) {
                  spin_country.setSelection(k);
                  spin_phone.setSelection(k);
                  break;
              }
          }
      }

        inputDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDate=inputDOB;
                showDatePicker();
            }
        });
//        spin_gender.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"Select Gender","Male","Female"}));
////        spin_category.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"Select Category"}));
////        spin_category_source.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"Select Source"}));
        //spin_category
      //  spin_agerange.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"Exact Age","Less than","Greater than","Between"}));
    }
    private void getNotification() {
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
                    TagDataObject contactManagerDataObject=  gson.fromJson(response,TagDataObject.class);
                    ArrayList<String> arrayListGroup=new ArrayList<>();
                    arrayListGroup.add("select group");
                    ArrayList<String> arrayListcategory=new ArrayList<>();
                    arrayListcategory.add("select category");
                    ArrayList<String> arrayListcategorysource=new ArrayList<>();
                    arrayListcategorysource.add("select source");
                    ArrayList<String> arrayListgender=new ArrayList<>();
                    arrayListgender.add("select gender");
                    //  currentPage=currentPage+1;
                    if (contactManagerDataObject.getSuccess().equalsIgnoreCase("true")) {
                        for (TagDataObject.Groups key: contactManagerDataObject.getData().getGroups()) {
                            arrayListGroup.add(key.getGroupName());
                        }
                        for (Map.Entry<String, String> key: contactManagerDataObject.getData().getGender().entrySet()) {
                            arrayListgender.add(key.getValue());
                        }
                        for (Map.Entry<String, String> key: contactManagerDataObject.getData().getSources().entrySet()) {
                            arrayListcategorysource.add(key.getValue());
                        }
                        for (TagDataObject.Groups key: contactManagerDataObject.getData().getCategory()) {
                            arrayListcategory.add(key.getGroupName());
                        }
                        spin_gender.setAdapter(new ArrayAdapter<String>(EditCampaigns.this,android.R.layout.simple_spinner_dropdown_item,arrayListgender));
                        spin_category.setAdapter(new ArrayAdapter<String>(EditCampaigns.this,android.R.layout.simple_spinner_dropdown_item,arrayListcategory));
                        spin_source.setAdapter(new ArrayAdapter<String>(EditCampaigns.this,android.R.layout.simple_spinner_dropdown_item,arrayListcategorysource));
                        ArrayList<String> arrayList1=new ArrayList<>();
                        arrayList1.add("Select tags");
                        //spin_taggroup.setAdapter(new ArrayAdapter<String>(AdvanceSearch.this,R.layout.support_simple_spinner_dropdown_item,arrayList));
                        spin_group.setAdapter(new ArrayAdapter<String>(EditCampaigns.this,R.layout.support_simple_spinner_dropdown_item,arrayListGroup));
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
    private void showDatePicker() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

// Create the DatePickerDialog instance
        DatePickerDialog datePicker = new DatePickerDialog(this, datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.setTitle("Select the date");
        datePicker.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
//            TextView tvDt = (TextView) findViewById(R.id.tvDate);
            textViewDate.setText(day1 + "/" + month1 + "/" + year1);

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submit:
                if(input_name.getText().toString().trim().length()==0)
                {
                    input_name.setError("Please enter first name");
                }
                else if (input_email.getText().toString().trim().length()==0)
                {
                    input_email.setError("Please enter Email");
                }
                else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    String strQuery = "";
                    hashMap.put("pro[pro_id]=",contactManagerListData.getProId());
                    if (input_name.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[first_name]=", input_name.getText().toString());
                    }
                    if (input_surname.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[sur_name]=", input_surname.getText().toString());
                    }
                    if (input_email.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[email]=", input_email.getText().toString());
                    }
                    if (input_phone.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[phone]=", input_phone.getText().toString());
                    }
                    if (input_city.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[city]=", input_city.getText().toString());
                    }
                    if (input_state.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[state]=", input_state.getText().toString());
                    }
                    if (input_streetaddress.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[street_address]=", input_streetaddress.getText().toString());
                    }
                    if (input_zipcode.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[zip_code]=", input_zipcode.getText().toString());
                    }
                    if (inputDOB.getText().toString().trim().length() > 0) {
                        hashMap.put("pro[dob]=", inputDOB.getText().toString());
                    }
//                    if(spin_group.getSelectedItemPosition()!=0){
//                        hashMap.put("pro[group]=", inputDOB.getText().toString());
//                    }
//                    if(spin_country.getSelectedItemPosition()!=0){
//                        hashMap.put("pro[group]=", inputDOB.getText().toString());
//                    }
//                    if(spin_gender.getSelectedItemPosition()!=0){
//                        hashMap.put("pro[group]=", inputDOB.getText().toString());
//                    }
//                    if(spin_category.getSelectedItemPosition()!=0){
//                        hashMap.put("pro[category]=", spin_category);
//                    }
//                    if(spin_source.getSelectedItemPosition()!=0){
//                        hashMap.put("pro[group]=", inputDOB.getText().toString());
//                    }
//                    if(spin_phone.getSelectedItemPosition()!=0){
//                        hashMap.put("pro[group]=", inputDOB.getText().toString());
//                    }
                  //  hashMap.put("pro[group]=", spin_taggroup.getSelectedItem().toString());
                    editCampigns(hashMap);

                }
                break;
        }

    }
    private void editCampigns(final HashMap<String,String> hashMap) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        final String uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        final  String hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        progressDialog.setMessage("Loading");

        if(progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();}
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.EDITPROSPECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                try {
                    Gson gson=new Gson();
                    SucessDataObject sucessDataObject =  gson.fromJson(response,SucessDataObject.class);
                    if(null != sucessDataObject) {
                        if (sucessDataObject.getSuccess().equalsIgnoreCase("true")) {
                            finish();
                        }
                        Toast.makeText(EditCampaigns.this, sucessDataObject.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    else{ Toast.makeText(EditCampaigns.this, "Server error", Toast.LENGTH_LONG).show();}
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
                params.putAll(hashMap);
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

