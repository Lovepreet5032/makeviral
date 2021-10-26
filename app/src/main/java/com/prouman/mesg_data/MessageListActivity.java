package com.prouman.mesg_data;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.prouman.adapter.MessageListAdapter;
import com.prouman.test_db.ContactTest;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


/**
 * Created by jcs on 12/12/2016.
 */
public class MessageListActivity extends AppCompatActivity {
    private ArrayList<ContactTest> stock_list;
    private ImageView backBtn;

    Spinner categorySpinner;
    TextView contactNmbr;
    StringBuilder builderName;
    StringBuilder builderNumber;


    MessageListAdapter messageListAdapter;
    LinearLayoutManager linearLayoutManager;
    //Declaring an Spinner
    // private Spinner langSpinner;

    //An ArrayList for Spinner Items
    private ArrayList<AllTemplatesCampigns.Category> students;
    private ArrayList<AllTemplatesCampigns.Template> selectedtemplateArrayList;
    private List<AllTemplatesCampigns.Template> messageModels;

    //JSON Array
    private JSONArray result;



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
    //private JSONArray temlateArray;
    SharedPreferences sharedPreferences;
    CircleImageView profile_image;
    LinearLayout contact_number1,contact_number2;
    PopupWindow  popUp;
    ImageView img_close;
    AllTemplatesCampigns messageDataObjectNew;
    AllTemplatesCampigns messageCampaignsData;
    AllTemplatesCampigns radiobtnSelectedList;
    RadioGroup radio_message_type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences =this.getSharedPreferences("MyPref",0);
        //  pDialog=new ProgressDialog(this);
        setContentView(R.layout.message_list_activity);
        popUp = new PopupWindow(MessageListActivity.this);
        backBtn =(ImageView)findViewById(R.id.back_btn);
        contact_number1=(LinearLayout)findViewById(R.id.contact_number1);
        contact_number2=(LinearLayout)findViewById(R.id.contact_number2);
        categorySpinner=(Spinner)findViewById(R.id.spinner);
        radio_message_type=(RadioGroup)findViewById(R.id.radio_message_type);

        //     langSpinner = (Spinner) findViewById(R.id.spinner2);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            stock_list = extras.getParcelableArrayList("phoneNumber");
            ArrayList<byte[]> arrayList=new ArrayList<>();
        } else {
            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
        }
        builderName = new StringBuilder();
        builderNumber= new StringBuilder();
        setSelectedsmsLayout();

        // contactNmbr.setText(builderName);

        students = new ArrayList<>();
        selectedtemplateArrayList=new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.message_recyler);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        customSpinnerAdapter= new CategorySpinnerAdapter(this,students);
        updateUI();
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
                        customSpinnerAdapter = new CategorySpinnerAdapter(MessageListActivity.this, students);
                        categorySpinner.setAdapter(customSpinnerAdapter);
                        try {
                            categorySpinner.setSelection(0);
                        }catch (Exception e){}
                    } else {
                        new getEmailTemplates().execute();
                    }
                } else if (checkedId  == R.id.radio_email_campaigns){
                    if (null != messageDataObjectNew) {
                        radiobtnSelectedList = messageDataObjectNew;
                        students.clear();
                        selectedtemplateArrayList.clear();
                        selectedtemplateArrayList.addAll(radiobtnSelectedList.getEmailCampaigns().getTemplates());
                        students.addAll(radiobtnSelectedList.getEmailCampaigns().getCategory());
                        customSpinnerAdapter = new CategorySpinnerAdapter(MessageListActivity.this, students);
                        categorySpinner.setAdapter(customSpinnerAdapter);
                        try {
                            categorySpinner.setSelection(0);
                        }catch (Exception e){}
                    } else {
                        new getEmailTemplates().execute();
                    }
                }

                else  if (checkedId == R.id.radio_sms_templates) {
                    if (null != messageDataObjectNew) {
                        radiobtnSelectedList = messageDataObjectNew;
                        students.clear();
                        selectedtemplateArrayList.clear();
                        selectedtemplateArrayList.addAll(radiobtnSelectedList.getSmsTemplates().getTemplates());
                        students.addAll(radiobtnSelectedList.getSmsTemplates().getCategory());
                        customSpinnerAdapter = new CategorySpinnerAdapter(MessageListActivity.this, students);
                        categorySpinner.setAdapter(customSpinnerAdapter);
                        try {
                            categorySpinner.setSelection(0);
                        } catch (Exception e) {
                        }
                    } else {
                        new getEmailTemplates().execute();
                    }
                } else if(checkedId == R.id.radio_sms_campaigns){
                    if (null != messageDataObjectNew) {
                        radiobtnSelectedList = messageDataObjectNew;
                        students.clear();
                        selectedtemplateArrayList.clear();
                        selectedtemplateArrayList.addAll(radiobtnSelectedList.getSmsCampaigns().getTemplates());
                        students.addAll(radiobtnSelectedList.getSmsCampaigns().getCategory());
                        customSpinnerAdapter = new CategorySpinnerAdapter(MessageListActivity.this, students);
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
                AllTemplatesCampigns.Category catId= students.get(position);
                ArrayList<AllTemplatesCampigns.Template> temlateArray=new ArrayList<>();
                if(null != radiobtnSelectedList){
                    for(AllTemplatesCampigns.Template template:selectedtemplateArrayList){
                        if(template.getCategory().equalsIgnoreCase(catId.getId())){
                            temlateArray.add(template);

                        }
                    }
                }
                mainId= catId.getId();
                getMessageList(temlateArray);
                // Toast.makeText(getApplicationContext(), "Toast: " + mainId, Toast.LENGTH_LONG).show();
                if(mainId!=null){
                    //langSpinner.setAdapter(new MessageSpinnerAdapter(MessageListActivity.this,students));
                    customSpinnerAdapter.notifyDataSetChanged();
                    messageAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void setSelectedsmsLayout(){
        for (int i=0;i<stock_list.size();i++) {
            final int position=i;
            LayoutInflater li = LayoutInflater.from(this);
            final View layout = li.inflate(R.layout.rowemail_list_image, null, false);
            layout.setTag(i+"");
            layout.setId(i);
            // View containerDestacado = li.inflate(R.layout.container_destacado, null, false);

            contactNmbr= (TextView)layout.findViewById(R.id.textView7);
            profile_image=(CircleImageView)layout.findViewById(R.id.profile_image);
            final ContactTest contactTest=stock_list.get(i);
            if(i==0){
                builderName.append(contactTest.getName());
                builderNumber.append(contactTest.getEmail_Id());
                contactNmbr.setText(contactTest.getName());
                if ((contactTest.getContactImage()) != null) {
                    Bitmap contactImage = getContactImage(contactTest.getContactImage());
                    profile_image.setImageBitmap(contactImage);
                }else {
                    profile_image.setImageResource(R.drawable.ic_account_circle);
                }
            }
            else {builderName.append(","+contactTest.getName());
                builderNumber.append(";"+contactTest.getEmail_Id());
                contactNmbr.setText(contactTest.getName());
                if ((contactTest.getContactImage()) != null) {
                    Bitmap contactImage = getContactImage(contactTest.getContactImage());
                    profile_image.setImageBitmap(contactImage);
                }else {
                    profile_image.setImageResource(R.drawable.ic_account_circle);
                }}
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(popUp.isShowing()) {
                        popUp.dismiss();
                    }
                    LayoutInflater li = LayoutInflater.from(MessageListActivity.this);
                    final View layout = li.inflate(R.layout.rowemail_list_image_popup, null, false);
                    // View containerDestacado = li.inflate(R.layout.container_destacado, null, false);

                    contactNmbr = (TextView) layout.findViewById(R.id.textView7);
                    img_close=(ImageView)layout.findViewById(R.id.img_close);
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
                            if(Integer.parseInt(img_close.getTag().toString())%2==0) {
                                View view=contact_number1.findViewById(Integer.parseInt(img_close.getTag().toString()));
                                contact_number1.removeView(view);
                            }
                            else{
                                contact_number2.removeView(contact_number2.findViewById(Integer.parseInt(img_close.getTag().toString())));
                            }
                            stock_list.remove(Integer.parseInt(img_close.getTag().toString()));
                            if(stock_list.size()>0){
                                contact_number1.removeAllViews();
                                contact_number2.removeAllViews();
                                setSelectedsmsLayout();
                            }
                            else{
                                finish();
                            }
                        }
                    });
                    contactNmbr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popUp.dismiss();
                        }
                    });
                    popUp.setContentView(layout);
                    if(position%2==0) {
                        popUp.showAsDropDown(contact_number1, 10, 10);
                    }
                    else{  popUp.showAsDropDown(contact_number2, 10, 10);}

                }
            });
            if(i%2==0) {
                contact_number1.addView(layout);
            }
            else{  contact_number2.addView(layout);}
        }

    }
    private void updateUI() {
        messageModels= new ArrayList<>();
        messageAdapter= new MessageAdapter(messageModels,MessageListActivity.this,builderNumber.toString(),stock_list);
        recyclerView.setAdapter(messageAdapter);

    }

    //    public class getEmailCampaigns extends AsyncTask<Void,Void,Void> {
//
//        @Override
//        protected void onPreExecute() {
//            pDialog=new ProgressDialog(MessageListActivity.this);
//             pDialog.setMessage("Loading........");
//               pDialog.show();
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
//                    .url(ConfigURL.GET_EMAILCAMPAIGNS)
//                    .post(formBody)
//                    .build();
//
//
//            try {
//                okhttp3.Response httpResponse = client.newCall(request).execute();
//                String responseData = httpResponse.body().string();
//                        Gson gson=new Gson();
//                        messageCampaignsData=  gson.fromJson(responseData,MessageDataObjectNew.class);
//                        radiobtnSelectedList=messageCampaignsData;
//                        if(null != messageCampaignsData && messageCampaignsData.getSuccess().equalsIgnoreCase("true")){
//                            students.clear();
//                            students.addAll(messageCampaignsData.getCategory());
//                        }
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
    public class getEmailTemplates extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            pDialog=new ProgressDialog(MessageListActivity.this);
            pDialog.setMessage("Loading........");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestBody formBody = new FormBody.Builder()
                    .add("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null))
                    .add("hash",sharedPreferences.getString(PrefrencesConstant.hash,null))
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
                Gson gson=new Gson();
                messageDataObjectNew=  gson.fromJson(responseData,AllTemplatesCampigns.class);
                radiobtnSelectedList=messageDataObjectNew;
                if(null != messageDataObjectNew && messageDataObjectNew.getSuccess().equalsIgnoreCase("true")){
                    students.clear();
                    students.addAll(messageDataObjectNew.getEmailCampaigns().getCategory());
                }



            } catch (IOException e) {
                e.printStackTrace();        }



            return  null ;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            radiobtnSelectedList = messageDataObjectNew;
            students.clear();
            selectedtemplateArrayList.clear();
            selectedtemplateArrayList.addAll(radiobtnSelectedList.getEmailCampaigns().getTemplates());
            students.addAll(radiobtnSelectedList.getEmailCampaigns().getCategory());
            customSpinnerAdapter = new CategorySpinnerAdapter(MessageListActivity.this, students);
            categorySpinner.setAdapter(customSpinnerAdapter);
            try {
                categorySpinner.setSelection(0);
            }catch (Exception e){}
            callRadio();

            /*selectedtemplateArrayList.clear();
            selectedtemplateArrayList.addAll(radiobtnSelectedList.getEmailTemplates().getTemplates());
            categorySpinner.setAdapter(customSpinnerAdapter);*/
            pDialog.dismiss();



        }
    }

    private void callRadio(){
        if(students.size()>0) {
            AllTemplatesCampigns.Category catId = students.get(0);
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
    }


    private void getMessageList(List<AllTemplatesCampigns.Template> temlateArray) {
        messageModels.clear();
        messageModels.addAll(temlateArray);
        messageAdapter.notifyDataSetChanged();
        if(temlateArray.size()==0) {
            Toast.makeText(MessageListActivity.this, "No messages under this category", Toast.LENGTH_LONG).show();
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