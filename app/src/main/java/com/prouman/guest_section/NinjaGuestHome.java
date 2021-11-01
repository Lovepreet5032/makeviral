package com.prouman.guest_section;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.Util.SessionManager;
import com.prouman.activity.ContactActivity;
import com.prouman.app.Config;
import com.prouman.member_all.CustomGridViewActivity;
import com.prouman.member_all.MemSecVideoActivity;
import com.prouman.member_all.ProductInfoCategory;
import com.prouman.member_all.ProductTestominal;
import com.prouman.member_all.WebinarActivity;
import com.prouman.model.ItemObject;
import com.prouman.notification.NotificationList;
import com.prouman.videonewscreen.VideoDirectory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.prouman.R.id.tv_contactName;
import static com.prouman.R.string.Push;
import static com.prouman.R.string.Webinar;


/**
 * Created by om on 1/23/2017.
 */

public class    NinjaGuestHome extends AppCompatActivity {
    Button callBtn;
    String firstName;
    Button homeBtn;
    String imgUrl;
    String img_name;
    String lastName;

    TextView tvLogout,tvHome;
    String phone,sEmailId;
    private boolean zoomOut =  false;
    ImageView toolImg,headeIV;
    TextView toolText;
    TextView website;
    TextView tvName;
    TextView tvPhoneNumber;
    String uproAffLink;
   // String uproShopLink;
    String upro_ref, upro_id;
    RelativeLayout backToGuest;
    ImageView drawImage;
    LinearLayout layout_call,layout_message,layout_website,layout_email,layout_joinus;
    TextView txt_phonenumber,text_email,txt_weburl;
    TextView emailTv,contactName,header_TV;//phoneNumber//websiteName//emailName
    ImageView phoneIcon, contactImage ;//chatIcon,emailIcon
   // Button joinBtn;
   // LinearLayout join_layout;
    RelativeLayout topLayout;
    private String websiteUrl;
    List<ItemObject> rowListItem;
    AlertDialog alertDialog=null;
    DrawerLayout drawer;
   // LinearLayout webLayout;
    GridView androidGridView;
    private ImageView logBtn,home,settingBtn,pushBtn;
    ProgressDialog progressDialog;
    SessionManager session;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private static final int PERMISSIONS_REQUEST_GETACCOUNT= 1000;
    String count;
    CustomGridViewActivity adapterViewAndroid=null;
    TextView contactTv,titleTv;
    String deviceEmailId="";
    String mPhoneNumber="";
    String mPhoneIMEI="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drwa);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        session=new SessionManager(getApplicationContext());
//        join_layout=(LinearLayout)findViewById(R.id.join_layout);
//        join_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sharedPreferences =NinjaGuestHome.this.getSharedPreferences("MyPref",0);
//                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.viralninjalink,""))));
//            }
//        });
        SessionManager sessionManager=new SessionManager(this);
//        if(!sessionManager.getIsTokenRegister())
//        {
            sendToken();
      //  }
        txt_phonenumber=(TextView) findViewById(R.id.txt_phonenumber);
        text_email=(TextView) findViewById(R.id.text_email);
        txt_weburl=(TextView) findViewById(R.id.txt_weburl);
        layout_message=(LinearLayout)findViewById(R.id.layout_message);
        layout_website=(LinearLayout)findViewById(R.id.layout_website);
        layout_email=(LinearLayout)findViewById(R.id.layout_email);
        layout_joinus=(LinearLayout)findViewById(R.id.layout_joinus);
        layout_call=(LinearLayout)findViewById(R.id.layout_call);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        titleTv=(TextView)findViewById(R.id.titleTv);
        drawImage = (ImageView) findViewById(R.id.menu_icon_iv);
       // phoneNumber = (TextView) findViewById(R.id.phone_number);
        //emailName = (TextView) findViewById(R.id.emailName);
        contactTv = (TextView) findViewById(R.id.contactTv);
        contactImage = (ImageView) findViewById(R.id.contact_image);
        //joinBtn = (Button) findViewById(R.id.change_language);
        contactName = (TextView) findViewById(R.id.contactName);
      //  websiteName = (TextView) findViewById(R.id.web_name);
        header_TV = (TextView) findViewById(tv_contactName);
        headeIV = (ImageView) findViewById(R.id.heade_imageView);
        phoneIcon = (ImageView) findViewById(R.id.call_icon);
       // emailIcon = (ImageView) findViewById(R.id.email_icon);
        //chatIcon = (ImageView) findViewById(R.id.msg_icon);
        logBtn = (ImageView) findViewById(R.id.btn_logout);
        home = (ImageView) findViewById(R.id.btn_home);
        rowListItem = getAllItemList();
        settingBtn = (ImageView) findViewById(R.id.btn_setting);
        pushBtn = (ImageView) findViewById(R.id.btn_push);
         /* logoutBtn = (Button) findViewById(R.id.logoutBtn);
        homeBtn = (Button) findViewById(R.id.homeBtn);
        tvName = (TextView) findViewById(R.id.tvName);
        toolImg = (ImageView) findViewById(R.id.toolbar_image)*/
        toolImg = (ImageView) findViewById(R.id.layout_logo);
        // backToGuest = (RelativeLayout)findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)findViewById(R.id.layout_top));
        //webLayout=(LinearLayout)findViewById(R.id.webLayout);
        // Session class instance
        session = new SessionManager(getApplicationContext());
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            firstName = extras.getString("FName");
            lastName = extras.getString("LName");
            titleTv.setText(firstName+" "+lastName);
            phone = extras.getString("Phone");
            img_name = extras.getString("imgName");
            upro_ref = extras.getString("uproRef");
            uproAffLink = extras.getString("uproAffLink");
          //  uproShopLink = extras.getString("uproShopLink");
            sEmailId =   extras.getString("upro_main_email");
            websiteUrl =   extras.getString("websiteUrl");
            upro_id = extras.getString(PrefrencesConstant.uproid);


        } else {
            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
        }
        //phoneNumber.setText(phone);
        //emailName.setText(sEmailId);
        contactTv.setText(pref.getString("Gupro_distributor_id",""));
        contactName.setText(firstName+" "+lastName);
        txt_phonenumber.setText(phone);
        text_email.setText(sEmailId);
        try {
            txt_weburl.setText(new String(websiteUrl.replace("http://", "")));
            txt_weburl.setText(new String(websiteUrl.replace("https://", "")));
        }catch (Exception e){}
        // websiteName.setText(websiteUrl);

        imgUrl = img_name;//ConfigURL.PHOTO_URL +

        Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.round_corner).into(toolImg);
        Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.round_corner).into(contactImage);


        /*phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
//        webLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(websiteUrl)));
//                return;
//            }
//        });
        phoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });
        layout_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });
        layout_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendSMS();
                sendEmail();
            }
        });
        layout_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(websiteUrl)));
            }
        });
        layout_joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent("android.intent.action.VIEW", Uri.parse(shared.getString(PrefrencesConstant.memeberKyanilink, ""))));
               startActivity(new Intent(NinjaGuestHome.this, ContactActivity.class));
            }
        });
        layout_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
//        chatIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //sendSMS();
//                sendEmail();
//            }
//        });
    /*    emailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail();
            }
        });*/
        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NinjaGuestHome.this, NotificationList.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });
       /* joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NinjaGuestHome.this);

                // Setting Dialog Title
                alertDialog.setTitle("Select Language");
                // Setting Dialog Message
                //alertDialog.setMessage("Are you sure you want delete this?");

                // Setting Icon to Dialog
                //alertDialog.setIcon(R.drawable.call_icon);

                // Setting Positive "Yes" Button
            *//*    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        // Write your code here to invoke YES event
                        Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                    }
                });
*//*
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });*/
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(NinjaGuestHome.this,HomeActivity.class);
//                startActivity(i);
                finish();
               // drawer.closeDrawers();
            }
        });
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NinjaGuestHome.this);
                alertDialogBuilder.setMessage("Are you sure to logout as Guest?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
               //                         Toast.makeText(NinjaGuestHome.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                        SharedPreferences sharedPreferences =NinjaGuestHome.this.getSharedPreferences("MyPref",0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        //   sessionManager.setPreferences(NinjaGuestHome.this,"status","0");
                                        session.setPreferences(NinjaGuestHome.this, "G_status", "0");
//                Intent i = new Intent(NinjaGuestHome.this,HomeActivity.class);
//                startActivity(i);
                                        finish();
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
       /* backToGuest.setOnClickListener(new OnClickListener()
        {
            public void onClick(View view)
            {
                finish();

            }
        });*/
        contactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NinjaGuestHome.this,ContactActivity.class));
            }
        });
        toolImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Dialog settingsDialog = new Dialog(NinjaGuestHome.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//                settingsDialog.setContentView(R.layout.image_layout);
//                ImageView image= (ImageView)settingsDialog.findViewById(R.id.image_full);
//                Button btnCancel=(Button) settingsDialog.findViewById(R.id.btnCancel);
//                Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.full_screen_image).into(image);
//                settingsDialog.show();
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        settingsDialog.dismiss();
//                    }
//                });
                startActivity(new Intent(NinjaGuestHome.this,ContactActivity.class));
              /*  if(zoomOut) {
                        Toast.makeText(getApplicationContext(), "NORMAL SIZE!", Toast.LENGTH_LONG).show();
                        toolImg.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                        toolImg.setAdjustViewBounds(true);
                        zoomOut =false;
                    }else{
                        Toast.makeText(getApplicationContext(), "FULLSCREEN!", Toast.LENGTH_LONG).show();
                        toolImg.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                        // toolImg.setScaleType(ImageView.ScaleType.FIT_XY);
                        zoomOut = true;
                }*/
               /* Bundle bundle = new Bundle();
                bundle.putString("FName", firstName);
                bundle.putString("LName", lastName);
                bundle.putString("Phone", phone);
                bundle.putString("imgName", img_name);
                Intent intent = new Intent(NinjaGuestHome.this, ContactActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);*/
            }
        });

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                SharedPreferences sharedPreferences =NinjaGuestHome.this.getSharedPreferences("MyPref",0);
                switch (rowListItem.get(i).getName()) {
                    case R.string.product_info:
                        startActivity(new Intent(NinjaGuestHome.this,ProductInfoCategory.class));

                        break;
                    case R.string.pro_testominal:
                        startActivity(new Intent(NinjaGuestHome.this,ProductTestominal.class));
                        break;
                    case R.string.video_text:
                        Bundle bundle = new Bundle();
                        bundle.putString("catID", "48");
                        Intent video = new Intent(NinjaGuestHome.this, VideoDirectory.class);
                        AppConstant.videoscreentitle=getString(R.string.premium_video);
                        video.putExtras(bundle);
                        startActivity(video);
                        break;
                    case R.string.business_testominals:
                        Bundle args = new Bundle();
                        args.putString("catID","47");
                        Intent intent = new Intent(NinjaGuestHome.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.business_testominals);
                        intent.putExtras(args);
                        startActivity(intent);
                        break;

                    case R.string.shop_prouman:
                        if(!"null".equalsIgnoreCase(sharedPreferences.getString("shop_link", "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("shop_link", ""))));
                        }
                        else {Toast.makeText(NinjaGuestHome.this,"Link is empty",Toast.LENGTH_LONG).show();}
                    //    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.shop_link, ""))));
                        break;
                    case R.string.shop_life_wave:
                        if(!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.guestuproshoplink, "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.guestuproshoplink, ""))));
                        }
                        else {Toast.makeText(NinjaGuestHome.this,"Link is empty",Toast.LENGTH_LONG).show();}
                        //    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.shop_link, ""))));
                        break;
                    case Webinar:
                        startActivity(new Intent(NinjaGuestHome.this,WebinarActivity.class));
                        break;
                    case R.string.notification:
                        startActivity(new Intent(NinjaGuestHome.this,NotificationList.class));
                        break;
                    case R.string.life_wave:
                        //startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.guestuproasfflink,""))));
//                        if(!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.guestuproasfflink, "null"))) {
//                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.guestuproasfflink, ""))));
//                        }
//                        else {Toast.makeText(NinjaGuestHome.this,"Link is empty",Toast.LENGTH_LONG).show();}
                      //  startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.viralninjalink, ""))));
                        if(!"null".equalsIgnoreCase(sharedPreferences.getString("G_uproAffLink", "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("G_uproAffLink", ""))));
                        }
                        else {Toast.makeText(NinjaGuestHome.this,"Link is empty",Toast.LENGTH_LONG).show();}
                        break;
                    case R.string.join_kyani:
                        if(!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.oms_referral_nocopy_link, "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.oms_referral_nocopy_link, ""))));
                        }
                        else {Toast.makeText(NinjaGuestHome.this,"Link is empty",Toast.LENGTH_LONG).show();}

                        break;
                    case R.string.rss_blog:
                      //  startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.rsslink, ""))));
                    break;


                }

                // Toast.makeText(NinjaGuestHome.this, "GridView Item: " +rowListItem.get(i).getName(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= 19) {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this);
            Intent sendIntent = new Intent("android.intent.action.SEND", Uri.parse("tel:" + phone));
            sendIntent.putExtra("address", phone);
            sendIntent.setType("text/plain");
            sendIntent.putExtra("android.intent.extra.TEXT", "");
            if (defaultSmsPackageName != null) {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);
            return;
        }
        Intent smsIntent = new Intent("android.intent.action.VIEW");
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", phone);
        smsIntent.putExtra("sms_body", "message");
        startActivity(smsIntent);
    }

    private void onCall() {

        if (ContextCompat.checkSelfPermission(NinjaGuestHome.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(NinjaGuestHome.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    5);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            if (Build.VERSION.SDK_INT >= 19) {
                TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                Intent sendIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                sendIntent.putExtra("address", phone);
                if (tm != null && tm.getSimState() == 5) {
                    startActivity(sendIntent);
                    return;
                }
                return;
            }
            Intent callIntent = new Intent("android.intent.action.DIAL");
            callIntent.setPackage("com.android.phone");
            callIntent.setData(Uri.parse(phone));
            startActivity(callIntent);
        }
    }
    protected void sendEmail() {
//        Log.i("Send email", "");
         String[] TO = {sEmailId.trim()};
//        String[] CC = {""};
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/email");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            return;
//
//        }
//        catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(NinjaGuestHome.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, TO
        );
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra("body", "");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent,"Email"));
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case 123:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Log.d("TAG", "Call Permission Not Granted");
                } else {
                    onCall();
                }
            break;
            case PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendToken();
                }
                break;
            case PERMISSIONS_REQUEST_GETACCOUNT:
                if (requestCode == PERMISSIONS_REQUEST_GETACCOUNT
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendToken();
                }
//                else {
                //   Toast.makeText(this, "Until you grant the permission, we canot display the notifications", Toast.LENGTH_SHORT).show();
                CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(NinjaGuestHome.this,rowListItem);

                androidGridView.setAdapter(adapterViewAndroid);
                // }
                break;
        }
    }
    private List<ItemObject> getAllItemList() {
        List<ItemObject> allItems = new ArrayList();
        allItems.add(new ItemObject(R.string.video_text, R.drawable.video_icon));
        allItems.add(new ItemObject(R.string.join_kyani, R.drawable.round_prouman));
        allItems.add(new ItemObject(R.string.shop_prouman, R.drawable.shop_icon));
        allItems.add(new ItemObject(R.string.life_wave, R.drawable.life_wave));
        allItems.add(new ItemObject(R.string.shop_life_wave, R.drawable.shop_life_wave));
      //  allItems.add(new ItemObject(Webinar,R.drawable.icon_webinar));



       // allItems.add(new ItemObject(R.string.product_info, R.drawable.icon_product_info));
        //allItems.add(new ItemObject(R.string.pro_testominal, R.drawable.icon_product_testimonial));
        //allItems.add(new ItemObject(R.string.business_testominals,R.drawable.icon_business_testimonial));


        allItems.add(new ItemObject(R.string.notification, R.drawable.notification_icon ));
//        allItems.add(new ItemObject(R.string.life_wave, R.drawable.life_wave));
        /*allItems.add(new ItemObject(R.string.shop_life_wave, R.drawable.shop_life_wave));
        allItems.add(new ItemObject(R.string.notification, R.drawable.notification_icon ));*/
     //   allItems.add(new ItemObject(R.string.rss_blog, R.drawable.icon_rss_blog));


        return allItems;
    }
    public void sendToken() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS}, PERMISSIONS_REQUEST_GETACCOUNT);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getDeviceImei();
            final SessionManager sessionManager=new SessionManager(NinjaGuestHome.this);
            progressDialog = new ProgressDialog(NinjaGuestHome.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Login Into Member Account......");
            progressDialog.show();
            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
            final String regId = pref.getString("regId", null);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.registerDevice, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(progressDialog!=null){progressDialog.dismiss();}
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("success").equals("true")) {
                            sessionManager.setIsTokenRegister(true);
                            AppConstant.count=Integer.parseInt(jsonObject.getString("push_count"));
                            count=String.valueOf(AppConstant.count);
                            adapterViewAndroid= new CustomGridViewActivity(NinjaGuestHome.this,rowListItem);

                            androidGridView.setAdapter(adapterViewAndroid);
                        }
                        else{
                            CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(NinjaGuestHome.this,rowListItem);

                            androidGridView.setAdapter(adapterViewAndroid);
                        }
                        Log.e("Token:",jsonObject.toString());
                     // Toast.makeText(NinjaGuestHome.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(NinjaGuestHome.this,rowListItem);

                        androidGridView.setAdapter(adapterViewAndroid);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(progressDialog!=null){progressDialog.dismiss();}
                    CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(NinjaGuestHome.this,rowListItem);
                    androidGridView.setAdapter(adapterViewAndroid);
                   // Toast.makeText(NinjaGuestHome.this,"Error",Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    super.getParams();
                    Map<String, String> params = new HashMap<>();
                    SharedPreferences sharedPreferences =NinjaGuestHome.this.getSharedPreferences("MyPref",0);

                    String uproID = sharedPreferences.getString(PrefrencesConstant.guestgproid,null);
                    params.put("upro_id", uproID);
                    params.put("hash",sharedPreferences.getString(PrefrencesConstant.hash, ""));
                    params.put("device","a");
                    params.put("email",deviceEmailId);
                    params.put("phone",mPhoneNumber==null?"":mPhoneNumber);
                    params.put("imei",getIMEIDeviceId(NinjaGuestHome.this)==null?"":mPhoneIMEI);
                    params.put("reg_id",regId);//sessionManager.getGCMToken());

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(NinjaGuestHome.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }
    }

    public String getIMEIDeviceId(Context context) {

        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    deviceId = mTelephony.getImei();
                }else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        Log.d("deviceId", deviceId);
        mPhoneIMEI=deviceId;
        return deviceId;

    }

    private String getDeviceImei() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS}, PERMISSIONS_REQUEST_GETACCOUNT);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else
            {
              deviceEmailId=getEmiailID();
            }
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
       // String deviceid= mTelephonyManager.getDeviceId();
        mPhoneNumber = mTelephonyManager.getLine1Number();

//        if(mPhoneNumber.equals(""))
//        {
//            AccountManager am = AccountManager.get(this);
//            Account[] accounts = am.getAccounts();
//
//            for (Account ac : accounts) {
//                String acname = ac.name;
//                String actype = ac.type;
//                if(actype.equals("com.whatsapp")){
//                    mPhoneNumber = ac.name;
//                }
//                // Take your time to look at all available accounts
//                // System.out.println("Accounts : " + acname + ", " + actype);
//            }
//        }
      //  Log.d("msg", "DeviceImei " + deviceid);
        return "";
    }


    private String getEmiailID() {
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account account = getAccount(accountManager);
        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(AppConstant.isLanguagechanged)
        {
            AppConstant.isLanguagechanged=false;
            AppConstant.setLanguageForApp(session.getString(AppConstant.STRINGLANGUAGE,""),NinjaGuestHome.this);
            finish();
            startActivity(getIntent());
        }
        if(adapterViewAndroid!=null){
        adapterViewAndroid.notifyDataSetChanged();}
    }

}

