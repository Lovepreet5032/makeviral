package com.prouman.mesg_data;/*
package com.makeviral.mesg_data;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.makeviral.R;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


*/
/**
 * Created by dsingh on 12/16/2016.
 *//*


public class MessageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Declaring an Spinner
    private Spinner catSpinner,langSpinner;

    //An ArrayList for Spinner Items
    private ArrayList<MsgMainModel.Category> students;
    private List<MsgMainModel> messageModels;

    //JSON Array
    private JSONArray result;

    //TextViews to display details
    private TextView textViewName;
    private TextView textViewCourse;
    private TextView textViewSession;

    CategorySpinnerAdapter customSpinnerAdapter;
    String defaultTextForSpinner = "Select Form";
    EditText etName,etSirName,etEmial,etphone;
    Button submtit;
    int formId;
    RecyclerView recyclerView;
    private String mainId;
    private String emailCategory;
    MessageAdapter messageAdapter;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pDialog;
    private MessageSpinnerAdapter laungeAdapter;
    private JSONArray temlateArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list_activity);



        //Initializing the ArrayList
        students = new ArrayList<>();


        //Initializing Spinner
       */
/* catSpinner = (Spinner) findViewById(R.id.spinnercategory);
        langSpinner = (Spinner) findViewById(R.id.spinnerlanguage);
        recyclerView= (RecyclerView)findViewById(R.id.recycler_view);*//*

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        customSpinnerAdapter= new CategorySpinnerAdapter(this,students);
       updateUI();

       //pinner.setPrompt("Select an item");
        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener



        //Initializing TextViews
        */
/*textViewName = (TextView) findViewById(R.id.textViewName);
        textViewCourse = (TextView) findViewById(R.id.textViewCourse);
        textViewSession = (TextView) findViewById(R.id.textViewSession);
*//*

        //This method will fetch the data from the URL
      new SubmitForm().execute();
        catSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MsgMainModel.Category catId= students.get(position);
                 mainId= catId.getId();
                getMessageList(temlateArray);
                // Toast.makeText(getApplicationContext(), "Toast: " + mainId, Toast.LENGTH_LONG).show();
                if(mainId!=null){
                    langSpinner.setAdapter(new MessageSpinnerAdapter(MessageActivity.this,students));
                    customSpinnerAdapter.notifyDataSetChanged();
                    messageAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateUI() {
        //mVideos = handler.getAllVideo();
        messageModels= new ArrayList<>();
        messageAdapter= new MessageAdapter(messageModels,MessageActivity.this);
        recyclerView.setAdapter(messageAdapter);


    }

   public class SubmitForm extends AsyncTask<Void,Void,Void> {

       MsgMainModel mainModel= new MsgMainModel();
       @Override
       protected void onPreExecute() {


//
         //  pDialog.setMessage("Adding Product to Cart........");
        //   pDialog.show();
       }

       @Override
       protected Void doInBackground(Void... params) {
           RequestBody formBody = new FormBody.Builder()
                   .add("upro_id", "448")
                   .add("hash","$2a$08$qRpoHHO2N8f.Qk.AZxieb.wR2RfKbhFSLVxJwwDwQkJcydliIRANi")
                   .build();

           OkHttpClient client = new OkHttpClient();
           // RequestBody requestBody = new FormBody.Builder().add("productId", String.valueOf(productId)).build();

          okhttp3.Request request = new okhttp3.Request.Builder()
               */
/**//*
  .url(Config.GETTEMPLATE)
                   .post(formBody)
                   .build();


           try {
               okhttp3.Response httpResponse = client.newCall(request).execute();
            //   int codeNumber = httpResponse.code();
             //  if (httpResponse.code()==200){
                   String responseData = httpResponse.body().string();
                   final JSONObject jsonObject = new JSONObject(responseData);

                     JSONArray jsonArray = jsonObject.getJSONArray("category");
              // pDialog.hide();
                     for(int k=0;k<jsonArray.length();k++) {

                     try {
                       //Getting json object
                       //Getting json object
                       MsgMainModel.Category formData = new MsgMainModel.Category();
                       JSONObject json = jsonArray.getJSONObject(k);
                       String catgeoryId = json.getString("id");
                       String formName = json.getString("name");
                       String langauge = json.getString("language");
                       //Adding the name of the student to array list
                       formData.setId(catgeoryId);
                       formData.setName(formName);
                       formData.setLanguage(langauge);
                        students.add(formData);
                         mainModel.setCategory(students);
                   }
                   catch (JSONException e) {
                       e.printStackTrace();
                   }

               }

                temlateArray  = jsonObject.getJSONArray("templates");


             */
/*  for (int i=0; i<temlateArray.length();i++) {
                   JSONObject jsonObject2 = null;
                   try {
                       jsonObject2 = temlateArray.getJSONObject(i);
                       SmsModel templates = new SmsModel();
                       emailCategory = jsonObject2.getString("email_category");
                       // int eCat= Integer.parseInt(emailCategory);

                           String body = jsonObject2.getString("body");
                           String subject = jsonObject2.getString("subject");
                           templates.setBody(body);
                           templates.setSubject(subject);
                           templates.setEmail_category(emailCategory);
                           if(catgeoryId.equals(emailCategory)) {
                            messageModels.add(templates);
    //mainModel.setTemplates(messageModels);
                             }
                    


                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }*//*




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
           catSpinner.setAdapter(customSpinnerAdapter);



           langSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 //  getMessageList(temlateArray);







               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });

       }
   }

    private void getMessageList(JSONArray temlateArray) {
        MsgMainModel mainModel= new MsgMainModel();
        messageModels.clear();
        for (int i=0; i<temlateArray.length();i++) {
            JSONObject jsonObject2 = null;
            try {
                jsonObject2 = temlateArray.getJSONObject(i);
                MsgMainModel templates = new MsgMainModel();
                emailCategory = jsonObject2.getString("email_category");
                // int eCat= Integer.parseInt(emailCategory);

                    String body = jsonObject2.getString("body");
                  String strRegEx = "<[^>]*>";
                   String strBody=body.replaceAll(strRegEx," ");
                  String mainBody= strBody.replaceAll("&nbsp;"," ");
                    String subject = jsonObject2.getString("subject");
                    templates.setBody(mainBody);
                    templates.setSubject(subject);
                    templates.setEmail_category(emailCategory);
                if(mainId.equals(emailCategory)) {

                    messageModels.add(templates);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

messageAdapter.notifyDataSetChanged();
        }


    }

    private void getStudents(JSONArray j){
        //Traversing through all the items in the json array

        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                MsgMainModel.Category formData = new MsgMainModel.Category();
                JSONObject json = j.getJSONObject(i);
                String catgeoryId= json.getString("id");
                String formName= json.getString("name");
                String langauge = json.getString("language");
                //Adding the name of the student to array list
                formData.setId(String.valueOf(formId));
                formData.setName(formName);
                formData.setLanguage(langauge);
                students.add(formData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
     catSpinner.setAdapter(new CategorySpinnerAdapter(this,students));
     langSpinner.setAdapter(new MessageSpinnerAdapter(this,students));
        //Setting adapter to show the items in the spinner

      // spinner.setAdapter(new CountryAdapter(this, android.R.layout.simple_spinner_dropdown_item, students));
    }

    //Method to get student name of a particular position
    private String getName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString(ConfigURL.TAG_NAME);
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
            course = json.getString(ConfigURL.TAG_COURSE);
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
            session = json.getString(ConfigURL.TAG_SESSION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }


    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MsgMainModel.Category selectedCountry = students.get(position);
        //SmsModel messageModel=messageModels.get(position);

        showToast(selectedCountry.getId() + " was selected!");
        formId= Integer.parseInt(selectedCountry.getId());

          */
/* if(formId==messageModels.get(position).getEmail_category())
           {
               recyclerView.setAdapter(messageAdapter);
               messageAdapter.notifyDataSetChanged();
           }*//*




       */
/* textViewName.setText(selectedCountry.getForm_name());
        textViewCourse.setText(selectedCountry.getForm_name());
        textViewSession.setText(selectedCountry.getForm_name());*//*

    }
    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }
    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
      */
/*  textViewName.setText("");
        textViewCourse.setText("");
        textViewSession.setText("");*//*

    }
}
*/
