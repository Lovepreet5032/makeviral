package com.prouman.member_all;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prouman.R;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.native_form.Config;
import com.prouman.native_form.CustomSpinnerAdapter;
import com.prouman.native_form.FormData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.prouman.R.id.back_btn;

/**
 * Created by jcs on 12/6/2016.
 */
public class VipCardActivity extends AppCompatActivity implements View.OnClickListener,Spinner.OnItemSelectedListener{
    //Declaring an Spinner
    private Spinner spinner;

    //An ArrayList for Spinner Items
    private ArrayList<FormData> students;

    //JSON Array
    private JSONArray result;
    private ImageView backBtn;
    //TextViews to display details
    private TextView termTextView;
    private TextView textViewCourse;
    private TextView textViewSession;
    //CountryAdapter countryAdapter;
    CustomSpinnerAdapter customSpinnerAdapter;
    String defaultTextForSpinner = "Select Form";
    EditText etName,etSirName,etEmial,etphone;
    Button submtit;
    int formId;
    private String uproID,hash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_form_layout);
        backBtn =(ImageView)findViewById(back_btn);
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash= sharedPreferences.getString(PrefrencesConstant.hash,null);
        backBtn.setOnClickListener(this);
        etName=(EditText)findViewById(R.id.et_name);
        etSirName=(EditText)findViewById(R.id.et_surname);
        etEmial=(EditText)findViewById(R.id.et_email);
        etphone=(EditText)findViewById(R.id.et_phone);
        submtit= (Button)findViewById(R.id.btn_submit);
//        termTextView= (TextView)findViewById(R.id.urlText);

        //Initializing the ArrayList
        students = new ArrayList<>();

        //Initializing Spinner
//        spinner = (Spinner) findViewById(R.id.spinnerCustom);
        // spinner.setPrompt("Select an item");
        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        spinner.setOnItemSelectedListener(this);
        submtit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= etName.getText().toString();
                String surName= etSirName.getText().toString();
                String emailTxt=etEmial.getText().toString();
                String phoneTxt= etphone.getText().toString();
                submtitData(name,surName,emailTxt,phoneTxt,formId);
            }
        });

        termTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://app.prouman.network/auth_public/view_terms_conditions")));


            }
        });
        getData();

    }

    private void submtitData(final String name, final String surName, final String emailTxt, final String phoneTxt, final int formId) {

        String mainUrl;
        Uri builtUri = Uri.parse(Config.INSERTFORM)
                .buildUpon()
                .appendQueryParameter("form_id", String.valueOf(formId))
                .appendQueryParameter("fname", name)
                .appendQueryParameter("lname", surName)
                .appendQueryParameter("email", emailTxt)
                .appendQueryParameter("phone",phoneTxt)
                .build();
        try {
            URL url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET,builtUri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            String result = j.getString("status");
                            Toast.makeText(getApplicationContext(),"Form Correctly Submitted",Toast.LENGTH_SHORT).show();

                            //Calling method getStudents to get the students from the JSON Array
                            // getStudents(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap();
               /* params.put("form_id", String.valueOf(formId));
                params.put("fname",name);
                params.put("lname",surName);
                params.put("email",emailTxt);
                params.put("phone",phoneTxt);*/

                return params;
            }
        };

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getStudents(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String,String> params = new HashMap<>();
                params.put("upro_id",uproID);
                params.put("hash",hash);

                return params;
            }
        };

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getStudents(JSONArray j){
        //Traversing through all the items in the json array

        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                FormData formData = new FormData();
                JSONObject json = j.getJSONObject(i);
                int formId= json.getInt("id");
                String formName= json.getString("form_name");
                if(formName.equals("Form VIP Card")) {
                    formData.setForm_id(formId);
                    formData.setForm_name(formName);
                    students.add(formData);
                }
                else {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(VipCardActivity.this);
                        builder1.setMessage(R.string.seve_day_empty);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });

                      /*  builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });*/

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new CustomSpinnerAdapter(this,students));
        //  spinner.setAdapter(new CountryAdapter(this, android.R.layout.simple_spinner_dropdown_item, students));
    }

    //Method to get student name of a particular position
    private String getName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString(Config.TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    //Doing the same with this method as we did with getName()
    private String getCourse(int position){
        String course="";
        try {
            JSONObject json = result.getJSONObject(position);
            course = json.getString(Config.TAG_COURSE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return course;
    }

    //Doing the same with this method as we did with getName()
    private String getSession(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Config.TAG_SESSION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }


    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FormData selectedCountry = students.get(position);
        showToast(selectedCountry.getForm_id() + " was selected!");
        formId=selectedCountry.getForm_id();
       /* textViewName.setText(selectedCountry.getForm_name());
        textViewCourse.setText(selectedCountry.getForm_name());
        textViewSession.setText(selectedCountry.getForm_name());*/
    }
    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }
    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
      /*  textViewName.setText("");
        textViewCourse.setText("");
        textViewSession.setText("");*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case back_btn:
                finish();
                break;


        }
    }
}
