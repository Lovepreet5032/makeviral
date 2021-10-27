package com.prouman.sms_template;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Telephony;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.prouman.R;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.mesg_data.AllTemplatesCampigns;
import com.prouman.test_db.ContactDbHandler;
import com.prouman.test_db.ContactTest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


/**
 * Created by dsingh on 12/16/2016.
 */

public class SmsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Declaring an Spinner
    private Spinner catSpinner;//langSpinner;
    String phoneNumber;
    private ImageView backBtn;

    Spinner categorySpinner;
    TextView contactNmbr;
    //An ArrayList for Spinner Items
    private ArrayList<AllTemplatesCampigns.Category> students;
    private ArrayList<AllTemplatesCampigns.Template> selectedtemplateArrayList;
    private List<AllTemplatesCampigns.Template> messageModels;
    //AlertDialog actions;
//JSON Array
    private JSONArray result;

    //TextViews to display details
    private TextView textViewName;
    private TextView textViewCourse;
    private TextView textViewSession;

    CategorySpinnerAdapter customSpinnerAdapter;
    ContactDbHandler db;
    int formId;
    RecyclerView recyclerView;
    private String mainId;
    private String emailCategory;
    SmsNewAdapter messageAdapter;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pDialog;
    private SmsSpinnerAdapter laungeAdapter;
    private JSONArray temlateArray;
    private ArrayList<ContactTest> stock_list;
    private String uproID;
    String hash;
    StringBuilder builderName;
    StringBuilder builderNumber;
    private String messageTitle, messageBody;
    CircleImageView profile_image;
    ImageView img_close;
    LinearLayout contact_number;
    PopupWindow popUp;
    LinearLayout contact_number1, contact_number2;
    SharedPreferences sharedPreferences;
    AllTemplatesCampigns messageDataObjectNew;
    AllTemplatesCampigns messageCampaignsData;
    AllTemplatesCampigns radiobtnSelectedList;
    RadioGroup radio_message_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_message_list);
        // contactNmbr= (TextView)findViewById(R.id.textView7);
        sharedPreferences = this.getSharedPreferences("MyPref", 0);
        contact_number = (LinearLayout) findViewById(R.id.contact_number);
        contact_number1 = (LinearLayout) findViewById(R.id.contact_number1);
        contact_number2 = (LinearLayout) findViewById(R.id.contact_number2);
        popUp = new PopupWindow(SmsActivity.this);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPref", 0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
        hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
        categorySpinner = (Spinner) findViewById(R.id.spinner);
        radio_message_type = (RadioGroup) findViewById(R.id.radio_message_type);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stock_list.clear();

                finish();


            }
        });

        //Initializing the ArrayList
        students = new ArrayList<>();
        selectedtemplateArrayList = new ArrayList<>();

        //Initializing Spinner
        catSpinner = (Spinner) findViewById(R.id.spinner);
        // langSpinner = (Spinner) findViewById(R.id.spinner2);
        recyclerView = (RecyclerView) findViewById(R.id.sms_recyler);
        Intent intent = getIntent();
        if (intent != null) {
            // Bundle extras = intent.getExtras();
            // stock_list.clear();
            stock_list = intent.getParcelableArrayListExtra("phoneNumber");

        } else {
            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
        }
        builderName = new StringBuilder();
        builderNumber = new StringBuilder();
        setSelectedsmsLayout();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        customSpinnerAdapter = new CategorySpinnerAdapter(SmsActivity.this, students);
        updateUI();

        //pinner.setPrompt("Select an item");
        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener


        //Initializing TextViews
        /*textViewName = (TextView) findViewById(R.id.textViewName);
        textViewCourse = (TextView) findViewById(R.id.textViewCourse);
        textViewSession = (TextView) findViewById(R.id.textViewSession);
*/
        //This method will fetch the data from the URL
        new getEmailTemplates().execute();
        radio_message_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_email_templates) {
                    if (null != messageDataObjectNew) {
                        radiobtnSelectedList = messageDataObjectNew;
                        students.clear();
                        selectedtemplateArrayList.clear();
                        selectedtemplateArrayList.addAll(radiobtnSelectedList.getEmailTemplates().getTemplates());
                        students.addAll(radiobtnSelectedList.getEmailTemplates().getCategory());
                        customSpinnerAdapter = new CategorySpinnerAdapter(SmsActivity.this, students);
                        categorySpinner.setAdapter(customSpinnerAdapter);
                       /* try {
                            categorySpinner.setSelection(0);
                        }catch (Exception e){}*/
                    } else {
                        new getEmailTemplates().execute();
                    }
                } else if (checkedId == R.id.radio_email_campaigns) {
                    if (null != messageDataObjectNew) {
                        radiobtnSelectedList = messageDataObjectNew;
                        students.clear();
                        selectedtemplateArrayList.clear();
                        selectedtemplateArrayList.addAll(radiobtnSelectedList.getEmailCampaigns().getTemplates());
                        students.addAll(radiobtnSelectedList.getEmailCampaigns().getCategory());
                        customSpinnerAdapter = new CategorySpinnerAdapter(SmsActivity.this, students);
                        categorySpinner.setAdapter(customSpinnerAdapter);
                       /* try {
                            categorySpinner.setSelection(0);
                        }catch (Exception e){}*/
                    } else {
                        new getEmailTemplates().execute();
                    }
                } else if (checkedId == R.id.radio_sms_templates) {
                    if (null != messageDataObjectNew) {
                        radiobtnSelectedList = messageDataObjectNew;
                        students.clear();
                        selectedtemplateArrayList.clear();
                        selectedtemplateArrayList.addAll(radiobtnSelectedList.getSmsTemplates().getTemplates());
                        students.addAll(radiobtnSelectedList.getSmsTemplates().getCategory());
                        customSpinnerAdapter = new CategorySpinnerAdapter(SmsActivity.this, students);
                        categorySpinner.setAdapter(customSpinnerAdapter);
                        try {
                            categorySpinner.setSelection(0);
                        } catch (Exception e) {
                        }
                    } else {
                        new getEmailTemplates().execute();
                    }
                } else if (checkedId == R.id.radio_sms_campaigns) {
                    if (null != messageDataObjectNew) {
                        radiobtnSelectedList = messageDataObjectNew;
                        students.clear();
                        selectedtemplateArrayList.clear();
                        selectedtemplateArrayList.addAll(radiobtnSelectedList.getSmsCampaigns().getTemplates());
                        students.addAll(radiobtnSelectedList.getSmsCampaigns().getCategory());
                        customSpinnerAdapter = new CategorySpinnerAdapter(SmsActivity.this, students);
                        categorySpinner.setAdapter(customSpinnerAdapter);
                        try {
                            categorySpinner.setSelection(0);
                        } catch (Exception e) {
                        }
                    } else {
                        new getEmailTemplates().execute();
                    }
                }

            }
        });
        categorySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AllTemplatesCampigns.Category catId = students.get(position);
                ArrayList<AllTemplatesCampigns.Template> temlateArray = new ArrayList<>();
                if (null != radiobtnSelectedList) {
                    for (AllTemplatesCampigns.Template template : selectedtemplateArrayList) {
                        if (template.getCategory().equalsIgnoreCase(catId.getId())) {
                            temlateArray.add(template);

                        }
                    }
                }
                mainId = catId.getId();
                getMessageList(temlateArray);
                // Toast.makeText(getApplicationContext(), "Toast: " + mainId, Toast.LENGTH_LONG).show();
                if (mainId != null) {
                    //langSpinner.setAdapter(new MessageSpinnerAdapter(MessageListActivity.this,students));
                    customSpinnerAdapter.notifyDataSetChanged();
                    messageAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSelectedsmsLayout() {
        for (int i = 0; i < stock_list.size(); i++) {
            final int position = i;
            LayoutInflater li = LayoutInflater.from(this);
            final View layout = li.inflate(R.layout.rowemail_list_image, null, false);
            layout.setTag(i + "");
            layout.setId(i);
            // View containerDestacado = li.inflate(R.layout.container_destacado, null, false);

            contactNmbr = (TextView) layout.findViewById(R.id.textView7);
            profile_image = (CircleImageView) layout.findViewById(R.id.profile_image);
            final ContactTest contactTest = stock_list.get(i);
            if (i == 0) {
                builderName.append(contactTest.getName());
                builderNumber.append(contactTest.getEmail_Id());
                contactNmbr.setText(contactTest.getName());
                if ((contactTest.getContactImage()) != null) {
                    Bitmap contactImage = getContactImage(contactTest.getContactImage());
                    profile_image.setImageBitmap(contactImage);
                } else {
                    profile_image.setImageResource(R.drawable.ic_account_circle);
                }
            } else {
                builderName.append("," + contactTest.getName());
                builderNumber.append(";" + contactTest.getEmail_Id());
                contactNmbr.setText(contactTest.getName());
                if ((contactTest.getContactImage()) != null) {
                    Bitmap contactImage = getContactImage(contactTest.getContactImage());
                    profile_image.setImageBitmap(contactImage);
                } else {
                    profile_image.setImageResource(R.drawable.ic_account_circle);
                }
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popUp.isShowing()) {
                        popUp.dismiss();
                    }
                    LayoutInflater li = LayoutInflater.from(SmsActivity.this);
                    final View layout = li.inflate(R.layout.rowemail_list_image_popup, null, false);
                    // View containerDestacado = li.inflate(R.layout.container_destacado, null, false);

                    contactNmbr = (TextView) layout.findViewById(R.id.textView7);
                    img_close = (ImageView) layout.findViewById(R.id.img_close);
                    img_close.setTag(v.getTag());
                    profile_image = (CircleImageView) layout.findViewById(R.id.profile_image);
                    contactNmbr.setText(contactTest.getPhone());
                    if ((contactTest.getContactImage()) != null) {
                        Bitmap contactImage = getContactImage(contactTest.getContactImage());
                        profile_image.setImageBitmap(contactImage);
                    } else {
                        profile_image.setImageResource(R.drawable.ic_account_circle);
                    }
                    img_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popUp.dismiss();
                            if (Integer.parseInt(img_close.getTag().toString()) % 2 == 0) {
                                View view = contact_number1.findViewById(Integer.parseInt(img_close.getTag().toString()));
                                contact_number1.removeView(view);
                            } else {
                                contact_number2.removeView(contact_number2.findViewById(Integer.parseInt(img_close.getTag().toString())));
                            }
                            stock_list.remove(Integer.parseInt(img_close.getTag().toString()));
                            if (stock_list.size() > 0) {
                                contact_number1.removeAllViews();
                                contact_number2.removeAllViews();
                                setSelectedsmsLayout();
                            } else {
                                finish();
                            }
                        }
                    });
                    //LinearLayout Linlayout = new LinearLayout(MessageListActivity.this);
                    // TextView tv = new TextView(MessageListActivity.this);

                    // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    //        LinearLayout.LayoutParams.WRAP_CONTENT);
                    //Linlayout.setOrientation(LinearLayout.VERTICAL);
                    // tv.setText(contactTest.getName());
                    //Linlayout.addView(tv, params);
                    contactNmbr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popUp.dismiss();
                        }
                    });
                    popUp.setContentView(layout);
                    if (position % 2 == 0) {
                        popUp.showAsDropDown(contact_number1, 10, 10);
                    } else {
                        popUp.showAsDropDown(contact_number2, 10, 10);
                    }

                    //  popUp.update(150, 50, 300, 80);
                    // popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                    //  mainLayout.addView(but, params);
                }
            });
            if (i % 2 == 0) {
                contact_number1.addView(layout);
            } else {
                contact_number2.addView(layout);
            }
            // Toast.makeText(this,contactTest.getEmail_Id(),Toast.LENGTH_LONG).show();
        }

    }

    private void updateUI() {
        //mVideos = handler.getAllVideo();
        messageModels = new ArrayList<>();
        messageAdapter = new SmsNewAdapter(messageModels, SmsActivity.this);
        recyclerView.setAdapter(messageAdapter);


    }

    //    public class getSmsCampaigns extends AsyncTask<Void,Void,Void> {
//
//        @Override
//        protected void onPreExecute() {
//            pDialog=new ProgressDialog(SmsActivity.this);
//            pDialog.setMessage("Loading........");
//            pDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            RequestBody formBody = new FormBody.Builder()
//                    .add("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null))
//                    .add("hash",sharedPreferences.getString(PrefrencesConstant.hash,null))
//                    .build();
//
//            OkHttpClient client = new OkHttpClient();
//            // RequestBody requestBody = new FormBody.Builder().add("productId", String.valueOf(productId)).build();
//
//            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .url(ConfigURL.SMS_CAMPAIGNS)
//                    .post(formBody)
//                    .build();
//
//
//            try {
//                okhttp3.Response httpResponse = client.newCall(request).execute();
//                String responseData = httpResponse.body().string();
//                Gson gson=new Gson();
//                messageCampaignsData=  gson.fromJson(responseData,MessageDataObjectNew.class);
//                radiobtnSelectedList=messageCampaignsData;
//                if(null != messageCampaignsData && messageCampaignsData.getSuccess().equalsIgnoreCase("true")){
//                    students.clear();
//                    students.addAll(messageCampaignsData.getCategory());
//                }
//
//
//
//            } catch (IOException e) {
//                e.printStackTrace();        }
//
//
//
//            return  null ;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            categorySpinner.setAdapter(customSpinnerAdapter);
//            pDialog.dismiss();
//
//
//
//        }
//    }
//   public class getSmsTemplate extends AsyncTask<Void,Void,Void> {
//
//       @Override
//       protected void onPreExecute() {
//           pDialog=new ProgressDialog(SmsActivity.this);
//           pDialog.setMessage("Loading........");
//           pDialog.show();
//       }
//
//       @Override
//       protected Void doInBackground(Void... params) {
//           RequestBody formBody = new FormBody.Builder()
//                   .add("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null))
//                   .add("hash",sharedPreferences.getString(PrefrencesConstant.hash,null))
//                   .build();
//
//           OkHttpClient client = new OkHttpClient();
//           // RequestBody requestBody = new FormBody.Builder().add("productId", String.valueOf(productId)).build();
//
//           okhttp3.Request request = new okhttp3.Request.Builder()
//                   .url(ConfigURL.SMS_TEMPLATE)
//                   .post(formBody)
//                   .build();
//
//
//           try {
//               okhttp3.Response httpResponse = client.newCall(request).execute();
//               String responseData = httpResponse.body().string();
//               Gson gson=new Gson();
//               messageDataObjectNew=  gson.fromJson(responseData,MessageDataObjectNew.class);
//               radiobtnSelectedList=messageDataObjectNew;
//               if(null != messageDataObjectNew && messageDataObjectNew.getSuccess().equalsIgnoreCase("true")){
//                   students.clear();
//                   students.addAll(messageDataObjectNew.getCategory());
//               }
//
//
//
//           } catch (IOException e) {
//               e.printStackTrace();        }
//
//
//
//           return  null ;
//       }
//
//       @Override
//       protected void onPostExecute(Void aVoid) {
//           super.onPostExecute(aVoid);
//           categorySpinner.setAdapter(customSpinnerAdapter);
//           pDialog.dismiss();
//
//
//
//       }
//   }
//
    public class getEmailTemplates extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(SmsActivity.this);
            pDialog.setMessage("Loading........");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestBody formBody = new FormBody.Builder()
                    .add("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid, null))
                    .add("hash", sharedPreferences.getString(PrefrencesConstant.hash, null))
                    .build();

            OkHttpClient client = new OkHttpClient();
            // RequestBody requestBody = new FormBody.Builder().add("productId", String.valueOf(productId)).build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(ConfigURL.GET_TEMPLATE_CAMPIGNS)
                    .post(formBody)
                    .build();


            try {
                okhttp3.Response httpResponse = client.newCall(request).execute();
                String responseData = httpResponse.body().string();
                Gson gson = new Gson();
                messageDataObjectNew = gson.fromJson(responseData, AllTemplatesCampigns.class);
                radiobtnSelectedList = messageDataObjectNew;
                if (null != messageDataObjectNew && messageDataObjectNew.getSuccess().equalsIgnoreCase("true")) {
                    students.clear();
                    students.addAll(messageDataObjectNew.getSmsCampaigns().getCategory());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            categorySpinner.setAdapter(customSpinnerAdapter);
            pDialog.dismiss();


        }
    }


    private void getMessageList(List<AllTemplatesCampigns.Template> temlateArray) {
        messageModels.clear();
        messageModels.addAll(temlateArray);
        messageAdapter.notifyDataSetChanged();
        if (temlateArray.size() == 0) {
            Toast.makeText(SmsActivity.this, "No messages under this category", Toast.LENGTH_LONG).show();
        }

    }


    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        AllTemplatesCampigns.Category selectedCountry = students.get(position);
        //SmsModel messageModel=messageModels.get(position);

        showToast(selectedCountry.getId() + " was selected!");
        formId = Integer.parseInt(selectedCountry.getId());

    }

    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class SmsNewAdapter extends RecyclerView.Adapter<SmsNewAdapter.MyViewHolder> {
        //ArrayList<Category> cModels;
        Context context;
        List<AllTemplatesCampigns.Template> messageModels;


        public SmsNewAdapter(List<AllTemplatesCampigns.Template> messageModels, Context context) {
            // this.cModels= cModels;

            this.messageModels = messageModels;
            this.context = context;
        }

        @Override
        public SmsNewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row, parent, false);

            return new SmsNewAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SmsNewAdapter.MyViewHolder holder, int position) {
            final AllTemplatesCampigns.Template messageModelTest = messageModels.get(position);
            //  String result = cPos.getBody();
            // String mainMsg= String.valueOf(Html.fromHtml(result));
            //messageModels = cPos.getProds();
            SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
            // 0 - for private mode
            String jsonstring = pref.getString("jsonarray", "");
            JSONArray arrayjson = null;
            try {
                arrayjson = new JSONArray(jsonstring);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (null != arrayjson) {
                for (int i = 0; i < arrayjson.length(); i++) {
                    try {
                        String string = arrayjson.get(i).toString();
                        if (null != messageModels.get(position).getSubject() && null != messageModels.get(position).getBody()) {
                            if (messageModelTest.getBody().contains(string)) {

                                if (stock_list.size() > 0) {
                                    if (string.equalsIgnoreCase("prospect_surname")) {
                                        messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getName()));
                                        messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getName()));
                                    } else if (string.equalsIgnoreCase("prospect_name")) {
                                        messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getName()));
                                        messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getName()));
                                    } else if (string.equalsIgnoreCase("prospect_first_name")) {
                                        messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getName()));
                                        messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getName()));
                                    } else if (string.equalsIgnoreCase("prospect_last_name")) {
                                        messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getName()));
                                        messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getName()));
                                    } else if (string.equalsIgnoreCase("prospect_email")) {
                                        messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getEmail_Id()));
                                        messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getEmail_Id()));
                                    } else if (string.equalsIgnoreCase("prospect_phone")) {
                                        messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getPhone()));
                                        messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", stock_list.get(0).getPhone()));
                                    } else {
                                        messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", ""));
                                        messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", ""));
                                    }
                                } else {
                                    messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", ""));
                                    messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", ""));
                                }


                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            holder.titleTv.setText(messageModelTest.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.textView.setText(Html.fromHtml(messageModelTest.getBody(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.textView.setText(Html.fromHtml(messageModelTest.getBody()));
            }

            // holder.titleTv.setText(messageModelTest.getSubject());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messageTitle = messageModelTest.getName();
                    messageBody = messageModelTest.getBody();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                    alertDialogBuilder.setTitle("Choose an Option");
                    String[] options = {"SMS", "WhatsApp and others .."};
                    // set title
                    // alertDialogBuilder.setTitle("Your Title");
                    alertDialogBuilder.setItems(options, actionListener);
                    alertDialogBuilder.setNegativeButton("Cancel", null);
                    //actions = alertDialogBuilder.create();
                    // set dialog message
       /* alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                       finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
*/
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(SmsActivity.this);
        builder.setTitle("Choose an Option");
        String[] options = { "SMS", "SHARE" };
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();*/
                //  chooseIntent();
                //sendSMS();
       /* Intent intent = new Intent(Intent.ACTION_SEND);


// Always use string resources for UI text.
// This says something like "Share this photo with"
        String title = getResources().getString(R.string.chooser_title);
// Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, title);

// Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
          if(intent.equals("android.intent.action.SEND"))
          {

          }
            else {

          }
        }
*/

            });

        }

        DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        sendSMS();
                        break;
                    case 1:
                        chooseIntent();
                        break;

                    default:
                        break;
                }
            }
        };

        @Override
        public int getItemCount() {
            return messageModels.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView titleTv;
            TextView textView;
            // Button button;


            public MyViewHolder(View itemView) {
                super(itemView);
                titleTv = (TextView) itemView.findViewById(R.id.txtTitle);
                textView = (TextView) itemView.findViewById(R.id.txtText);
                //button=(Button)itemView.findViewById(R.id.btnSale);

            }
        }
    }

    private void chooseIntent() {
        update_sentStatus();
        List<Intent> targetedShareIntents = new ArrayList<Intent>();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageBody);

        List<ResolveInfo> resInfo = this.getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;

            Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
            targetedShareIntent.setType("text/plain");
            targetedShareIntent.putExtra(Intent.EXTRA_TEXT, messageBody);
            targetedShareIntent.setPackage(packageName);

            if (!packageName.equals("com.facebook.katana")) { // Remove Facebook Intent share
                targetedShareIntents.add(targetedShareIntent);
            }
        }

// Add my own activity in the share Intent chooser
        Intent i = new Intent(this, SmsActivity.class);
        targetedShareIntents.add(i);

        Intent chooserIntent = Intent.createChooser(
                targetedShareIntents.remove(0), "Select app to share");

        chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

        startActivity(chooserIntent);
        // Intents with SEND action
    /*    PackageManager packageManager = getApplicationContext().getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(sendIntent, 0);

        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        Resources resources = getApplicationContext().getResources();

        for (int j = 0; j < resolveInfoList.size(); j++) {
            ResolveInfo resolveInfo = resolveInfoList.get(j);
            String packageName = resolveInfo.activityInfo.packageName;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setComponent(new ComponentName(packageName,
                    resolveInfo.activityInfo.name));
            intent.setType("text/plain");

            if (packageName.contains("android.intent.action.SEND")) {
                sendSMS();

            } else {
               *//* // skip android mail and gmail to avoid adding to the list twice
                if (packageName.contains("android.email") || packageName.contains("android.gm")) {
                    continue;*//*

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,messageTitle);
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,messageBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
*/


    }

    private void sendSMS() {
        update_sentStatus();
        if (Build.VERSION.SDK_INT >= 19) {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this);
            Intent sendIntent = new Intent("android.intent.action.SEND", Uri.parse("tel:" + builderNumber.toString()));
            sendIntent.putExtra("address", builderNumber.toString());
            sendIntent.setType("text/plain");
            sendIntent.putExtra("android.intent.extra.TEXT", messageBody);
            if (defaultSmsPackageName != null) {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);
            return;
        }
        Intent smsIntent = new Intent("android.intent.action.VIEW");
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", builderNumber.toString());
        smsIntent.putExtra("sms_body", messageBody);
        startActivity(smsIntent);
    }

    private void update_sentStatus() {
        db = new ContactDbHandler(SmsActivity.this);
        for (int i = 0; i < stock_list.size(); i++) {
            ContactTest contactVO = stock_list.get(i);
            contactVO.setMobileShared("1");
            db.updateSentStatus(contactVO, "0");
        }
    }

    private Bitmap getContactImage(byte[] photo) {
        int targetW = 50, targetH = 50;
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeByteArray(photo, 0, photo.length, options);
        options.inJustDecodeBounds = true;
        int imageW = options.outWidth;
        int imageH = options.outHeight;
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(imageW / targetW, imageH / targetH);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeByteArray(photo, 0, photo.length, options);
    }
}
