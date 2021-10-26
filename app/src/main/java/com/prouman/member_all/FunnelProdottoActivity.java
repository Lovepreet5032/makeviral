package com.prouman.member_all;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.model.FormData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.prouman.R.id.back_btn;

/**
 * Created by jcs on 12/6/2016.
 */
public class FunnelProdottoActivity extends AppCompatActivity implements View.OnClickListener,Spinner.OnItemSelectedListener{
    private ImageView backBtn;
    private EditText etName,etSurname,etMail,etPhone;
    private Checkable cbPrivacy;
    private Button btnSubmit;
    TextView typeSpiner;
    ArrayList<FormData> students;
    CustomSpinnerAdapter spinnerAdapter;
    int formId;
    String formName,fName,lName,strEmail,strPhone,form_id;
  private String uproID;
    String hash;
    ProgressDialog pd;
    private String msgName;
    private JSONArray result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comman_form_activity);
        backBtn =(ImageView)findViewById(back_btn);
        backBtn.setOnClickListener(this);
        etName= (EditText)findViewById(R.id.et_name);

        etSurname= (EditText)findViewById(R.id.et_surname);

        etMail= (EditText)findViewById(R.id.et_email);

        etPhone= (EditText)findViewById(R.id.et_phone);

        cbPrivacy= (CheckBox)findViewById(R.id.CheckBoxResponse);
       // boolean cbTest= cbPrivacy.isChecked();
        btnSubmit= (Button)findViewById(R.id.btn_submit);
        typeSpiner = (TextView) findViewById(R.id.formName);
        typeSpiner.setText("Form pagina Prodotto");
         SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash= sharedPreferences.getString(PrefrencesConstant.hash,null);

        students = new ArrayList<>();


        btnSubmit.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){

                fName = etName.getText().toString();
                lName = etSurname.getText().toString();
                strEmail = etMail.getText().toString();
                strPhone = etPhone.getText().toString();
                    if( fName.equals(null)&&lName.equals(null)&&strEmail.equals(null)&&strPhone.equals(null))
                    {
                        Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                    }
                      else{
                       // new SubmitForm().execute();
                        submitFormData();
                    }

                //submitForm(form_id, fName, lName, strEmail, strPhone);

            }
            }

            );
       // typeSpiner.setOnItemSelectedListener(this);

      //  getData();
    }

    private void submitFormData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, ConfigURL.INSERT_FORM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String result =jsonObject.getString("success");
                    if(result.equals("true")){
                        progressDialog.dismiss();
                        msgName = jsonObject.getString("status");
                        etName.getText().clear();
                        etSurname.getText().clear();
                        etMail.getText().clear();
                        etPhone.getText().clear();
           if(msgName.equals("200")) {
                Toast.makeText(getApplicationContext(),"Form Submitted successfully", Toast.LENGTH_SHORT).show();
            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("form_id", "4656");
                params.put("fname", fName);
                params.put("lname", lName);
                params.put("email", strEmail);
                params.put("phone",strPhone);
                return params;

            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public class SubmitForm extends AsyncTask<Void,Void,Void> {


        @Override
        protected void onPreExecute() {


//
            //  pDialog.setMessage("Adding Product to Cart........");
            //   pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestBody formBody = new MultipartBody.Builder()
                    .addFormDataPart("form_id", "4656")
                    .addFormDataPart("fname", fName)
                    .addFormDataPart("lname", lName)
                    .addFormDataPart("email", strEmail)
                    .addFormDataPart("phone",strPhone)
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
            Toast.makeText(getApplicationContext(),msgName,Toast.LENGTH_SHORT).show();
           /* if(msgName.equals("200")) {
                Toast.makeText(getApplicationContext(),"Form Submitted successfully", Toast.LENGTH_SHORT).show();
            }*/
        }
    }
    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GET_FORM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("form_templates");

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
                //Adding the name of the student to array list
                formData.setForm_id(formId);
                formData.setForm_name(formName);
                students.add(formData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
     //   typeSpiner.setAdapter(new CustomSpinnerAdapter(this,students));
        //  spinner.setAdapter(new CountryAdapter(this, android.R.layout.simple_spinner_dropdown_item, students));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case back_btn:
                finish();
                break;



        }

    }

    private void submitForm(final String fId, final String firstName, final String lastName, final String Email, final String Phone) {
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
    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

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


    }

}
