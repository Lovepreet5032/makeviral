package com.prouman.ninjaforms;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.prouman.Util.PrefrencesConstant;
import com.prouman.app.Config;
import com.prouman.model.CountryListDataObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.prouman.R.id.back_btn;
import static com.prouman.R.id.btn_submit;

public class DynamicForm extends AppCompatActivity implements View.OnClickListener {
    LinearLayout dynamic_layout;
    TextView titleTv;
    ImageView backBtn;
    EditText textViewDate;
    String deviceid="";
    Button btn_submits;
    ArrayList<SubmitDatabject> submitDatabjectArrayList;
    SubmitDatabject submitDatabject;
    Map<String, String> stringMap;
    Map<String, String> stringHiddenMap = null;
    ProgressDialog progressDialog;
    private String uproID;
    String hash;
    String form_id;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    View.OnClickListener clickListener;
    private int intCount = 0;
    boolean isBackPressed = false;
    LinearLayout layoutPhone;
    EditText phoneEditText;
    Spinner spinnerPhone;
    //ArrayList<String> arrayListContryName;
    ArrayList<CountryListDataObject.CountryData> arrayListCountryCode = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_form);
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intCount++;
            }
        };
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPref", 0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        submitDatabjectArrayList = new ArrayList<>();
        dynamic_layout = (LinearLayout) findViewById(R.id.dynamic_layout);
        titleTv = (TextView) findViewById(R.id.titleTv);
        Form formdata = getIntent().getExtras().getParcelable("form_fields");
        form_id = formdata.getId();
        titleTv.setText(formdata.getFormName());
        backBtn = (ImageView) findViewById(back_btn);
        btn_submits = (Button) findViewById(R.id.btn_submit);
        btn_submits.setText(formdata.getButton_text());
        btn_submits.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        //    Toast.makeText(this,formdata.getFormName(),Toast.LENGTH_LONG).show();
        stringHiddenMap = new HashMap<>();
        for (int i = 0; i < formdata.getFormFields().size(); i++) {
            Label label = formdata.getFormFields().get(i).getLabel();
            ArrayList<Element> elementArrayList = formdata.getFormFields().get(i).getElements();
            if (label != null && formdata.getFormFields().get(i).getAnchor() == null) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER);
                textView.setText(label.getText());
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setPadding(20, 20, 20, 20);
                dynamic_layout.addView(textView);
            }
            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(RadioGroup.HORIZONTAL);
            for (int j = 0; j < elementArrayList.size(); j++) {
                submitDatabject = new SubmitDatabject();
                Element element = elementArrayList.get(j);
                String type = element.getType();
                if (type != null && (type.equalsIgnoreCase("text") ||
                        type.equalsIgnoreCase("email") || type.equals("number") || type.equalsIgnoreCase("tel") ||
                        type.equalsIgnoreCase("password")) || type.equalsIgnoreCase("textarea") ||
                        type.equalsIgnoreCase("url")) {
                    if (type != null) {
                        addEditText(element, label.getText());
                    }
                } else if (type != null && type.equalsIgnoreCase("checkbox")) {
                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    checkBox.setTag(element.getId());

                    checkBox.setTextColor(Color.parseColor("#ffffff"));
                    checkBox.setPadding(20, 20, 20, 20);
                    TextView textView = new TextView(this);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    textView.setTextColor(Color.parseColor("#8A90A5"));
                    if (null != formdata.getFormFields().get(i).getAnchor() && null != formdata.getFormFields().get(i).getAnchor().getText()) {
                        if (!formdata.getFormFields().get(i).getAnchor().getText().contains("Privacy")) {
                            checkBox.setText(element.getValue());
                        }
                        textView.setText(Html.fromHtml("<a \\" + formdata.getFormFields().get(i).getAnchor().getHref() + ">" + formdata.getFormFields().get(i).getAnchor().getText() + "<\\a>"));
                        final String ahref = formdata.getFormFields().get(i).getAnchor().getHref();
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intCount++;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ahref));
                                startActivity(intent);
                            }
                        });
                    }
                    textView.setMovementMethod(LinkMovementMethod.getInstance());


                    linearLayout.addView(checkBox);
                    linearLayout.addView(textView);
                    checkBox.setOnClickListener(clickListener);
                    //   textView.setOnClickListener(clickListener);
                    dynamic_layout.addView(linearLayout);
                    submitDatabject.setStrType(element.getType());
                    submitDatabject.setView(checkBox);
                    submitDatabject.setField_name(element.getName());
                    submitDatabject.setStrRequired(element.getRequired());
                    submitDatabject.setField_name(element.getName());
                    submitDatabject.setValue(element.getValue());
                    submitDatabjectArrayList.add(submitDatabject);
                } else if (type != null && type.equalsIgnoreCase("select") && (null != element.getId() && element.getId().equalsIgnoreCase("telcode_phone"))) {

                    // Iterator iter = null;
                    Map<String, String> jsonObject = null;
//                 try {
                    jsonObject = element.getoption_str();
                    if (null != jsonObject) {
                        Set<Map.Entry<String, String>> entries = jsonObject.entrySet();
                        //Map<String, String> treeMap = new TreeMap<String, String>(jsonObject);
                        // iter = treeMap.entrySet().iterator();
                        Comparator<Map.Entry<String, String>> valueComparator = new Comparator<Map.Entry<String, String>>() {
                            @Override
                            public int compare(Map.Entry<String, String> e1, Map.Entry<String, String> e2) {
                                String v1 = e1.getValue();
                                String v2 = e2.getValue();
                                return v1.compareTo(v2);
                            }
                        };
                        List<Map.Entry<String, String>> listOfEntries = new ArrayList<Map.Entry<String, String>>(entries);
                        Collections.sort(listOfEntries, valueComparator);
                        LinkedHashMap<String, String> sortedByValue = new LinkedHashMap<String, String>(listOfEntries.size());
//                    arrayListContryName = new ArrayList<>();
//                    arrayListCountryCode = new ArrayList<>();
// copying entries from List to Map
//                 for(Map.Entry<String, String> entry : listOfEntries)
//                 {
//                     sortedByValue.put(entry.getKey(), entry.getValue());
//                 }
                        // Set<Map.Entry<String, String>> entrySetSortedByValue = sortedByValue.entrySet();
//                     for(Map.Entry<String, String> mapping : entrySetSortedByValue)
//                     {
                        //   System.out.println(mapping.getKey() + " ==> " + mapping.getValue());\
                        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
                        String countryCodeValue = tm.getNetworkCountryIso();
                        Gson gson = new Gson();
                        CountryListDataObject countryListDataObject=gson.fromJson(AppConstant.loadCountryJson(this), CountryListDataObject.class);
                        ArrayList<CountryListDataObject.CountryData> arrayList =countryListDataObject.getListCountryData();
//                     for (CountryListDataObject.CountryData countryData:arrayList){
//                         arrayListCountryCode.add(countryData.get);}
//                     try {
                        //;
                        arrayListCountryCode.addAll(arrayList);
                        //}


                        Spinner spinner = new Spinner(this);//,null,android.R.style.Widget_Spinner,Spinner.MODE_DROPDOWN);
                        spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        spinner.setTag(element.getId());
                        spinner.setBackground(getResources().getDrawable(R.drawable.whiteback_roundcorner));

//                 spinner.setText(label.getText()+" <a>"+formdata.getFormFields().get(i).getAnchor()+"</\\a>");
//                 spinner.setTextColor(Color.parseColor("#000000"));
                 /*spinner.setPadding(20,20,20,20);*/


//                     while (iter.hasNext()) {
//                         TreeMap.Entry pair = (TreeMap.Entry)iter.next();
//                         arrayListCountryCode.add(pair.getKey().toString());
////                     try {
//                         //;
//                         arrayListContryName.add(pair.getValue().toString());
////                     } catch (JSONException e) {
////                         // Something went wrong!
////                     }
//
//                     }
//                 } catch (JSONException e) {
//                     e.printStackTrace();
//                 }
                        CustomAdapter arrayAdapter = new CustomAdapter(this,
                                R.layout.row_spinner, R.id.txt_countrycode, arrayListCountryCode);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(arrayAdapter);
                        for(int k=0;k<arrayListCountryCode.size();k++)
                        {
                            CountryListDataObject.CountryData countryData=arrayListCountryCode.get(k);
                            if(countryData.getIso().equalsIgnoreCase(countryCodeValue))
                            {
                                spinner.setSelection(k);
                                break;
                            }
                        }
                        //spinner.setOnClickListener(clickListener);
//                 spinner.setMovementMethod(LinkMovementMethod.getInstance());
                        if (null != layoutPhone) {
                            layoutPhone.removeAllViews();
                            layoutPhone.addView(phoneEditText);
                            layoutPhone.addView(spinner);
                            dynamic_layout.addView(layoutPhone);
                        } else {
                            spinnerPhone = spinner;
                            layoutPhone = new LinearLayout(DynamicForm.this);
                            layoutPhone.setOrientation(LinearLayout.HORIZONTAL);
                            layoutPhone.setPadding(0, 0, 0, 0);
                            //  layoutPhone.setBackground(getResources().getDrawable(R.drawable.whiteback_roundcorner));
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutPhone.setLayoutParams(layoutParams);
                            layoutPhone.addView(spinner);
                        }
                        submitDatabject.setStrType(element.getType());
                        submitDatabject.setView(spinner);
                        submitDatabject.setStrRequired(element.getRequired());
                        submitDatabject.setField_name(element.getName());
                        submitDatabject.setValue(element.getValue());
                        submitDatabjectArrayList.add(submitDatabject);
                    }
                } else if (type != null && type.equalsIgnoreCase("select") && (null != element.getId() && !element.getId().equalsIgnoreCase("telcode_phone"))) {
                    Spinner spinner = new Spinner(this);//,null,android.R.style.Widget_Spinner,Spinner.MODE_DROPDOWN);
                    spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    spinner.setTag(element.getId());
                    spinner.setBackground(getResources().getDrawable(R.drawable.whiteback_roundcorner));
//                 spinner.setText(label.getText()+" <a>"+formdata.getFormFields().get(i).getAnchor()+"</\\a>");
//                 spinner.setTextColor(Color.parseColor("#000000"));
                    spinner.setPadding(20, 20, 20, 20);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                            R.layout.row_spinner, element.getOptions());
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                    //spinner.setOnClickListener(clickListener);
//                 spinner.setMovementMethod(LinkMovementMethod.getInstance());
                    dynamic_layout.addView(spinner);
                    submitDatabject.setStrType(element.getType());
                    submitDatabject.setView(spinner);
                    submitDatabject.setStrRequired(element.getRequired());
                    submitDatabject.setField_name(element.getName());
                    submitDatabject.setValue(element.getValue());
                    submitDatabjectArrayList.add(submitDatabject);
                } else if (type != null && type.equalsIgnoreCase("radio")) {

                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setId(j);
                    radioButton.setTag(element.getId());
                    radioButton.setText(element.getValue());
                    radioButton.setTextColor(Color.parseColor("#ffffff"));
                    radioButton.setOnClickListener(clickListener);
                    radioGroup.addView(radioButton);
                    if (j == 0) {
                        radioButton.setChecked(true);
                        dynamic_layout.addView(radioGroup);
                        submitDatabject.setStrType(element.getType());
                        submitDatabject.setView(radioGroup);
                        submitDatabject.setStrRequired(element.getRequired());
                        submitDatabject.setField_name(element.getName());
                        submitDatabject.setValue(element.getValue());
                        submitDatabjectArrayList.add(submitDatabject);
                    }
                }
//              else if(type!=null&&type.equalsIgnoreCase("url"))
//             {
//                 TextView  textView =new TextView(this);
//                 textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                         LinearLayout.LayoutParams.WRAP_CONTENT));
//                 textView.setTextColor(Color.parseColor("#0000ff"));
//                 if(formdata.getFormFields().get(i).getAnchor()!=null) {
//                    textView.setText(Html.fromHtml("<a \\" + formdata.getFormFields().get(i).getAnchor().getHref() + ">" + formdata.getFormFields().get(i).getAnchor().getText() + "<\\a>"));
//                     final String ahref = formdata.getFormFields().get(i).getAnchor().getHref();
//                     textView.setOnClickListener(new View.OnClickListener() {
//                         @Override
//                         public void onClick(View view) {
//                             Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ahref));
//                             startActivity(intent);
//                         }
//                     });
//                 }
//                 dynamic_layout.addView(textView);
//             }
                else if (type != null && type.equalsIgnoreCase("date")) {
//                 LinearLayout linearLayout=new LinearLayout(this);
//                 linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                         LinearLayout.LayoutParams.WRAP_CONTENT));
//                 linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    textViewDate = new EditText(this);
                    textViewDate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    textViewDate.setTextColor(Color.parseColor("#ffffff"));
                    textViewDate.setFocusable(false);
                    textViewDate.setBackground(getResources().getDrawable(R.drawable.whiteback_roundcorner));
                    textViewDate.setPadding(20, 20, 20, 20);
                    textViewDate.setHint("DD/MM/YY");
                    textViewDate.setHintTextColor(Color.parseColor("#ffffff"));
                    textViewDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intCount++;
                            showDatePicker();
                        }
                    });
//                 Button button=new Button(this);
//                 button.setBackgroundColor(Color.parseColor("#1f4687"));
//                 button.setTextColor(Color.parseColor("#ffffff"));
//                 button.setText("Select Date");
//                 button.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View view) {
//                         showDatePicker();
//                     }
//                 });
                    //linearLayout.addView(textViewDate);
                    //  linearLayout.addView(button);
                    dynamic_layout.addView(textViewDate);
                    submitDatabject.setStrType(element.getType());
                    submitDatabject.setView(textViewDate);
                    submitDatabject.setStrRequired(element.getRequired());
                    submitDatabject.setField_name(element.getName());
                    submitDatabject.setValue(element.getValue());
                    submitDatabjectArrayList.add(submitDatabject);
                } else {

                    stringHiddenMap.put(element.getName(), element.getValue());
                }
            }
        }
        hitForm();
    }

    @Override
    public void onBackPressed() {
        isBackPressed = true;
        if (intCount == 0) {
            super.onBackPressed();
        } else {
            hitForm();
        }
    }

    private void hitForm() {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        // progressDialog.setMessage("");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.SUBMIT_HITS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        try {
                            //Parsing the fetched Json String to JSON Object
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                                //                Toast.makeText(DynamicForm.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                //            finish();
                            } else {
                                //              Toast.makeText(DynamicForm.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (isBackPressed) {
                            finish();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        if (isBackPressed) {
                            finish();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                params.put("form_id", form_id);
                params.put("device", "a");
                params.put("hits", intCount + "");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    //   getDeviceImei();
                    params.put("imei", getDeviceImei());
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                final String regId = pref.getString("regId", null);
                params.put("reg_id", regId);
                params.put("hits", intCount + "");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
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

    private void addEditText(Element element, String label) {
        EditText editText = new EditText(this);
        boolean istel = false;
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setTag(element.getId());
        editText.setBackground(getResources().getDrawable(R.drawable.whiteback_roundcorner));
        //editText.setHint(label);
        editText.setTextColor(Color.parseColor("#ffffff"));
        editText.setPadding(20, 20, 20, 20);
        if (element.getName() != null && (element.getName().equalsIgnoreCase("email") || element.getType().equalsIgnoreCase("email"))) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        if (element.getName() != null && (element.getName().equalsIgnoreCase("phone") ||
                element.getType().equalsIgnoreCase("number") || element.getType().equalsIgnoreCase("tel"))) {
            istel = true;
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        }
        if (element.getType() != null && element.getType().equalsIgnoreCase("password")) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            editText.setSelection(editText.getText().length());
        }
        if (element.getType() != null && element.getType().equalsIgnoreCase("textarea")) {
            editText.setMinLines(5);
        } else {
            editText.setSingleLine(true);
        }
        editText.setOnClickListener(clickListener);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                intCount++;
            }
        });
        if (!istel) {
            dynamic_layout.addView(editText);
        } else {
            editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            if (null != layoutPhone) {
                layoutPhone.removeAllViews();
                layoutPhone.addView(spinnerPhone);
                layoutPhone.addView(editText);
                //layoutPhone.addView(editText);
                dynamic_layout.addView(layoutPhone);
            } else {
                phoneEditText = editText;
                layoutPhone = new LinearLayout(DynamicForm.this);
                layoutPhone.setOrientation(LinearLayout.HORIZONTAL);
                layoutPhone.setPadding(0, 0, 0, 0);
                //  layoutPhone.setBackground(getResources().getDrawable(R.drawable.whiteback_roundcorner));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                layoutPhone.setLayoutParams(layoutParams);
                layoutPhone.addView(editText);
                layoutPhone.removeAllViews();
                if (null != spinnerPhone) {
                    layoutPhone.addView(spinnerPhone);
                }
                layoutPhone.addView(editText);
                dynamic_layout.addView(layoutPhone);
            }
        }

        submitDatabject.setStrType(element.getType());
        submitDatabject.setView(editText);
        submitDatabject.setStrRequired(element.getRequired());
        submitDatabject.setField_name(element.getName());
        submitDatabject.setValue(element.getValue());
        submitDatabjectArrayList.add(submitDatabject);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case back_btn:
                finish();
                break;
            case btn_submit:
                stringMap = new HashMap<>();
                if (stringHiddenMap != null) ;
                stringMap.putAll(stringHiddenMap);
                boolean errorflag = false;
                for (int i = 0; i < submitDatabjectArrayList.size(); i++) {
                    submitDatabject = submitDatabjectArrayList.get(i);
                    if (submitDatabject.getStrType().equalsIgnoreCase("text") ||
                            submitDatabject.getStrType().equalsIgnoreCase("email") || submitDatabject.getStrType().equals("number") ||
                            submitDatabject.getStrType().equalsIgnoreCase("url") || submitDatabject.getStrType().equalsIgnoreCase("tel")
                            || submitDatabject.getStrType().equalsIgnoreCase("phone") ||
                            submitDatabject.getStrType().equalsIgnoreCase("password") || submitDatabject.getStrType().equalsIgnoreCase("textarea")) {
                        if (submitDatabject.getStrRequired() != null && submitDatabject.getStrRequired().equals("1")) {
                            if (((EditText) submitDatabjectArrayList.get(i).getView()).length() > 0) {
                                stringMap.put(submitDatabject.getField_name(), ((EditText) submitDatabjectArrayList.get(i).getView()).getText().toString().trim());
                            } else {
                                ((EditText) submitDatabject.getView()).setError(getResources().getString(R.string.pleaseentername));
                                errorflag = true;
                            }
                        } else {
                            stringMap.put(submitDatabject.getField_name(), ((EditText) submitDatabject.getView()).getText().toString().trim());
                        }
                    } else if (submitDatabject.getStrType().equalsIgnoreCase("checkbox")) {
                        if (submitDatabject.getStrRequired() != null && submitDatabject.getStrRequired().equals("1")) {
                            if (((CheckBox) submitDatabject.getView()).isChecked()) {
                                stringMap.put(submitDatabject.getField_name(), submitDatabject.getValue());
                            } else {
                                Toast.makeText(DynamicForm.this, getResources().getString(R.string.accept), Toast.LENGTH_LONG).show();
                                errorflag = true;
                            }
                        } else {
                            stringMap.put(submitDatabject.getField_name(), submitDatabject.getValue());
                        }
                    } else if (submitDatabject.getStrType().equalsIgnoreCase("date")) {
                        if (submitDatabject.getStrRequired() != null && submitDatabject.getStrRequired().equals("1")) {
                            if (((TextView) submitDatabject.getView()).getText().length() > 0) {
                                stringMap.put(submitDatabject.getField_name(), ((TextView) submitDatabject.getView()).getText().toString().trim());

                            } else {
                                Toast.makeText(DynamicForm.this, getResources().getString(R.string.enterdate), Toast.LENGTH_LONG).show();
                                errorflag = true;
                            }
                        } else {
                            stringMap.put(submitDatabject.getField_name(), ((TextView) submitDatabject.getView()).getText().toString().trim());
                        }
                    } else if (submitDatabject.getStrType().equalsIgnoreCase("select") && (null != submitDatabject.getField_name() && !submitDatabject.getField_name().equalsIgnoreCase("telcode_phone"))) {
                        Spinner spinner = (Spinner) submitDatabject.getView();
                        stringMap.put(submitDatabject.getField_name(), spinner.getSelectedItem().toString());
                    } else if (submitDatabject.getStrType().equalsIgnoreCase("select") && (null != submitDatabject.getField_name() && submitDatabject.getField_name().equalsIgnoreCase("telcode_phone"))) {
                        Spinner spinner = (Spinner) submitDatabject.getView();
                        stringMap.put(submitDatabject.getField_name(), arrayListCountryCode.get(spinner.getSelectedItemPosition()).getIso());
                    } else if (submitDatabject.getStrType().equalsIgnoreCase("radio")) {
                        RadioGroup radioGroup = (RadioGroup) submitDatabject.getView();
                        stringMap.put(submitDatabject.getField_name(), submitDatabject.getValue());

                    }
                }
                if (!errorflag) {
                    getData();
                }
                break;
        }
    }

    private void getData() {
        //Creating a string request
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Submit Forms...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.SUBMIT_FORM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        try {
                            //Parsing the fetched Json String to JSON Object
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                                Toast.makeText(DynamicForm.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(DynamicForm.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                params.put("form_id", form_id);
                params.put("device", "a");
                params.put("hits", intCount + "");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    //   getDeviceImei();
                    params.put("imei", getDeviceImei());
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                final String regId = pref.getString("regId", null);
                params.put("reg_id", regId);
                JSONObject jsonObject = new JSONObject(stringMap);
                params.put("fields", jsonObject.toString());
                //  params.putAll(stringMap);

                return params;
            }
        };
//        JSONObject jsonobject=new JSONObject();
//     //   HashMap<String,String> params = new HashMap<>();
//        JSONObject parameters =new JSONObject();
//        try {
//            parameters.put("upro_id",uproID);
//            parameters.put("hash",hash);
//            parameters.put("form_id",form_id);
//                //params.putAll(stringMap);
//          //  parameters= new JSONObject(params);
//        JSONObject fileds=new JSONObject(stringMap);
////        JSONArray jsonArray=new JSONArray();
//      //  jsonArray.put(fileds);
//
//            parameters.put("fields",fileds);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, ConfigURL.SUBMIT_FORM, parameters, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                JSONObject j = null;
//                        if(progressDialog!=null){progressDialog.dismiss();}
//                        try {
//                            //Parsing the fetched Json String to JSON Object
//                           JSONObject jsonObject=response;
//                            if(jsonObject.getString("success").equalsIgnoreCase("true"))
//                            {
//                                Toast.makeText(DynamicForm.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
//                            finish();
//                            }
//                            else
//                            {
//                             Toast.makeText(DynamicForm.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        if(progressDialog!=null){progressDialog.dismiss();}
//                    }
//
//        });

        //   Volley.newRequestQueue(this).add(jsonRequest);
        //  Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private String getDeviceImei() {

        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            deviceid = mTelephonyManager.getDeviceId();
            Log.d("msg", "DeviceImei " + deviceid);
            return deviceid;
        }

        return deviceid;
    }
}
