package com.prouman.notification;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.app.Config;
import com.prouman.model.NotifcationdataObj;
import com.prouman.test_db.ContactDbHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationDetail extends AppCompatActivity {
TextView txt_date,txt_name,txt_title, txt_description;
    ImageView img_user,layout_logo;
    ProgressDialog progressDialog;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_detail);
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        ScrollView layout_main=(ScrollView)findViewById(R.id.layout_main);
        //  SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        if(sharedPreferences.getString(PrefrencesConstant.hash,"").equalsIgnoreCase("")){
            layout_main.setBackground(getResources().getDrawable(R.drawable.bg_guest));
        }
        ContactDbHandler db=new ContactDbHandler(this);
        txt_date=(TextView)findViewById(R.id.txt_date);
        txt_name=(TextView)findViewById(R.id.txt_name);
        txt_title=(TextView) findViewById(R.id.txt_title);
        txt_description=(TextView) findViewById(R.id.txt_description);
        img_user=(ImageView)findViewById(R.id.img_user);
        findViewById(R.id.layout_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        NotifcationdataObj notifcationdataObj=db.getNotification(getIntent().getExtras().getInt("notification_id"));
//        if(notifcationdataObj!=null&&notifcationdataObj.getNotification_id()>0)
//        {
//            db.updateNotification(notifcationdataObj.getNotification_id());
//            id=String.valueOf(notifcationdataObj.getNotification_id());
//            txt_date.setText(notifcationdataObj.getStr_date());
//            txt_name.setText(notifcationdataObj.getStr_username());
//            txt_title.setText(notifcationdataObj.getStr_tittle());
//            txt_description.setText(notifcationdataObj.getStr_description());
//            Picasso.with(this).load(notifcationdataObj.getStr_userimage()).placeholder(R.drawable.round_corner).into(img_user);
//        }
//        else
//        {
        NotifcationdataObj  notifcationdataObj=getIntent().getExtras().getParcelable("notification_data");
            id=String.valueOf(notifcationdataObj.getNotification_id());
            txt_date.setText(notifcationdataObj.getStr_date());
            txt_name.setText(notifcationdataObj.getStr_username());
//        String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(notifcationdataObj.getStr_tittle());
//        String fromServerUnicodeDecoded2 = StringEscapeUtils.unescapeJava(notifcationdataObj.getStr_description());

//        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/emojione-android.ttf");
//        txt_title.setTypeface(type);
        //txt_title.setText(AppConstant.getSmiledText(this, notifcationdataObj.getStr_tittle()));

       // txt_title.setText(Html.fromHtml(Html.toHtml(AppConstant.getSmiledText(this, notifcationdataObj.getStr_tittle()))));//notifcationdataObj.getStr_tittle()));//fromServerUnicodeDecoded);
        //txt_description.setText(AppConstant.getSmiledText(this, notifcationdataObj.getStr_description()));
    //    txt_description.setText(Html.fromHtml(Html.toHtml(AppConstant.getSmiledText(this, notifcationdataObj.getStr_description())),new ImageGetter(),null));//AndroidEmoji.ensure(notifcationdataObj.getStr_description(),this));
          //String s=txt_description.getText().toString();
        txt_title.setText(Html.fromHtml(notifcationdataObj.getStr_tittle()));
        txt_description.setText(Html.fromHtml(notifcationdataObj.getStr_description()));
            Picasso.with(this).load(notifcationdataObj.getStr_userimage()).placeholder(R.drawable.round_corner).into(img_user);
      //  }
        if(notifcationdataObj.getStr_isread().equals("0"))
        {
            sendRead();
        }
    }
    private void sendRead() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        if(progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();}
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final SharedPreferences sharedPreferences =getSharedPreferences("MyPref",0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.SeenNotification_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(progressDialog!=null){
                    progressDialog.hide();}
                JSONObject jsonObject;
                try {
                    jsonObject=new JSONObject(response);
                    if(jsonObject.getString("success").equals("true"))
                    {
//                        mNotifcationList.get(position).setStr_isread("1");
//                        mVideoModels.setStr_isread("1");
//                        notifyDataSetChanged();
//                        Intent intent = new Intent(context, NotificationDetail.class);
//                        intent.putExtra("notification_id", mVideoModels.getNotification_id());
//                        intent.putExtra("notification_data", mVideoModels);
//                        context.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null){
                    progressDialog.hide();}
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", sharedPreferences.getString(PrefrencesConstant.uproid,null));
                params.put("id",id);
                //    params.put("hash", hash);
                //    params.put("page",String.valueOf(currentPage));
//                params.put("reg_id",regId);
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
    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;

            id = getResources().getIdentifier(source, "drawable", getPackageName());

            if (id == 0) {
                // the drawable resource wasn't found in our package, maybe it is a stock android drawable?
                id = getResources().getIdentifier(source, "drawable", "android");
            }

            if (id == 0) {
                // prevent a crash if the resource still can't be found
                return null;
            }
            else {
                Drawable d = getResources().getDrawable(id);
              //  d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
                return d;
            }
        }

    }
}
