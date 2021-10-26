package com.prouman.ninjaforms;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prouman.R;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.adapter.FormAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.prouman.R.id.back_btn;

/**
 * Created by jcs on 12/6/2016.
 */
public class NativeFormActivity extends AppCompatActivity implements View.OnClickListener{//Spinner.OnItemSelectedListener{
    private ImageView backBtn;
//    private EditText etName,etSurname,etMail,etPhone;
//    private Checkable cbPrivacy;
//    private Button btnSubmit;
//    Spinner typeSpiner;
    ArrayList<Form> students;
    int formId;
    //String formName,fName,lName,strEmail,strPhone,form_id;
    private String uproID;
    String hash;
    ProgressDialog pd;
    private String msgName;
    private JSONArray result;
    RecyclerView recycler_form;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_form_layout);
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash= sharedPreferences.getString(PrefrencesConstant.hash,null);
        backBtn =(ImageView)findViewById(back_btn);
        backBtn.setOnClickListener(this);
        recycler_form=(RecyclerView)findViewById(R.id.recycler_form);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_form.setLayoutManager(manager);
//        etName= (EditText)findViewById(R.id.et_name);
//
//        etSurname= (EditText)findViewById(R.id.et_surname);
//
//        etMail= (EditText)findViewById(R.id.et_email);
//
//        etPhone= (EditText)findViewById(R.id.et_phone);
//
//        cbPrivacy= (CheckBox)findViewById(R.id.CheckBoxResponse);
//       // boolean cbTest= cbPrivacy.isChecked();
//        btnSubmit= (Button)findViewById(R.id.btn_submit);
//        typeSpiner = (Spinner) findViewById(R.id.spinnerCustom);
        //SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
      //  uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
       // hash= sharedPreferences.getString(PrefrencesConstant.hash,null);

        students = new ArrayList<>();


//        btnSubmit.setOnClickListener(new View.OnClickListener()
//
//            {
//                @Override
//                public void onClick (View v){
//
//                fName = etName.getText().toString();
//                lName = etSurname.getText().toString();
//                strEmail = etMail.getText().toString();
//                strPhone = etPhone.getText().toString();
//                    if( fName.equals(null)&&lName.equals(null)&&strEmail.equals(null)&&strPhone.equals(null))
//                    {
//                        Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_LONG).show();
//                    }
//                      else{
//                        new SubmitForm().execute();
//                    }
//
//                //submitForm(form_id, fName, lName, strEmail, strPhone);
//
//            }
//            }
//
//            );
//        typeSpiner.setOnItemSelectedListener(this);

        getData();
    }
 /*   public class SubmitForm extends AsyncTask<Void,Void,Void> {


        @Override
        protected void onPreExecute() {


//
            //  pDialog.setMessage("Adding Product to Cart........");
            //   pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestBody formBody = new FormBody.Builder()
                    .add("form_id", String.valueOf(formId))
                    .add("fname", fName)
                    .add("lname", lName)
                    .add("email", strEmail)
                    .add("phone",strPhone)
                    .build();

            OkHttpClient client = new OkHttpClient();
            // RequestBody requestBody = new FormBody.Builder().add("productId", String.valueOf(productId)).build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(ConfigURL.INSERT_FORM)
                    .post(formBody)
                    .build();


            try {
                okhttp3.Response httpResponse = client.newCall(request).execute();
                //   int codeNumber = httpResponse.code();
                //  if (httpResponse.code()==200){
                String responseData = httpResponse.body().string();


                final JSONObject jsonObject = new JSONObject(responseData);
                String result =jsonObject.getString("success");
                if(result.equals("true")){
                    msgName = jsonObject.getString("status");
                }



                // pDialog.hide();





            } catch (IOException e) {
                e.printStackTrace();        }

            catch (JSONException e) {
                e.printStackTrace();
            }



            return  null ;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(msgName.equals("200")) {
                etName.getText().clear();
                etSurname.getText().clear();
                etMail.getText().clear();
                etPhone.getText().clear();
                Toast.makeText(getApplicationContext(),"Form Submitted successfully", Toast.LENGTH_SHORT).show();
            }

        }
    }*/
    private void getData(){
        //Creating a string request
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Get Forms...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GET_FORM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        if(progressDialog!=null){progressDialog.dismiss();}
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);
                            Gson gson=new Gson();
                            FormDataObject formDataObject=gson.fromJson(response, FormDataObject.class);
                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("forms");

                            //Calling method getStudents to get the students from the JSON Array
                            getStudents(formDataObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null){progressDialog.dismiss();}
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


    private void getStudents(FormDataObject formDataObject){
        //Traversing through all the items in the json array

//        for(int i=0;i<formDataObject.getForms().size();i++){
//            try {
//                Form dataObject=formDataObject.getForms().get(i);
//                JSONObject json = j.getJSONObject(i);
//                int formId= json.getInt("id");
//                String formName= json.getString("form_name");
//                //Adding the name of the student to array list
//                formData.setForm_id(dataObject.getId());
//                formData.setForm_name(dataObject.getFormName());
//                students.add(formData);
        if(formDataObject.getForms().size()>0) {
            FormAdapter adapter = new FormAdapter(formDataObject.getForms(), this);
            recycler_form.setAdapter(adapter);
        }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

        //Setting adapter to show the items in the spinner
        //typeSpiner.setAdapter(new CustomSpinnerAdapter(this,students));
        //  spinner.setAdapter(new CountryAdapter(this, android.R.layout.simple_spinner_dropdown_item, students));


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case back_btn:
                finish();
                break;



        }

    }

 /*   private void submitForm(final String fId, final String firstName, final String lastName, final String Email, final String Phone) {
       pd = new ProgressDialog(this);
        pd.setMessage("Submitting form");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.INSERT_FORM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    boolean result = jsonObject.getBoolean("success");

                    if(result){
                        pd.hide();
                        Toast.makeText(getApplicationContext(),"Form Submitted Succesfully",Toast.LENGTH_SHORT).show();
                        }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Form Not Submitted ",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap();
                params.put("form_id", fId);
                params.put("fname", firstName);
                params.put("lname", lastName);
                params.put("email", Email);
                params.put("phone",Phone);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/

    //this method will execute when we pic an item from the spinner
   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FormData selectedCountry = students.get(position);
       // showToast(selectedCountry.getForm_id() + " was selected!");
        formId=selectedCountry.getForm_id();
       *//* textViewName.setText(selectedCountry.getForm_name());
        textViewCourse.setText(selectedCountry.getForm_name());
        textViewSession.setText(selectedCountry.getForm_name());*//*
    }
    public void showToast(String msg) {
       // Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }*/
    //When no item is selected this method would execute
 /*   @Override
    public void onNothingSelected(AdapterView<?> parent) {
      *//*  textViewName.setText("");
        textViewCourse.setText("");
        textViewSession.setText("");*//*
    }*/
  /*  public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<FormData> asr;

        public CustomSpinnerAdapter(Context context,ArrayList<FormData> asr) {
            this.asr=asr;
            activity = context;
        }



        public int getCount()
        {
            return asr.size();
        }

        public Object getItem(int i)
        {
            return asr.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(activity);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position).getForm_name());
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(activity);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setText(asr.get(i).getForm_name());
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }


    }*/

}
