package com.prouman.contactmanager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.prouman.model.CountryListDataObject;
import com.prouman.ninjaforms.CustomAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class AdvanceSearch extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText input_findby_location,input_name,input_surname,input_email;
    TextView input_dateto,input_datefrom,inputDOB;
    EditText input_phone,input_city,input_state,input_streetaddress,input_zipcode,input_device,input_browse,inputAge;
    Spinner spin_taggroup,spin_group;
    Spinner spin_country,spin_gender,spin_category,spin_source,spin_agerange;

    Button btn_submit;
    ImageView layout_logo;
    ArrayList<CountryListDataObject.CountryData> arrayListCountryCode = new ArrayList<>();
    TextView textViewDate;
    ArrayList<String> arrgroupId=new ArrayList<>();
    ArrayList<String> arrtagId=new ArrayList<>();
    String[] strGender=new String[]{"Select Gender","Male","Female"};
    String[] strAgeRange=new String[]{"Select Age Param","Exact Age","Less than","Greater than","Between"};
    String strGroupId="";
    String strTagId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(this);
        setContentView(R.layout.activity_advance_search);
        input_dateto=(TextView)findViewById(R.id.input_dateto);
        input_datefrom=(TextView)findViewById(R.id.input_datefrom);
        inputDOB=(TextView)findViewById(R.id.inputDOB);
        inputAge=(EditText) findViewById(R.id.inputAge);
        spin_taggroup=(Spinner) findViewById(R.id.spin_taggroup);
        spin_category=(Spinner) findViewById(R.id.spin_category);
        spin_source=(Spinner) findViewById(R.id.spin_source);
        spin_group=(Spinner) findViewById(R.id.spin_group);
        input_findby_location=(EditText)findViewById(R.id.input_findby_location);
        input_name=(EditText)findViewById(R.id.input_name);
        input_surname=(EditText)findViewById(R.id.input_surname);
        spin_gender=(Spinner)findViewById(R.id.spin_gender);
        spin_agerange=(Spinner)findViewById(R.id.spin_agerange);
        input_email=(EditText)findViewById(R.id.input_email);
        input_phone=(EditText)findViewById(R.id.input_phone);
        input_city=(EditText)findViewById(R.id.input_city);
        input_state=(EditText)findViewById(R.id.input_state);
        input_streetaddress=(EditText)findViewById(R.id.input_streetaddress);
        input_zipcode=(EditText)findViewById(R.id.input_zipcode);
        input_device=(EditText)findViewById(R.id.input_device);
        input_browse=(EditText)findViewById(R.id.input_browse);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                HashMap<String,String> hashMap=new HashMap<>();

                ArrayList<SearchTagLocalObject> arrsearLocal=new ArrayList<>();
                String strQuery="";
                if(input_dateto.getText().toString().trim().length()>0) {
                    hashMap.put("search[date_to]=",input_dateto.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Date To:"+input_dateto.getText().toString());
                    searchTagLocalObject.setKey("search[date_to]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(inputDOB.getText().toString().trim().length()>0) {
                    hashMap.put("search[dob]=",inputDOB.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Date Of Birth:"+inputDOB.getText().toString());
                    searchTagLocalObject.setKey("search[dob]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_datefrom.getText().toString().trim().length()>0) {
                    hashMap.put("search[date_from]=",input_datefrom.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Date From:"+input_datefrom.getText().toString());
                    searchTagLocalObject.setKey("search[date_from]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_findby_location.getText().toString().trim().length()>0) {
                    hashMap.put("search[location]=",input_findby_location.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Location:"+input_findby_location.getText().toString());
                    searchTagLocalObject.setKey("search[location]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_name.getText().toString().trim().length()>0) {
                    hashMap.put("search[first_name]=",input_name.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("First Name:"+input_name.getText().toString());
                    searchTagLocalObject.setKey("search[first_name]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_surname.getText().toString().trim().length()>0) {
                    hashMap.put("search[last_name]=",input_surname.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Last Name:"+input_surname.getText().toString());
                    searchTagLocalObject.setKey("search[last_name]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_email.getText().toString().trim().length()>0) {
                    hashMap.put("search[email]=",input_email.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Email:"+input_email.getText().toString());
                    searchTagLocalObject.setKey("search[email]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_phone.getText().toString().trim().length()>0) {
                    hashMap.put("search[phone]=",input_phone.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Phone:"+input_email.getText().toString());
                    searchTagLocalObject.setKey("search[phone]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_city.getText().toString().trim().length()>0) {
                    hashMap.put("search[city]=",input_city.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("City:"+input_city.getText().toString());
                    searchTagLocalObject.setKey("search[city]=");
                    arrsearLocal.add(searchTagLocalObject);
                }if(input_state.getText().toString().trim().length()>0) {
                    hashMap.put("search[state]=",input_state.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("State:"+input_state.getText().toString());
                    searchTagLocalObject.setKey("search[state]=");
                    arrsearLocal.add(searchTagLocalObject);
                }if(input_streetaddress.getText().toString().trim().length()>0) {
                    hashMap.put("search[street_address]=",input_streetaddress.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Email:"+input_streetaddress.getText().toString());
                    searchTagLocalObject.setKey("search[street_address]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_zipcode.getText().toString().trim().length()>0) {
                    hashMap.put("search[zip_code]=",input_zipcode.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("ZipCode:"+input_zipcode.getText().toString());
                    searchTagLocalObject.setKey("search[zip_code]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_device.getText().toString().trim().length()>0) {
                    hashMap.put("search[device]=",input_device.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Device:"+input_device.getText().toString());
                    searchTagLocalObject.setKey("search[device]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(input_browse.getText().toString().trim().length()>0) {
                    hashMap.put("search[browse]=",input_browse.getText().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Browse:"+input_browse.getText().toString());
                    searchTagLocalObject.setKey("search[browse]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(spin_country.getSelectedItemPosition()>0){
                    hashMap.put("search[country_code]=",arrayListCountryCode.get(spin_country.getSelectedItemPosition()).getCode());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Country:"+arrayListCountryCode.get(spin_country.getSelectedItemPosition()).getCountry());
                    searchTagLocalObject.setKey("search[country_code]=");
                    arrsearLocal.add(searchTagLocalObject);
                }

                if(spin_gender.getSelectedItemPosition()>0){
                    hashMap.put("search[gender]=",spin_gender.getSelectedItem().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Gender:"+spin_gender.getSelectedItem().toString());
                    searchTagLocalObject.setKey("search[gender]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(spin_category.getSelectedItemPosition()>0){
                  //  hashMap.put("search[category]=",spin_country.getText().toString());
                }
                if(spin_source.getSelectedItemPosition()>0){
                    //hashMap.put("search[source]=",spin_country.getText().toString());
                }
                if(inputAge.getText().toString().trim().length()>0){
                    hashMap.put("search[age_range]=",spin_agerange.getSelectedItem().toString());
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    hashMap.put("search[age]=",inputAge.getText().toString().trim());
                    searchTagLocalObject.setValue("Age Cateria:"+spin_agerange.getSelectedItem().toString());
                    searchTagLocalObject.setKey("search[age_range]=");
                    arrsearLocal.add(searchTagLocalObject);
                    SearchTagLocalObject searchTagLocalObject2=new SearchTagLocalObject();
                    searchTagLocalObject2.setValue("Age:"+inputAge.getText().toString());
                    searchTagLocalObject2.setKey("search[age]=");
                    arrsearLocal.add(searchTagLocalObject2);
                }
                if(spin_taggroup.getSelectedItemPosition()>0){
                    hashMap.put("search[group]=",arrgroupId.get(spin_taggroup.getSelectedItemPosition()-1));
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Group:"+spin_taggroup.getSelectedItem().toString());
                    searchTagLocalObject.setKey("search[group]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                if(spin_group.getSelectedItemPosition()>0) {
                    hashMap.put("search[tag]=", arrtagId.get(spin_group.getSelectedItemPosition()-1));
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Tag:"+spin_group.getSelectedItem().toString());
                    searchTagLocalObject.setKey("search[tag]=");
                    arrsearLocal.add(searchTagLocalObject);
                }
                Intent data = new Intent();
              //  data.putExtra("query",sb.toString());
                data.putExtra("query",hashMap);
                data.putParcelableArrayListExtra("querylist",arrsearLocal);
                setResult(RESULT_OK,data);
                finish();
            }
        });

        findViewById(R.id.layout_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        Gson gson = new Gson();
        CountryListDataObject countryListDataObject=gson.fromJson(AppConstant.loadCountryJson(this), CountryListDataObject.class);
        ArrayList<CountryListDataObject.CountryData> arrayList =new ArrayList<>();
        arrayList.add(null);
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
//        for(int k=0;k<arrayListCountryCode.size();k++)
//        {
//            CountryListDataObject.CountryData countryData=arrayListCountryCode.get(k);
//            if(countryData.getIso().equalsIgnoreCase(countryCodeValue))
//            {
//                spin_country.setSelection(k);
//                break;
//            }
//        }
        input_dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDate=input_dateto;
                showDatePicker();
            }
        });input_datefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDate=input_datefrom;
                showDatePicker();
            }
        });
        inputDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDate=inputDOB;
                showDatePicker();
            }
        });
        spin_gender.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,strGender));
        spin_category.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"Select Category"}));
        spin_source.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"Select Source"}));
        //spin_category
        spin_agerange.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,strAgeRange));
         setSearchData();
        getNotification();
    }

    private void setSearchData() {
        ArrayList<SearchTagLocalObject> searchTagLocalObjects= getIntent().getParcelableArrayListExtra("querylist");
        for(int i=0;i<searchTagLocalObjects.size();i++)
        {
            SearchTagLocalObject searchTagLocalObject=searchTagLocalObjects.get(i);
            if(searchTagLocalObject.getKey().equalsIgnoreCase("search[date_to]=")){
                input_dateto.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[date_from]=")){
                input_datefrom.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[location]=")){
                input_findby_location.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[first_name]=")){
                input_name.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[last_name]=")){
                input_surname.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[email]=")){
                input_email.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[country_code]=")){
                for (int j=0;j<arrayListCountryCode.size();j++){
                    if(null != arrayListCountryCode.get(j) && searchTagLocalObject.getValue().split(":")[1].equalsIgnoreCase(arrayListCountryCode.get(j).getCountry())) {
                        spin_country.setSelection(j);
                    }
                }
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[gender]=")){
                 for (int j=0;j<strGender.length;j++){
                     if(searchTagLocalObject.getValue().split(":")[1].equalsIgnoreCase(strGender[j])) {
                         spin_gender.setSelection(j);
                     }
                 }
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[age_range]=")){
                for (int j=0;j<strAgeRange.length;j++){
                    if(searchTagLocalObject.getValue().split(":")[1].equalsIgnoreCase(strAgeRange[j])) {
                        spin_agerange.setSelection(j);
                    }
                }
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[age]=")){
                inputAge.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[phone]=")) {
                input_phone.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[city]=")) {
                input_city.setText(searchTagLocalObject.getValue().split(":")[1]);
            }else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[state]=")) {
                input_state.setText(searchTagLocalObject.getValue().split(":")[1]);
            }else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[street_address]=")) {
                input_streetaddress.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[zip_code]=")) {
                input_zipcode.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[device]=")) {
                input_device.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[browse]=")) {
                input_browse.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[dob]=")) {
                inputDOB.setText(searchTagLocalObject.getValue().split(":")[1]);
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[group_id]=")){
                strGroupId=searchTagLocalObject.getValue().split(":")[1];
            }
            else if(searchTagLocalObject.getKey().equalsIgnoreCase("search[tag_id]=")){
                strTagId=searchTagLocalObject.getValue().split(":")[1];
            }
        }
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
                    ArrayList<String> arrayList=new ArrayList<>();
                  //  currentPage=currentPage+1;
                    if (contactManagerDataObject.getSuccess().equalsIgnoreCase("true")) {
                        arrayList.add("Select Group");
                        for (TagDataObject.Groups key: contactManagerDataObject.getData().getGroups()) {
                            arrayList.add(key.getGroupName());
                            arrgroupId.add(key.getId());
                        }
                        ArrayList<String> arrayList1=new ArrayList<>();
                            arrayList1.add("Select tags");
                        for (TagDataObject.Groups key: contactManagerDataObject.getData().getTags()) {
                            arrayList1.add(key.getGroupName());
                            arrtagId.add(key.getId());
                        }

                         spin_taggroup.setAdapter(new ArrayAdapter<String>(AdvanceSearch.this,R.layout.support_simple_spinner_dropdown_item,arrayList));
                        spin_group.setAdapter(new ArrayAdapter<String>(AdvanceSearch.this,R.layout.support_simple_spinner_dropdown_item,arrayList1));
                        if(!strGroupId.equalsIgnoreCase(""))
                        {
                            for (int i=0;i<arrayList.size();i++)
                            {
                                if(strGroupId.equalsIgnoreCase(arrayList.get(i))){
                                    spin_taggroup.setSelection(i);
                                }
                            }
                        }
                        if(!strTagId.equalsIgnoreCase(""))
                        {
                            for (int i=0;i<arrayList.size();i++)
                            {
                                if(strTagId.equalsIgnoreCase(arrayList1.get(i))){
                                    spin_group.setSelection(i);
                                }
                            }
                        }
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
}
